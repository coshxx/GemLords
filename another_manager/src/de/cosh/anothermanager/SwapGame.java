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

                backGround.addActor(board[x][y]);
                foreGround.addActor(board[x][y].getOccupant());
            }
        }
        boardState = BoardState.IDLE;
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
        if (boardState == BoardState.IDLE) {
            markHits();
            removeMarkedGems();
        }
        updateBoardState();
    }

    private void removeMarkedGems() {
        for (int x = 0; x < MAX_SIZE_X; x++) {
            for (int y = 0; y < MAX_SIZE_Y; y++) {
                if (board[x][y].isMarkedForRemoval()) {
                    foreGround.removeActor(board[x][y].getOccupant());
                    board[x][y].getOccupant().setToNoGem();
                }
            }
        }
    }

    private void applyForceToGemsAbove(int x, int y) {
    }

    private void updateBoardState() {
        boolean hadToMove = false;
        for( int x = 0; x < MAX_SIZE_X; x++ ) {
            for( int y = 0; y < MAX_SIZE_Y; y++ ) {
                if( board[x][y].getOccupant().getActions().size > 0 )
                    hadToMove = true;
            }
        }
        if( !hadToMove )
            boardState = BoardState.IDLE;
    }

    private void markHits() {
        for (int x = 0; x < MAX_SIZE_X; x++) {
            for (int y = 0; y < MAX_SIZE_Y; y++) {
                int result = howManyMatchesToTheRight(x, y);
                if (result >= 3) {
                    for (int d = 0; d < result; d++) {
                        board[x + d][y].markForRemoval();
                    }
                }
                result = howManyMatchesToTheTop(x, y);
                if (result >= 3) {
                    for (int d = 0; d < result; d++) {
                        board[x][y + d].markForRemoval();
                    }
                }
            }
        }
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
