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
    private float GEM_SPEED = 0.125f;

    private enum BoardState {
        WAITING,
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
    }

    public void init() {
        board = new Cell[MAX_SIZE_X][MAX_SIZE_Y];
        backGroundImage = new Image(myGame.assets.get("data/background.png", Texture.class));
        backGroundImage.setBounds(0, 0, myGame.VIRTUAL_WIDTH, myGame.VIRTUAL_HEIGHT);
        backGround.addActor(backGroundImage);
        for (int x = 0; x < MAX_SIZE_X; x++) {
            for (int y = 0; y < MAX_SIZE_Y; y++) {
                board[x][y] = new Cell(myGame.assets.get("data/cell_back.png", Texture.class));
                board[x][y].setPosition(PADDING_LEFT + (x * CELL_SIZE), PADDING_BOT + (y * CELL_SIZE));
                board[x][y].setColor(1f, 1f, 1f, 0.33f);

                Random r = new Random();
                board[x][y].setOccupant(new Gem(myGame, GemType.values()[r.nextInt(6)]));
                board[x][y].getOccupant().setPosition(PADDING_LEFT + (x * CELL_SIZE), PADDING_BOT + (y * CELL_SIZE));
                board[x][y].setMarkedForRemoval(false);

                backGround.addActor(board[x][y]);
                foreGround.addActor(board[x][y].getOccupant());
            }
        }
        boardState = BoardState.WAITING;
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
        GridPoint2 boardLocation = convertToBoardIndex(flingStartPosition);
        board[boardLocation.x][boardLocation.y].getOccupant().addAction(Actions.moveBy(CELL_SIZE * x, CELL_SIZE * y, GEM_SPEED));
        board[boardLocation.x + x][boardLocation.y + y].getOccupant().addAction(Actions.moveBy(-(CELL_SIZE * x), -(CELL_SIZE * y), GEM_SPEED));
        Gem oldOccupant = board[boardLocation.x][boardLocation.y].getOccupant();
        board[boardLocation.x][boardLocation.y].setOccupant(board[boardLocation.x + x][boardLocation.y + y].getOccupant());
        board[boardLocation.x + x][boardLocation.y + y].setOccupant(oldOccupant);
        boardState = BoardState.MOVING;
    }

    public void update(float delta) {
        updateBoardState();
        if (boardState == BoardState.WAITING) {
            markHits();
            removeMarkedGems();
        }
    }

    private void removeMarkedGems() {
        for (int x = 0; x < MAX_SIZE_X; x++) {
            for (int y = 0; y < MAX_SIZE_Y; y++) {
                if (board[x][y].isMarkedForRemoval()) {
                    board[x][y].setMarkedForRemoval(false);
                    board[x][y].setEmpty(true);
                    applyForceToGemsAbove(x, y);
                    foreGround.removeActor(board[x][y].getOccupant());
                }
            }
        }
    }

    private void applyForceToGemsAbove(int x, int y) {
        int startAtY = y + 1;
        if (startAtY >= MAX_SIZE_Y)
            return;

        for (; startAtY < MAX_SIZE_Y; startAtY++) {
            if (board[x][startAtY].isMarkedForRemoval() || board[x][startAtY].isEmpty())
                continue;
            board[x][startAtY].getOccupant().addAction(Actions.moveBy(0f, -CELL_SIZE, GEM_SPEED));
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
            boardState = BoardState.WAITING;
        }
    }

    private void markHits() {
        for (int x = 0; x < MAX_SIZE_X; x++) {
            for (int y = 0; y < MAX_SIZE_Y; y++) {
                int countSame = howManyMatchesToTheRight(x, y);

                if (countSame >= 3) {
                    for (int delta = 0; delta < countSame; delta++) {
                        if (!board[x + delta][y].isEmpty()) {
                            board[x + delta][y].setMarkedForRemoval(true);
                        }
                    }
                }
                countSame = howManyMatchesToTheTop(x, y);

                if (countSame >= 3) {
                    for (int delta = 0; delta < countSame; delta++) {
                        if (!board[x][y + delta].isEmpty()) {
                            board[x][y + delta].setMarkedForRemoval(true);
                        }
                    }
                }
            }
        }
    }

    private int howManyMatchesToTheTop(int x, int y) {
        GemType currentType = board[x][y].getOccupant().getGemType();
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
