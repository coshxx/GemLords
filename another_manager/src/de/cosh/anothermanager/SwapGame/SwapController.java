package de.cosh.anothermanager.SwapGame;

import com.badlogic.gdx.math.GridPoint2;

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

		if( firstGem == null | secondGem == null )
			return;
		
		if (firstGem.isTypeNone() || secondGem.isTypeNone()) {
			return;
		}
        if( firstGem.isDisabled() || secondGem.isDisabled()) {
            return;
        }

		firstGem.moveBy(x, y);
		secondGem.moveBy(-x, -y);
		if (x != 0) {
			firstGem.setMoving(Gem.MoveDirection.DIRECTION_HORIZONTAL);
			secondGem.setMoving(Gem.MoveDirection.DIRECTION_HORIZONTAL);
		} else {
			firstGem.setMoving(Gem.MoveDirection.DIRECTION_VERTICAL);
			secondGem.setMoving(Gem.MoveDirection.DIRECTION_VERTICAL);
		}

		cells[start.x][start.y].setGem(secondGem);
		cells[start.x + x][start.y + y].setGem(firstGem);
	}
}
