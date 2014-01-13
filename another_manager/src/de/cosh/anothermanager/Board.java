package de.cosh.anothermanager;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.sun.org.apache.bcel.internal.generic.SWAP;

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
    private Random r;
    private Group backGround;
    private Group foreGround;
    private SwapController swapController;
    private MatchFinder matchFinder;
    private boolean initialized;
    private BoardState boardState;

    public Board(AnotherManager myGame) {
        this.myGame = myGame;
        cells = new Cell[MAX_SIZE_X][MAX_SIZE_Y];
        swapController = new SwapController(cells);
        matchFinder = new MatchFinder(cells);
        r = new Random();
        backGround = new Group();
        backGround.setBounds(0, 0, myGame.VIRTUAL_WIDTH, myGame.VIRTUAL_HEIGHT);
        foreGround = new Group();
        foreGround.setBounds(0, 0, myGame.VIRTUAL_WIDTH, myGame.VIRTUAL_HEIGHT);
        addActor(backGround);
        addActor(foreGround);
        boardState = BoardState.STATE_EMPTY;
        initialized = false;
    }

    private void fillWithRandomGems() {
        for (int x = 0; x < MAX_SIZE_X; x++) {
            for (int y = 0; y < MAX_SIZE_Y; y++) {
                cells[x][y] = new Cell(myGame);
                cells[x][y].setPosition(CELL_PAD_X + (x * CELL_SIZE), CELL_PAD_Y + (y * CELL_SIZE));
                cells[x][y].putGem(new Gem(myGame, Gem.GemType.values()[r.nextInt(6)]));
                backGround.addActor(cells[x][y]);
                foreGround.addActor(cells[x][y].getGem());
            }
        }
    }

    public void update(float delta) {
        if (!initialized) {
            initialized = true;
            fillWithRandomGems();
            boardState = BoardState.STATE_IDLE;
        }

        updateBoardState();

        if( boardState == BoardState.STATE_IDLE ) {
            boolean boardHasMatches = matchFinder.checkForMatches();
            matchFinder.markAllMatchingGems();
        }
    }

    private void updateBoardState() {
        boolean stillMovement = false;
        for( int x = 0; x < MAX_SIZE_X; x++ ) {
            for( int y = 0; y < MAX_SIZE_Y; y++ ) {
                Gem gem = cells[x][y].getGem();
                if( gem.getActions().size > 0 ) {
                    stillMovement = true;
                }
            }
        }
        if( !stillMovement )
            boardState = BoardState.STATE_IDLE;
    }

    public void swapTo(Vector2 flingStartPosition, int x, int y) {
        if (boardState == BoardState.STATE_IDLE) {
            GridPoint2 start = convertToBoardIndex(flingStartPosition);
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

    private enum BoardState {
        STATE_EMPTY,
        STATE_IDLE,
        STATE_SWAPPING
    }

}
