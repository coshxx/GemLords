package de.cosh.anothermanager.SwapGame;

import com.badlogic.gdx.scenes.scene2d.Group;

import java.util.Random;

/**
 * Created by cosh on 13.01.14.
 */
public class GemRespawner {
    private Cell[][] cells;
    private Random random;
    private GemFactory gemFactory;

    public GemRespawner(Cell[][] cells, Random r, GemFactory gemFactory) {
        this.cells = cells;
        this.random = r;
        this.gemFactory = gemFactory;
    }

    public void respawn(Group foreGround) {
        int counter;
        for (int x = 0; x < Board.MAX_SIZE_X; x++) {
            counter = 0;
            for (int y = 0; y < Board.MAX_SIZE_Y; y++) {
                Gem gem = cells[x][y].getGem();
                if( gem.isTypeNone() ) {
                    Gem newGem = gemFactory.newRandomGem();
                    newGem.setPosition(Board.CELL_PAD_X + ( x * Board.CELL_SIZE ), Board.CELL_PAD_Y + ( (counter+Board.MAX_SIZE_Y) * Board.CELL_SIZE));
                    newGem.fallBy(0, -(counter + (Board.MAX_SIZE_Y - y)));
                    ++counter;
                    cells[x][y].setGem(newGem);
                    foreGround.addActor(newGem);
                }
            }
        }
    }
}
