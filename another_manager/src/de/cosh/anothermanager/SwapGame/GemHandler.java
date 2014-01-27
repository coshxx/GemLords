package de.cosh.anothermanager.SwapGame;

import com.badlogic.gdx.scenes.scene2d.Group;

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
//		int counter;
//		boolean disabledFound = false;
//		for (int x = 0; x < Board.MAX_SIZE_X; x++) {
//			counter = 0;
//			disabledFound = false;
//			for (int y = 0; y < Board.MAX_SIZE_Y; y++) {
//				final Gem gem = cells[x][y].getGem();
//				if (gem.isTypeNone()) {
//					for (int d = y; d < Board.MAX_SIZE_Y; d++) {
//						Gem disabledOne = cells[x][d].getGem();
//						if (disabledOne.isDisabled())
//							disabledFound = true;
//					}
//
//					if (disabledFound)
//						break;
//					final Gem newGem = gemFactory.newRandomGem();
//					newGem.setPosition(Board.CELL_PAD_X + (x * Board.CELL_SIZE), Board.CELL_PAD_Y
//							+ ((counter + Board.MAX_SIZE_Y) * Board.CELL_SIZE));
//					newGem.fallBy(0, -(counter + (Board.MAX_SIZE_Y - y)));
//					++counter;
//					cells[x][y].setGem(newGem);
//					foreGround.addActor(newGem);
//				}
//			}
//		}
	}

	public void respawnAndApplyGravity(Group foreGround) {
		int counter;
		for (int x = 0; x < Board.MAX_SIZE_X; x++) {
			counter = 0;
			for (int y = 0; y < Board.MAX_SIZE_Y; y++) {
				Gem g = cells[x][y].getGem();
				if( g.isTypeNone() ) {
					
					Gem newGem = gemFactory.newRandomGem();
					newGem.setPosition(Board.CELL_PAD_X + (x * Board.CELL_SIZE), Board.CELL_PAD_Y
							+ ((counter + Board.MAX_SIZE_Y) * Board.CELL_SIZE));
					counter++;
					cells[x][y].setGem(newGem);
					foreGround.addActor(newGem);
				}
			}
		}
	}
}
