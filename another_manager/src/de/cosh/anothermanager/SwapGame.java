package de.cosh.anothermanager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import de.cosh.anothermanager.Gem.GemType;

import java.util.Random;

/**
 * Created by cosh on 16.12.13.
 */
public class SwapGame extends Table {
    private AnotherManager myGame;
    private int MAX_SIZE_X = 9;
    private int MAX_SIZE_Y = 9;
    private int CELL_SIZE = 70;
    private int PADDING_LEFT = 45;
    private int PADDING_BOT = 45;
    private Cell[][] board;
    private Image backGroundImage;
    private Group foreGround, backGround;
    private float GEM_SPEED = 0.20f;
    private Random r = new Random();
    private int hits_during_current_move;

    private BoardController boardController;
    private MatchFinder matchFinder;

    private int INDEV_MAX_DIFFERENT_GEMS = 6;

    private boolean justSwapped;
    private Vector2 lastFlingPosition;
    private GridPoint2 lastSwapDirection;

    private Player player;
    private Enemy enemy;

    private enum BoardState {
        IDLE,
        MOVING
    }

    private BoardState boardState;

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        player.draw(batch, parentAlpha);
        enemy.draw(batch, parentAlpha);
    }

    public SwapGame(AnotherManager game) {

        myGame = game;
        setBounds(0, 0, myGame.VIRTUAL_WIDTH, myGame.VIRTUAL_HEIGHT);
        setClip(true);
        foreGround = new Group();
        backGround = new Group();

        backGround.setBounds(0, 0, myGame.VIRTUAL_WIDTH, myGame.VIRTUAL_HEIGHT);
        foreGround.setBounds(0, 0, myGame.VIRTUAL_WIDTH, myGame.VIRTUAL_HEIGHT);

        addActor(backGround);
        addActor(foreGround);

        justSwapped = false;

        boardController = new BoardController();
        boardController.init(PADDING_BOT, PADDING_LEFT, CELL_SIZE, INDEV_MAX_DIFFERENT_GEMS, MAX_SIZE_X, MAX_SIZE_Y, myGame);

        matchFinder = new MatchFinder();
        player = myGame.player;
        player.setHealthBarPosition(PADDING_LEFT, PADDING_BOT-40, (CELL_SIZE * MAX_SIZE_X), 25 );

        enemy = new Enemy(myGame.assets.get("data/enemy.png", Texture.class), myGame);
        enemy.init();
        enemy.setHealthBarPosition(PADDING_LEFT, PADDING_BOT + (CELL_SIZE*MAX_SIZE_Y) + 160, (CELL_SIZE * MAX_SIZE_X), 25);
        enemy.setPosition(PADDING_LEFT + 250, PADDING_BOT + (CELL_SIZE*MAX_SIZE_Y) + 200);

        foreGround.addActor(player);
        foreGround.addActor(enemy);
    }

    public void init() {
        backGroundImage = new Image(myGame.assets.get("data/background.png", Texture.class));
        backGroundImage.setBounds(0, 0, myGame.VIRTUAL_WIDTH, myGame.VIRTUAL_HEIGHT);
        backGround.addActor(backGroundImage);
        board = boardController.fillBoard(backGround, foreGround);
        matchFinder.init(board, MAX_SIZE_X, MAX_SIZE_Y);
        boardState = BoardState.IDLE;
        hits_during_current_move = 0;

    }

    private GridPoint2 convertToBoardIndex(Vector2 position) {
        position.x -= PADDING_LEFT;
        position.x /= CELL_SIZE;

        position.y -= PADDING_BOT;
        position.y /= CELL_SIZE;

        int gemX = (int) position.x, gemY = (int) position.y;

        GridPoint2 point = new GridPoint2(gemX, gemY);

        return point;
    }

    public void swapTo(Vector2 flingStartPosition, int x, int y) {
        if (boardState != BoardState.IDLE)
            return;

        player.damage(5);
        justSwapped = true;
        lastFlingPosition = new Vector2(flingStartPosition);
        GridPoint2 start = convertToBoardIndex(flingStartPosition);
        lastSwapDirection = new GridPoint2(x, y);
        hits_during_current_move = 0;

        boardController.swapCellTo(start, x, y);
        boardState = BoardState.MOVING;
    }

    public void update(float delta) {
        if (boardState == BoardState.IDLE) {
            markHits();
            removeMarkedGems();
            applyFallingMovement();
            respawnMissingGems();
        }
        updateBoardState();
    }

    private void respawnMissingGems() {
        boardController.respawnMissingGems(foreGround);
    }

    private void applyFallingMovement() {
        boolean neededToMoveOne = boardController.applyFallingActionToGems();

        if (neededToMoveOne)
            boardState = BoardState.MOVING;
    }

    private void removeMarkedGems() {
        int hadToRemoveSome = boardController.removeAllMarkedGems(foreGround);

        if (hadToRemoveSome > 0) {
            boardState = BoardState.MOVING;
            myGame.soundPlayer.playDing(hits_during_current_move);
            hits_during_current_move++;
            enemy.damage(hadToRemoveSome);
        }
    }

    private void updateBoardState() {
        boolean hadToMove = false;
        for (int x = 0; x < MAX_SIZE_X; x++) {
            for (int y = 0; y < MAX_SIZE_Y; y++) {
                if (board[x][y].getOccupant().getActions().size > 0)
                    hadToMove = true;
            }
        }
        if (!hadToMove) {
            boardState = BoardState.IDLE;
        }
    }

    private void markHits() {
        boolean markedSome = matchFinder.markMatches(board);
        if( !markedSome ) {
            if( justSwapped ) {
                myGame.soundPlayer.playSwapError();
                swapTo(lastFlingPosition, lastSwapDirection.x, lastSwapDirection.y);
                boardState = BoardState.MOVING;
            }
        }
        justSwapped = false;
    }
}


