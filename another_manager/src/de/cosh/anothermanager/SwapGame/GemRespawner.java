package de.cosh.anothermanager.SwapGame;

import java.util.Random;

import com.badlogic.gdx.scenes.scene2d.Group;

/**
 * Created by cosh on 13.01.14.
 */
public class GemRespawner {
	private final Cell[][] cells;
	private final GemFactory gemFactory;
	private final Random random;

	public GemRespawner(final Cell[][] cells, final Random r, final GemFactory gemFactory) {
		this.cells = cells;
		this.random = r;
		this.gemFactory = gemFactory;
	}

	public void respawn(final Group foreGround) {
		int counter;
		for (int x = 0; x < Board.MAX_SIZE_X; x++) {
			counter = 0;
			for (int y = 0; y < Board.MAX_SIZE_Y; y++) {
				final Gem gem = cells[x][y].getGem();
				if (gem.isTypeNone()) {
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
