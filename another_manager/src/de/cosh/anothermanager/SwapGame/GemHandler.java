package de.cosh.anothermanager.SwapGame;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import de.cosh.anothermanager.AnotherManager;

public class GemHandler {
	private GemFactory gemFactory;
	private Cell[][] cells;
	private float delayDelta, delay;
	private boolean lastShiftRight;

	public GemHandler(Cell[][] cells) {
		gemFactory = new GemFactory(AnotherManager.getInstance());
		this.cells = cells;
		lastShiftRight = false;
		delay = 0f;
	}

	public GemFactory getGemFactory() {
		return gemFactory;
	}

	public void respawn(final Group foreGround) {
		int count;
		boolean cancel;
		boolean cenceledOnce = false;
		for( int x = 0; x < Board.MAX_SIZE_X; x++ ) {
			count = 0;
			cancel = false;
			for( int y = 0; y < Board.MAX_SIZE_Y; y++) {
				Gem gem = cells[x][y].getGem();
				if( gem.isTypeNone() ) {
					for( int d = y; d < Board.MAX_SIZE_Y; d++ ) {
						Gem test = cells[x][d].getGem();
						if( test.isDisabled() ) {
							cancel = true;
						}
					}
					if( cancel ) {
						cenceledOnce = true;
						break;
					}
					Gem newGem = gemFactory.newRandomGem();
					newGem.setPosition(Board.CELL_PAD_X + x * Board.CELL_SIZE, Board.CELL_PAD_Y + Board.CELL_SIZE * (count+Board.MAX_SIZE_Y));
					newGem.fallBy(0, -(Board.MAX_SIZE_Y-(y-count)));
					cells[x][y].setGem(newGem);
					count++;
					foreGround.addActor(newGem);
				}
			}
		}
		if( cenceledOnce )
			respawnAndApplyGravity(foreGround);
	}

	public void respawnAndApplyGravity(Group foreGround) {
		/*
		fall();
		if (shift())
			respawnAndApplyGravity(foreGround); // loop until no more shifts
		respawn(foreGround);
		delay = 0f;
		*/
	}

	private boolean shift() {
		Gem fall;
		boolean hadToShift = false;
		for (int x = 0; x < Board.MAX_SIZE_X; x++) {
			for (int y = 0; y < Board.MAX_SIZE_Y; y++) {
				Gem gem = cells[x][y].getGem();
				if (gem.isDisabled()) {
					if (y - 1 < 0)
						continue;
					if (cells[x][y - 1].getGem().isTypeNone()) {
						if (x - 1 < 0) {
							lastShiftRight = false;
						}
						if (x + 1 >= Board.MAX_SIZE_X) {
							lastShiftRight = true;
						}
						if (lastShiftRight && !cells[x - 1][y].getGem().isTypeNone() && !cells[x-1][y].getGem().isDisabled()) {
							lastShiftRight = false;
							fall = cells[x - 1][y].getGem();
							fall.addAction(Actions.after(Actions.sequence(Actions.delay(delay),
									Actions.moveBy(Board.CELL_SIZE, -Board.CELL_SIZE, 0.15f))));
							Gem temp = cells[x][y - 1].getGem();
							cells[x][y - 1].setGem(fall);
							cells[x - 1][y].setGem(temp);
							delay += delayDelta;
							hadToShift = true;
							delay = 0f;
						} else {
							lastShiftRight = true;
							if (!cells[x + 1][y].getGem().isTypeNone() && !cells[x+1][y].getGem().isDisabled()) {
								fall = cells[x + 1][y].getGem();
								fall.addAction(Actions.after(Actions.sequence(Actions.delay(delay),
										Actions.moveBy(-Board.CELL_SIZE, -Board.CELL_SIZE, 0.15f))));
								Gem temp = cells[x][y - 1].getGem();
								cells[x][y - 1].setGem(fall);
								cells[x + 1][y].setGem(temp);
								hadToShift = true;
								delay = 0f;
							}
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
