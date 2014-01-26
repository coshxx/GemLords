package de.cosh.anothermanager.SwapGame;

import java.util.Random;

import com.badlogic.gdx.scenes.scene2d.Group;

/**
 * Created by cosh on 13.01.14.
 */
public class GemRespawner {
	private final Cell[][] cells;
	private final GemFactory gemFactory;
	public GemRespawner(final Cell[][] cells, final Random r, final GemFactory gemFactory) {
		this.cells = cells;
		this.gemFactory = gemFactory;
	}

	public void respawn(final Group foreGround) {
		int counter;
        boolean disabledFound = false;
		for (int x = 0; x < Board.MAX_SIZE_X; x++) {
			counter = 0;
            disabledFound = false;
			for (int y = 0; y < Board.MAX_SIZE_Y; y++) {
				final Gem gem = cells[x][y].getGem();
				if (gem.isTypeNone()) {
                    for( int d = y; d < Board.MAX_SIZE_Y; d++ ) {
                        Gem disabledOne = cells[x][d].getGem();
                        if( disabledOne.isDisabled() )
                            disabledFound = true;
                    }

                    if( disabledFound )
                        break;
					final Gem newGem = gemFactory.newRandomGem();
					newGem.setPosition(Board.CELL_PAD_X + (x * Board.CELL_SIZE), Board.CELL_PAD_Y
							+ ((counter + Board.MAX_SIZE_Y) * Board.CELL_SIZE));
					newGem.fallBy(0, -(counter + (Board.MAX_SIZE_Y - y)));
					++counter;
					cells[x][y].setGem(newGem);
					foreGround.addActor(newGem);
				}
			}
		}
	}
}
