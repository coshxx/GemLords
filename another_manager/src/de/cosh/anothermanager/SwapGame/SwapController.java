package de.cosh.anothermanager.SwapGame;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by cosh on 13.01.14.
 */
public class SwapController {
	private final Cell[][] cells;

	public SwapController(final Cell[][] cells) {
		this.cells = cells;
	}

	public void swap(final GridPoint2 start, final int x, final int y) {
		final Gem firstGem = cells[start.x][start.y].getGem();
		final Gem secondGem = cells[start.x + x][start.y + y].getGem();

		if (firstGem.isTypeNone() || secondGem.isTypeNone()) {
			return;
		}

		firstGem.moveBy(x, y);
		secondGem.moveBy(-x, -y);
		firstGem.setMoving(true);
		secondGem.setMoving(true);

		cells[start.x][start.y].setGem(secondGem);
		cells[start.x + x][start.y + y].setGem(firstGem);
	}
}
