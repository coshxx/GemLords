package de.cosh.anothermanager;

import com.badlogic.gdx.graphics.Texture;
import de.cosh.anothermanager.Gem.GemType;

import java.util.Random;

/**
 * Created by cosh on 27.12.13.
 */
public class BoardFiller {

    public Cell[][] fillBoard(int MAX_SIZE_X, int MAX_SIZE_Y, int PADDING_LEFT, int PADDING_BOT, int CELL_SIZE, int INDEV_MAX_DIFFERENT_GEMS, AnotherManager myGame) {
        Cell[][] board = new Cell[MAX_SIZE_X][MAX_SIZE_Y];
        Random r = new Random();
        for (int x = 0; x < MAX_SIZE_X; x++) {
            for (int y = 0; y < MAX_SIZE_Y; y++) {
                board[x][y] = new Cell(myGame.assets.get("data/cell_back.png", Texture.class));
                board[x][y].setPosition(PADDING_LEFT + (x * CELL_SIZE), PADDING_BOT + (y * CELL_SIZE));
                board[x][y].setColor(1f, 1f, 1f, 0.33f);

                board[x][y].setOccupant(new Gem(myGame, GemType.values()[r.nextInt(INDEV_MAX_DIFFERENT_GEMS)]));
                board[x][y].getOccupant().setPosition(PADDING_LEFT + (x * CELL_SIZE), PADDING_BOT + (y * CELL_SIZE));

            }
        }
        return board;
    }
}
