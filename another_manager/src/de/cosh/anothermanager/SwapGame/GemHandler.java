package de.cosh.anothermanager.SwapGame;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import de.cosh.anothermanager.AnotherManager;

public class GemHandler {
	private GemFactory gemFactory;
	private Cell[][] cells;

	public GemHandler(Cell[][] cells) {
		gemFactory = new GemFactory(AnotherManager.getInstance());
		this.cells = cells;
	}

	public GemFactory getGemFactory() {
		return gemFactory;
	}

	public void respawn(final Group foreGround) {
		// int counter;
		// boolean disabledFound = false;
		// for (int x = 0; x < Board.MAX_SIZE_X; x++) {
		// counter = 0;
		// disabledFound = false;
		// for (int y = 0; y < Board.MAX_SIZE_Y; y++) {
		// final Gem gem = cells[x][y].getGem();
		// if (gem.isTypeNone()) {
		// for (int d = y; d < Board.MAX_SIZE_Y; d++) {
		// Gem disabledOne = cells[x][d].getGem();
		// if (disabledOne.isDisabled())
		// disabledFound = true;
		// }
		//
		// if (disabledFound)
		// break;
		// final Gem newGem = gemFactory.newRandomGem();
		// newGem.setPosition(Board.CELL_PAD_X + (x * Board.CELL_SIZE),
		// Board.CELL_PAD_Y
		// + ((counter + Board.MAX_SIZE_Y) * Board.CELL_SIZE));
		// newGem.fallBy(0, -(counter + (Board.MAX_SIZE_Y - y)));
		// ++counter;
		// cells[x][y].setGem(newGem);
		// foreGround.addActor(newGem);
		// }
		// }
		// }
	}

	public void respawnAndApplyGravity(Group foreGround) {
		fall();
		if (shift())
			respawnAndApplyGravity(foreGround);
		//
		// Gem fall;
		// boolean hadToShift = false;
		// for (int x = 0; x < Board.MAX_SIZE_X; x++) {
		// for (int y = 0; y < Board.MAX_SIZE_Y; y++) {
		// final Gem gem = cells[x][y].getGem();
		// if (gem.isTypeNone()) {
		// for (int d = y; d < Board.MAX_SIZE_Y; d++) {
		// fall = cells[x][d].getGem();
		// if (fall.isTypeNone()) {
		// continue;
		// }
		// if (fall.isDisabled()) {
		// fall = cells[x + 1][d].getGem();
		// if (fall.isTypeNone())
		// break;
		// fall.addAction(Actions.after(Actions.moveBy(
		// -Board.CELL_SIZE, -Board.CELL_SIZE, 0.25f)));
		// Gem temp = cells[x][d - 1].getGem();
		// cells[x][d - 1].setGem(fall);
		// cells[x + 1][d].setGem(temp);
		// hadToShift = true;
		// break;
		// } else
		// fall.fallBy(0, -(d - y));
		//
		// fall.setMoving(Gem.MoveDirection.DIRECTION_VERTICAL);
		//
		// final Gem temp = cells[x][y].getGem();
		// cells[x][y].setGem(fall);
		// cells[x][d].setGem(temp);
		// break;
		// }
		// }
		// }
		// }
		// if (hadToShift) {
		// respawnAndApplyGravity(foreGround);
		// }
	}

	private boolean shift() {
		Gem fall;
		boolean hadToShift = false;
		for (int x = 0; x < Board.MAX_SIZE_X; x++) {
			for (int y = 0; y < Board.MAX_SIZE_Y; y++) {
				Gem gem = cells[x][y].getGem();
				if (gem.isDisabled()) {
					if (cells[x][y - 1].getGem().isTypeNone()) {
						if (!cells[x + 1][y].getGem().isTypeNone()) {
							fall = cells[x + 1][y].getGem();
							fall.addAction(Actions.after(Actions.moveBy(
									-Board.CELL_SIZE, -Board.CELL_SIZE, 0.25f)));
							Gem temp = cells[x][y - 1].getGem();
							cells[x][y - 1].setGem(fall);
							cells[x + 1][y].setGem(temp);
							hadToShift = true;
						}
					}
				}
			}
		}
		return hadToShift;
	}

	private void fall() {
		Gem fall;
		for (int x = 0; x < Board.MAX_SIZE_X; x++) {
			for (int y = 0; y < Board.MAX_SIZE_Y; y++) {
				final Gem gem = cells[x][y].getGem();
				if (gem.isTypeNone()) {
					for (int d = y; d < Board.MAX_SIZE_Y; d++) {
						fall = cells[x][d].getGem();
						if (fall.isTypeNone()) {
							continue;
						}
						if (fall.isDisabled()) {
							break;
						}
						fall.fallBy(0, -(d - y));

						fall.setMoving(Gem.MoveDirection.DIRECTION_VERTICAL);

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
