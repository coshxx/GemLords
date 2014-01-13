package de.cosh.anothermanager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

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
    private CellOld[][] board;
    private Image backGroundImage;
    private Group foreGround, backGround, windowGroup;
    private Random r = new Random();
    private int hits_during_current_move;

    private BoardControllerOld boardControllerOld;
    private MatchFinderOld matchFinderOld;

    private int INDEV_MAX_DIFFERENT_GEMS = 6;

    private boolean justSwapped;
    private Vector2 lastFlingPosition;
    private GridPoint2 lastSwapDirection;

    private Player player;
    private Enemy enemy;

    private boolean showingWindow;

    public void dispose() {
        foreGround.clear();
        backGround.clear();
        windowGroup.clear();
    }

    private enum BoardState {
        IDLE,
        CHECK,
        MOVING,
        FADING
    }

    private BoardState boardState;

    public SwapGame(AnotherManager game) {
        myGame = game;
        setBounds(0, 0, myGame.VIRTUAL_WIDTH, myGame.VIRTUAL_HEIGHT);
        setClip(true);

        justSwapped = false;

        boardControllerOld = new BoardControllerOld();
        boardControllerOld.init(PADDING_BOT, PADDING_LEFT, CELL_SIZE, INDEV_MAX_DIFFERENT_GEMS, MAX_SIZE_X, MAX_SIZE_Y, myGame);

        matchFinderOld = new MatchFinderOld();
    }

    public void init() {
        foreGround = new Group();
        backGround = new Group();
        windowGroup = new Group();

        backGround.setBounds(0, 0, myGame.VIRTUAL_WIDTH, myGame.VIRTUAL_HEIGHT);
        foreGround.setBounds(0, 0, myGame.VIRTUAL_WIDTH, myGame.VIRTUAL_HEIGHT);
        windowGroup.setBounds(0, 0, myGame.VIRTUAL_WIDTH, myGame.VIRTUAL_HEIGHT);

        backGroundImage = new Image(myGame.assets.get("data/background.png", Texture.class));
        backGroundImage.setBounds(0, 0, myGame.VIRTUAL_WIDTH, myGame.VIRTUAL_HEIGHT);
        backGround.addActor(backGroundImage);
        board = boardControllerOld.fillBoard(backGround, foreGround);
        matchFinderOld.init(board, MAX_SIZE_X, MAX_SIZE_Y);


        while (matchFinderOld.findMatches(board, false)) {
            backGround.clear();
            foreGround.clear();
            windowGroup.clear();
            backGround.addActor(backGroundImage);
            board = boardControllerOld.fillBoard(backGround, foreGround);
            matchFinderOld.init(board, MAX_SIZE_X, MAX_SIZE_Y);
        }

        player = myGame.player;
        player.init(200);
        player.setHealthBarPosition(PADDING_LEFT, PADDING_BOT - 40, (CELL_SIZE * MAX_SIZE_X), 25);

        foreGround.addActor(enemy.getImage());
        foreGround.addActor(enemy.getHealthBar());
        foreGround.addActor(player.getHealthBar());

        boardState = BoardState.IDLE;
        hits_during_current_move = 0;
        showingWindow = false;

        addActor(backGround);
        addActor(foreGround);
        addActor(windowGroup);
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
        if (boardState != BoardState.IDLE && boardState != BoardState.CHECK)
            return;

        player.damage(5);
        justSwapped = true;
        lastFlingPosition = new Vector2(flingStartPosition);
        GridPoint2 start = convertToBoardIndex(flingStartPosition);
        lastSwapDirection = new GridPoint2(x, y);
        boardControllerOld.swapCellTo(start, x, y);
        boardState = BoardState.MOVING;
    }

    public void update(float delta) {
        if (!showingWindow) {
            if (boardState == BoardState.IDLE || boardState == BoardState.CHECK) {
                markHits();
                fadeMarkedGems();
            }
            updateBoardState();
            updateGameState();
        }
    }

    private void updateGameState() {
        if (player.getHealth() <= 0 && !showingWindow) {
            showingWindow = true;
            myGame.soundPlayer.playLoseSound();
            GUIWindow guiWindow = new GUIWindow(myGame, this.getStage());
            guiWindow.createDefeatWindow(foreGround, backGround, windowGroup);
            player.decreaseLife();
        }

        if (enemy.getHealth() <= 0 && !showingWindow) {
            showingWindow = true;
            myGame.soundPlayer.playVictorySound();
            GUIWindow guiWindow = new GUIWindow(myGame, this.getStage());
            guiWindow.createVictoryWindow(foreGround, backGround, windowGroup);
            enemy.setDefeated(true);
        }
    }

    private void respawnMissingGems() {
        boardControllerOld.respawnMissingGems(foreGround);
    }

    private void applyFallingMovement() {
        boolean neededToMoveOne = boardControllerOld.applyFallingActionToGems();

        if (neededToMoveOne)
            boardState = BoardState.MOVING;
    }

    private void fadeMarkedGems() {
        MatchResult r = boardControllerOld.playFadingAnimationOnMarkedGems(foreGround);

        if (r.howMany > 0) {
            boardState = BoardState.FADING;
            if( r.specialExplo )
                myGame.soundPlayer.playBang();
            else myGame.soundPlayer.playDing(hits_during_current_move);
            hits_during_current_move++;
            enemy.damage(r.howMany);
        }
    }

    private void updateBoardState() {
        boolean hadActions = false;
        for (int x = 0; x < MAX_SIZE_X; x++) {
            for (int y = 0; y < MAX_SIZE_Y; y++) {
                if (board[x][y].getOccupant().getActions().size > 0)
                    hadActions = true;
            }
        }
        if (!hadActions && boardState == BoardState.FADING) {
            boardControllerOld.removeMarkedGems(foreGround);
            applyFallingMovement();
            respawnMissingGems();
            boardState = BoardState.MOVING;

        } else if (!hadActions && boardState == BoardState.MOVING) {
            boardState = BoardState.CHECK;
        } else if (!hadActions && boardState == BoardState.CHECK) {
            if (hits_during_current_move > 3) {
                myGame.soundPlayer.playAwesome();
                Image awesome = new Image(myGame.assets.get("data/awesome.png", Texture.class));
                awesome.setPosition(myGame.VIRTUAL_WIDTH, myGame.VIRTUAL_HEIGHT/2);
                // TODO: hardcoded shit
                awesome.addAction(Actions.sequence(Actions.moveBy(-myGame.VIRTUAL_WIDTH*0.845f, 0, 0.15f), Actions.delay(1f), Actions.moveBy(-myGame.VIRTUAL_WIDTH, 0, 0.15f), Actions.removeActor(awesome)));
                foreGround.addActor(awesome);
            }
            hits_during_current_move = 0;
            boardState = BoardState.IDLE;
            for (int x = 0; x < MAX_SIZE_X; x++) {
                for (int y = 0; y < MAX_SIZE_Y; y++) {
                    board[x][y].getOccupant().setMoved(false);
                }
            }
        }
    }

    private void markHits() {
        boolean markedSome = matchFinderOld.findMatches(board, true);
        if (!markedSome) {
            if (justSwapped) {
                myGame.soundPlayer.playSwapError();
                swapTo(lastFlingPosition, lastSwapDirection.x, lastSwapDirection.y);
                boardState = BoardState.MOVING;
            }
        }
        justSwapped = false;
    }

    public void setupEnemy(int hp) {
        enemy = new Enemy(myGame, myGame.assets.get("data/enemy.png", Texture.class));
        enemy.setHealth(hp);
        enemy.setHealthBarPosition(PADDING_LEFT, PADDING_BOT + (CELL_SIZE * MAX_SIZE_Y) + 360, (CELL_SIZE * MAX_SIZE_X), 25);
        enemy.setPosition(PADDING_LEFT + 250, PADDING_BOT + (CELL_SIZE * MAX_SIZE_Y) + 400);
    }
}


