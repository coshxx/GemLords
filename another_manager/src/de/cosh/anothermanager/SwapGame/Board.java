package de.cosh.anothermanager.SwapGame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import de.cosh.anothermanager.AnotherManager;
import de.cosh.anothermanager.Characters.Enemy;
import de.cosh.anothermanager.Characters.Player;
import de.cosh.anothermanager.GUI.GUIWindow;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.Random;

/**
 * Created by cosh on 13.01.14.
 */
public class Board extends Table {
    public static final int MAX_SIZE_X = 9;
    public static final int MAX_SIZE_Y = 9;
    public static final int CELL_PAD_X = 45;
    public static final int CELL_PAD_Y = 45;
    public static final int CELL_SIZE = 70;
    private AnotherManager myGame;
    private Cell[][] cells;
    private Random random;
    private Group backGround;
    private Group foreGround;
    private Group effectGroup;
    private SwapController swapController;
    private MatchFinder matchFinder;
    private GravityApplier gravityApplier;
    private GemRemover gemRemover;
    private GemRespawner gemRespawner;
    private boolean initialized;
    private boolean justSwapped;
    private BoardState boardState;
    private int matchesDuringCurrentMove;

    private GridPoint2 lastSwap;
    private int lastX, lastY;

    private Enemy enemy;
    private Player player;


    public Board(AnotherManager myGame) {
        this.myGame = myGame;
        cells = new Cell[MAX_SIZE_X][MAX_SIZE_Y];
        swapController = new SwapController(cells);
        matchFinder = new MatchFinder(cells);
        gravityApplier = new GravityApplier(cells);
        gemRemover = new GemRemover(cells);
        gemRespawner = new GemRespawner(cells, random, myGame.gemFactory);
        random = new Random();
        backGround = new Group();
        backGround.setBounds(0, 0, myGame.VIRTUAL_WIDTH, myGame.VIRTUAL_HEIGHT);
        foreGround = new Group();
        foreGround.setBounds(0, 0, myGame.VIRTUAL_WIDTH, myGame.VIRTUAL_HEIGHT);
        effectGroup = new Group();
        effectGroup.setBounds(0, 0, myGame.VIRTUAL_WIDTH, myGame.VIRTUAL_HEIGHT);
        Image backImage = new Image(myGame.assets.get("data/background.png", Texture.class));
        backImage.setBounds(0, 0, myGame.VIRTUAL_WIDTH, myGame.VIRTUAL_HEIGHT);
        backGround.addActor(backImage);
        addActor(backGround);
        addActor(foreGround);
        addActor(effectGroup);
        boardState = BoardState.STATE_EMPTY;
        initialized = false;
        justSwapped = false;
    }



    private void fillWithRandomGems() {
        for (int x = 0; x < MAX_SIZE_X; x++) {
            for (int y = 0; y < MAX_SIZE_Y; y++) {
                cells[x][y] = new Cell(myGame);
                cells[x][y].setColor(1f, 1f, 1f, 0.35f);
                cells[x][y].setPosition(CELL_PAD_X + (x * CELL_SIZE), CELL_PAD_Y + (y * CELL_SIZE));
                cells[x][y].putGem(myGame.gemFactory.newRandomGem());
                backGround.addActor(cells[x][y]);
                foreGround.addActor(cells[x][y].getGem());
            }
        }
    }

    private void prepareEnemy() {
        enemy = myGame.enemyManager.getSelectedEnemy();
        enemy.addToBoard(foreGround);

    }

    private void preparePlayer() {
        player = myGame.player;
        player.addToBoard(foreGround);
    }

    public void update(float delta) {
        if (!initialized) {
            initialized = true;
            fillWithRandomGems();
            while (matchFinder.hasMatches() ) {
                backGround.clear();
                foreGround.clear();
                Image backImage = new Image(myGame.assets.get("data/background.png", Texture.class));
                backGround.addActor(backImage);
                fillWithRandomGems();
            }
            prepareEnemy();
            preparePlayer();
            boardState = BoardState.STATE_CHECK;
            matchesDuringCurrentMove = 0;
        }

        if (boardState == BoardState.STATE_CHECK) {
            MatchResult result = matchFinder.markAllMatchingGems();
            if (result.howMany > 0) {
                if (result.conversion)
                    myGame.soundPlayer.playConvert();
                else {
                    myGame.soundPlayer.playDing(matchesDuringCurrentMove++);
                }
                result.howMany = 0;
                result = gemRemover.fadeMarkedGems(effectGroup);
                enemy.damage(result.howMany);
                System.out.println("Enemy damage: " + result.howMany);
                if (result.specialExplo)
                    myGame.soundPlayer.playWoosh();
                boardState = BoardState.STATE_FADING;
            }
        }

        updateBoardState();
    }

    private void updateBoardState() {
        boolean stillMovement = false;
        for (int x = 0; x < MAX_SIZE_X; x++) {
            for (int y = 0; y < MAX_SIZE_Y; y++) {
                Gem gem = cells[x][y].getGem();
                if (gem.getActions().size > 0) {
                    stillMovement = true;
                }
            }
        }
        if (!stillMovement && boardState == BoardState.STATE_FADING) {
            gemRemover.removeFadedGems(myGame, effectGroup);
            gravityApplier.applyGravity();
            gemRespawner.respawn(foreGround);
            boardState = BoardState.STATE_MOVING;
        } else if (!stillMovement && boardState == BoardState.STATE_SWAPPING) {
            if( !matchFinder.hasMatches() ) {
                boardState = BoardState.STATE_MOVING;
                swapController.swap(lastSwap, lastX, lastY);
            }
            boardState = BoardState.STATE_CHECK;
        } else if (!stillMovement && boardState == BoardState.STATE_CHECK) {

            if( enemy.getHealth() <= 0 ) {
                GUIWindow guiWindow = new GUIWindow(myGame, getStage());
                guiWindow.createVictoryWindow(foreGround, backGround, effectGroup);
                myGame.soundPlayer.playVictorySound();
                boardState = BoardState.STATE_INACTIVE;
            }
            else if( player.getHealth() <= 0 ) {
                GUIWindow guiWindow = new GUIWindow(myGame, getStage());
                guiWindow.createDefeatWindow(foreGround, backGround, effectGroup);
                myGame.soundPlayer.playLoseSound();
                boardState = BoardState.STATE_INACTIVE;
            }

            if( justSwapped ) {
                justSwapped = false;
                enemy.turn();
            }
            boardState = BoardState.STATE_IDLE;
        } else if( !stillMovement && boardState == BoardState.STATE_MOVING ) {
            boardState = BoardState.STATE_CHECK;
        }
    }

    public void swapTo(Vector2 flingStartPosition, int x, int y) {
        if (boardState == BoardState.STATE_IDLE) {
            lastX = x;
            lastY = y;
            justSwapped = true;
            matchesDuringCurrentMove = 0;
            GridPoint2 start = convertToBoardIndex(flingStartPosition);
            lastSwap = start;
            swapController.swap(start, x, y);
            boardState = BoardState.STATE_SWAPPING;
        }
    }

    private GridPoint2 convertToBoardIndex(Vector2 position) {
        position.x -= CELL_PAD_X;
        position.x /= CELL_SIZE;
        position.y -= CELL_PAD_Y;
        position.y /= CELL_SIZE;
        int gemX = (int) position.x, gemY = (int) position.y;
        GridPoint2 point = new GridPoint2(gemX, gemY);
        return point;
    }

    public void act(float delta) {
        super.act(delta);
    }

    public void draw(SpriteBatch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        enemy.draw(batch, parentAlpha);
    }

    private enum BoardState {
        STATE_EMPTY,
        STATE_IDLE,
        STATE_SWAPPING,
        STATE_CHECK,
        STATE_FADING,
        STATE_MOVING,
        STATE_INACTIVE
    }

}
