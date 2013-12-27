package de.cosh.anothermanager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import de.cosh.anothermanager.Gem.GemType;

import java.util.Random;

/**
 * Created by cosh on 27.12.13.
 */
public class BoardController {

    private Cell[][] currentBoard;
    private int PADDING_BOT, PADDING_LEFT, CELL_SIZE, MAX_DIFFERENT_GEMS, MAX_SIZE_X, MAX_SIZE_Y;
    private AnotherManager myGame;
    private float GEM_SPEED = 0.20f;

    public void init(int PADDING_BOT, int PADDING_LEFT, int CELL_SIZE, int MAX_DIFFERENT_GEMS, int MAX_SIZE_X, int MAX_SIZE_Y, AnotherManager myGame) {
        this.PADDING_BOT = PADDING_BOT;
        this.PADDING_LEFT = PADDING_LEFT;
        this.CELL_SIZE = CELL_SIZE;
        this.MAX_DIFFERENT_GEMS = MAX_DIFFERENT_GEMS;
        this.MAX_SIZE_X = MAX_SIZE_X;
        this.MAX_SIZE_Y = MAX_SIZE_Y;
        this.myGame = myGame;
    }

    public Cell[][] fillBoard(Group backGround, Group foreGround) {
        currentBoard = new Cell[MAX_SIZE_X][MAX_SIZE_Y];
        Random r = new Random();
        for (int x = 0; x < MAX_SIZE_X; x++) {
            for (int y = 0; y < MAX_SIZE_Y; y++) {
                currentBoard[x][y] = new Cell(myGame.assets.get("data/cell_back.png", Texture.class));
                currentBoard[x][y].setPosition(PADDING_LEFT + (x * CELL_SIZE), PADDING_BOT + (y * CELL_SIZE));
                currentBoard[x][y].setColor(1f, 1f, 1f, 0.33f);
                backGround.addActor(currentBoard[x][y]);

                currentBoard[x][y].setOccupant(new Gem(myGame, GemType.values()[r.nextInt(MAX_DIFFERENT_GEMS)]));
                currentBoard[x][y].getOccupant().setPosition(PADDING_LEFT + (x * CELL_SIZE), PADDING_BOT + (y * CELL_SIZE));
                foreGround.addActor(currentBoard[x][y].getOccupant());
            }
        }
        return currentBoard;
    }

    public void swapCellTo(GridPoint2 cell, int x, int y) {

        GridPoint2 start = cell;
        GridPoint2 end = new GridPoint2(start.x + x, start.y + y);

        Gem startOccupant = currentBoard[start.x][start.y].getOccupant(), endOccupant = currentBoard[end.x][end.y].getOccupant();

        if (startOccupant.getGemType() == GemType.TYPE_NONE || endOccupant.getGemType() == GemType.TYPE_NONE)
            return;

        currentBoard[start.x][start.y].getOccupant().addAction(Actions.moveBy(CELL_SIZE * x, CELL_SIZE * y, GEM_SPEED));
        currentBoard[end.x][end.y].getOccupant().addAction(Actions.moveBy(-(CELL_SIZE * x), -(CELL_SIZE * y), GEM_SPEED));
        Gem oldOccupant = startOccupant;
        currentBoard[start.x][start.y].setOccupant(currentBoard[end.x][end.y].getOccupant());
        currentBoard[end.x][end.y].setOccupant(oldOccupant);
    }

}
