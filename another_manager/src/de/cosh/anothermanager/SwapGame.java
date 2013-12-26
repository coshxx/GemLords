package de.cosh.anothermanager;

import com.badlogic.gdx.graphics.Texture;
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

    private int INDEV_MAX_DIFFERENT_GEMS = 6;

    private boolean justSwapped;
    private Vector2 lastFlingPosition;
    private GridPoint2 lastSwapDirection;

    private enum BoardState {
        IDLE,
        MOVING
    }

    private BoardState boardState;

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
    }

    public void init() {
        // le merge
        board = new Cell[MAX_SIZE_X][MAX_SIZE_Y];
        backGroundImage = new Image(myGame.assets.get("data/background.png", Texture.class));
        backGroundImage.setBounds(0, 0, myGame.VIRTUAL_WIDTH, myGame.VIRTUAL_HEIGHT);
        backGround.addActor(backGroundImage);
        for (int x = 0; x < MAX_SIZE_X; x++) {
            for (int y = 0; y < MAX_SIZE_Y; y++) {
                board[x][y] = new Cell(myGame.assets.get("data/cell_back.png", Texture.class));
                board[x][y].setPosition(PADDING_LEFT + (x * CELL_SIZE), PADDING_BOT + (y * CELL_SIZE));
                board[x][y].setColor(1f, 1f, 1f, 0.33f);

                board[x][y].setOccupant(new Gem(myGame, GemType.values()[r.nextInt(INDEV_MAX_DIFFERENT_GEMS)]));
                board[x][y].getOccupant().setPosition(PADDING_LEFT + (x * CELL_SIZE), PADDING_BOT + (y * CELL_SIZE));

                backGround.addActor(board[x][y]);
                foreGround.addActor(board[x][y].getOccupant());
            }
        }
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

        justSwapped = true;
        lastFlingPosition = new Vector2(flingStartPosition);
        GridPoint2 start = convertToBoardIndex(flingStartPosition);
        GridPoint2 end = new GridPoint2(start.x + x, start.y + y);
        lastSwapDirection = new GridPoint2(x, y);
        hits_during_current_move = 0;

        Gem startOccupant = board[start.x][start.y].getOccupant(), endOccupant = board[end.x][end.y].getOccupant();

        if (startOccupant.getGemType() == GemType.TYPE_NONE || endOccupant.getGemType() == GemType.TYPE_NONE)
            return;

        board[start.x][start.y].getOccupant().addAction(Actions.moveBy(CELL_SIZE * x, CELL_SIZE * y, GEM_SPEED));
        board[end.x][end.y].getOccupant().addAction(Actions.moveBy(-(CELL_SIZE * x), -(CELL_SIZE * y), GEM_SPEED));
        Gem oldOccupant = startOccupant;
        board[start.x][start.y].setOccupant(board[end.x][end.y].getOccupant());
        board[end.x][end.y].setOccupant(oldOccupant);
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
        for (int x = 0; x < MAX_SIZE_X; x++) {
            int counter = 0;
            for (int y = 0; y < MAX_SIZE_Y; y++) {
                Gem currentGem = board[x][y].getOccupant();

                if (currentGem.getGemType() == GemType.TYPE_NONE) {
                    Gem newGem = new Gem(myGame, GemType.values()[r.nextInt(INDEV_MAX_DIFFERENT_GEMS)]);
                    newGem.setPosition(PADDING_LEFT + (x * CELL_SIZE), PADDING_BOT + (((MAX_SIZE_Y) + counter) * CELL_SIZE));
                    counter++;
                    newGem.addAction(Actions.moveTo(PADDING_LEFT + (x * CELL_SIZE), PADDING_BOT + (y * CELL_SIZE), GEM_SPEED * (((MAX_SIZE_Y - 1) + counter) - (y))));
                    foreGround.addActor(newGem);
                    board[x][y].setOccupant(newGem);
                }
            }
        }
    }

    private void applyFallingMovement() {
        boolean neededToMoveOne = false;

        for (int x = 0; x < MAX_SIZE_X; x++) {
            for (int y = 0; y < MAX_SIZE_Y; y++) {
                Gem currentGem = board[x][y].getOccupant();

                if (currentGem.getGemType() == GemType.TYPE_NONE) {
                    for (int d = y + 1; d < MAX_SIZE_Y; d++) {
                        if (board[x][d].getOccupant().getGemType() != GemType.TYPE_NONE) {
                            neededToMoveOne = true;
                            board[x][d].getOccupant().addAction(Actions.moveBy(0, -(CELL_SIZE * (d - y)), (GEM_SPEED * (d - y))));
                            board[x][y].setOccupant(board[x][d].getOccupant());
                            board[x][d].setOccupant(currentGem);
                            break;
                        }
                    }
                }
            }
        }
        if (neededToMoveOne)
            boardState = BoardState.MOVING;
    }

    private void removeMarkedGems() {
        boolean hadToRemoveSome = false;
        for (int x = 0; x < MAX_SIZE_X; x++) {
            for (int y = 0; y < MAX_SIZE_Y; y++) {
                if (board[x][y].isMarkedForRemoval()) {
                    hadToRemoveSome = true;
                    foreGround.removeActor(board[x][y].getOccupant());
                    board[x][y].getOccupant().setToNoGem();
                    board[x][y].unmarkForRemoval();
                }
            }
        }

        if (hadToRemoveSome) {
            boardState = BoardState.MOVING;
            myGame.soundPlayer.playDing(hits_during_current_move);
            hits_during_current_move++;
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
        boolean markedSome = false;

        for (int x = 0; x < MAX_SIZE_X; x++) {
            for (int y = 0; y < MAX_SIZE_Y; y++) {
                int result = howManyMatchesToTheRight(x, y);

                if (result >= 3) {
                    for (int d = 0; d < result; d++) {
                        board[x + d][y].markForRemoval();
                        markedSome = true;
                    }
                }
                result = howManyMatchesToTheTop(x, y);
                if (result >= 3) {
                    for (int d = 0; d < result; d++) {
                        board[x][y + d].markForRemoval();
                        markedSome = true;
                    }
                }
            }
        }
        if( !markedSome ) {
            if( justSwapped ) {
                myGame.soundPlayer.playSwapError();
                swapTo(lastFlingPosition, lastSwapDirection.x, lastSwapDirection.y);
                boardState = BoardState.MOVING;
            }
        }
        justSwapped = false;
    }

    private int howManyMatchesToTheTop(int x, int y) {
        GemType currentType = board[x][y].getOccupant().getGemType();
        if (currentType == GemType.TYPE_NONE)
            return 0;
        int searchPos = y;
        int count = 1;

        while (true) {
            if (searchPos + 1 >= MAX_SIZE_Y)
                break;
            if (board[x][searchPos + 1].getOccupant().getGemType() == currentType) {
                count++;
                searchPos++;
            } else {
                break;
            }
        }
        return count;
    }

    private int howManyMatchesToTheRight(int x, int y) {
        GemType currentType = board[x][y].getOccupant().getGemType();
        int searchPos = x;
        int count = 1;

        while (true) {
            if (searchPos + 1 >= MAX_SIZE_X)
                break;
            if (board[searchPos + 1][y].getOccupant().getGemType() == currentType) {
                count++;
                searchPos++;
            } else {
                break;
            }
        }
        return count;
    }
}
