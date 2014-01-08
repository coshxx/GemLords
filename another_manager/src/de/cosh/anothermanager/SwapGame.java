package de.cosh.anothermanager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.*;

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
    private Group foreGround, backGround, windowGroup;
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

    private boolean showingWindow;

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
        foreGround = new Group();
        backGround = new Group();
        windowGroup = new Group();

        backGround.setBounds(0, 0, myGame.VIRTUAL_WIDTH, myGame.VIRTUAL_HEIGHT);
        foreGround.setBounds(0, 0, myGame.VIRTUAL_WIDTH, myGame.VIRTUAL_HEIGHT);
        windowGroup.setBounds(0, 0, myGame.VIRTUAL_WIDTH, myGame.VIRTUAL_HEIGHT);

        addActor(backGround);
        addActor(foreGround);
        addActor(windowGroup);

        justSwapped = false;

        boardController = new BoardController();
        boardController.init(PADDING_BOT, PADDING_LEFT, CELL_SIZE, INDEV_MAX_DIFFERENT_GEMS, MAX_SIZE_X, MAX_SIZE_Y, myGame);

        matchFinder = new MatchFinder();
        showingWindow = false;
    }

    public void init() {
        backGroundImage = new Image(myGame.assets.get("data/background.png", Texture.class));
        backGroundImage.setBounds(0, 0, myGame.VIRTUAL_WIDTH, myGame.VIRTUAL_HEIGHT);
        backGround.addActor(backGroundImage);
        board = boardController.fillBoard(backGround, foreGround);
        matchFinder.init(board, MAX_SIZE_X, MAX_SIZE_Y);

        while (matchFinder.findMatches(board, false)) {
            backGround.clear();
            foreGround.clear();
            backGround.addActor(backGroundImage);
            board = boardController.fillBoard(backGround, foreGround);
            matchFinder.init(board, MAX_SIZE_X, MAX_SIZE_Y);
        }

        player = myGame.player;
        player.init();
        player.setHealthBarPosition(PADDING_LEFT, PADDING_BOT - 40, (CELL_SIZE * MAX_SIZE_X), 25);

        enemy = new Enemy(myGame.assets.get("data/enemy.png", Texture.class), myGame);
        enemy.init();
        enemy.setHealthBarPosition(PADDING_LEFT, PADDING_BOT + (CELL_SIZE * MAX_SIZE_Y) + 360, (CELL_SIZE * MAX_SIZE_X), 25);
        enemy.setPosition(PADDING_LEFT + 250, PADDING_BOT + (CELL_SIZE * MAX_SIZE_Y) + 400);

        foreGround.addActor(enemy);
        foreGround.addActor(player);

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
        if (boardState != BoardState.IDLE && boardState != BoardState.CHECK)
            return;

        player.damage(5);
        justSwapped = true;
        lastFlingPosition = new Vector2(flingStartPosition);
        GridPoint2 start = convertToBoardIndex(flingStartPosition);
        lastSwapDirection = new GridPoint2(x, y);
        boardController.swapCellTo(start, x, y);
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
            Window.WindowStyle style = new Window.WindowStyle();
            style.titleFont = myGame.assets.get("data/font.fnt", BitmapFont.class);
            style.titleFont.setScale(1f);
            Texture nTexture = new Texture("data/menuskin.png");
            NinePatch nPatch = new NinePatch(new TextureRegion(nTexture, 24, 24), 8, 8, 8, 8);
            style.background = new NinePatchDrawable(nPatch);

            Window window = new Window("You lose", style);
            window.setPosition(200, 200);
            window.setColor(1.0f, 1.0f, 1.0f, 0.95f);
            window.setMovable(true);
            window.pad(20, 0, 0, 0);
            window.setModal(false);
            window.align(Align.center);
            window.setTitleAlignment(Align.center);
            window.setSize(200, 200);

            TextButton button;
            TextButton.TextButtonStyle tStyle;
            tStyle = new TextButton.TextButtonStyle();
            Texture buttonTexture = myGame.assets.get("data/button.png", Texture.class);
            BitmapFont buttonFont = new BitmapFont();
            tStyle.up = new TextureRegionDrawable(new TextureRegion(buttonTexture));
            tStyle.down = new TextureRegionDrawable(new TextureRegion(buttonTexture));
            tStyle.font = buttonFont;

            button = new TextButton("Map", tStyle);
            button.setPosition(50, 50);
            button.setSize(100, 100);
            Gdx.input.setInputProcessor(this.getStage());
            button.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    fadeToMap();
                }
            });
            window.addActor(button);

            foreGround.addAction(Actions.alpha(0.5f, 1.0f));
            backGround.addAction(Actions.alpha(0.5f, 1.0f));
            windowGroup.addActor(window);
        }

        if (enemy.getHealth() <= 0 && !showingWindow) {
            showingWindow = true;
            Window.WindowStyle style = new Window.WindowStyle();
            style.titleFont = myGame.assets.get("data/font.fnt", BitmapFont.class);
            style.titleFont.setScale(1f);
            Texture nTexture = new Texture("data/menuskin.png");
            NinePatch nPatch = new NinePatch(new TextureRegion(nTexture, 24, 24), 8, 8, 8, 8);
            style.background = new NinePatchDrawable(nPatch);

            Window window = new Window("You win", style);
            window.setPosition(200, 200);
            window.setColor(1.0f, 1.0f, 1.0f, 0.95f);
            window.setMovable(true);
            window.pad(20, 0, 0, 0);
            window.setModal(false);
            window.align(Align.center);
            window.setTitleAlignment(Align.center);
            window.setSize(200, 200);

            TextButton button;
            TextButton.TextButtonStyle tStyle;
            tStyle = new TextButton.TextButtonStyle();
            Texture buttonTexture = myGame.assets.get("data/button.png", Texture.class);
            BitmapFont buttonFont = new BitmapFont();
            tStyle.up = new TextureRegionDrawable(new TextureRegion(buttonTexture));
            tStyle.down = new TextureRegionDrawable(new TextureRegion(buttonTexture));
            tStyle.font = buttonFont;

            button = new TextButton("Loot", tStyle);
            button.setPosition(50, 50);
            button.setSize(100, 100);
            Gdx.input.setInputProcessor(this.getStage());
            button.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    fadeToLoot();
                }
            });
            window.addActor(button);

            foreGround.addAction(Actions.alpha(0.5f, 1.0f));
            backGround.addAction(Actions.alpha(0.5f, 1.0f));
            windowGroup.addActor(window);
        }

    }

    private void fadeToMap() {
        this.addAction(Actions.sequence(Actions.fadeOut(1f), Actions.run(new Runnable() {
            @Override
            public void run() {
                myGame.setScreen(myGame.mapTraverseScreen);
            }
        })));
    }

    private void fadeToLoot() {
        this.addAction(Actions.sequence(Actions.fadeOut(1f), Actions.run(new Runnable() {
            @Override
            public void run() {
                myGame.setScreen(myGame.lootScreen);
            }
        })));
    }

    private void respawnMissingGems() {
        boardController.respawnMissingGems(foreGround);
    }

    private void applyFallingMovement() {
        boolean neededToMoveOne = boardController.applyFallingActionToGems();

        if (neededToMoveOne)
            boardState = BoardState.MOVING;
    }

    private void fadeMarkedGems() {
        int didSome = boardController.playFadingAnimationOnMarkedGems(foreGround);

        if (didSome > 0) {
            boardState = BoardState.FADING;
            myGame.soundPlayer.playDing(hits_during_current_move);
            hits_during_current_move++;
            enemy.damage(didSome);
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
            boardController.removeMarkedGems(foreGround);
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
        }
    }

    private void markHits() {
        boolean markedSome = matchFinder.findMatches(board, true);
        if (!markedSome) {
            if (justSwapped) {
                myGame.soundPlayer.playSwapError();
                swapTo(lastFlingPosition, lastSwapDirection.x, lastSwapDirection.y);
                boardState = BoardState.MOVING;
            }
        }
        justSwapped = false;
    }
}


