package de.cosh.anothermanager;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by cosh on 13.01.14.
 */
public class SwapController {
    private Cell[][] cells;
    private Vector2 lastFlingPosition;


    public SwapController(Cell[][] cells) {
        this.cells = cells;
    }

    public void swap(GridPoint2 start, int x, int y) {
        Gem firstGem = cells[start.x][start.y].getGem();
        Gem secondGem = cells[start.x + x][start.y + y].getGem();

        firstGem.moveTo(x, y);
        secondGem.moveTo(-x, -y);

        cells[start.x][start.y].setGem(secondGem);
        cells[start.x+x][start.y+y].setGem(firstGem);
    }
}
