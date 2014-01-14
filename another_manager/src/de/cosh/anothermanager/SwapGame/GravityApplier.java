package de.cosh.anothermanager.SwapGame;

/**
 * Created by cosh on 13.01.14.
 */
public class GravityApplier {
    private Cell[][] cells;

    public GravityApplier(Cell[][] cells) {
        this.cells = cells;
    }

    public void applyGravity() {
        for (int x = 0; x < Board.MAX_SIZE_X; x++) {
            for (int y = 0; y < Board.MAX_SIZE_Y; y++) {
                Gem gem = cells[x][y].getGem();
                if (gem.isTypeNone()) {
                    for (int d = y; d < Board.MAX_SIZE_Y; d++) {
                        Gem fall = cells[x][d].getGem();
                        if (fall.isTypeNone())
                            continue;
                        fall.fallBy(0, -(d - y));
                        fall.setMoving(true);

                        Gem temp = cells[x][y].getGem();
                        cells[x][y].setGem(fall);
                        cells[x][d].setGem(temp);
                        break;
                    }
                }
            }
        }
    }
}
