package de.cosh.gemlords.SwapGame;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import de.cosh.gemlords.GemLord;

public class GemHandler {
	private GemFactory gemFactory;
	private Cell[][] cells;
	private float delayDelta, delay;
	private boolean lastShiftRight;
	private final RespawnRequest respawnRequest;

	public GemHandler(Cell[][] cells, RespawnRequest respawnRequest) {
		gemFactory = new GemFactory(GemLord.getInstance());
		this.cells = cells;
		this.respawnRequest = respawnRequest;
		lastShiftRight = false;
		delay = 0f;
		
	}

	public GemFactory getGemFactory() {
		return gemFactory;
	}

    public void respawn(Group foreGround) {
		for( int x = 0; x < Board.MAX_SIZE_X; x++ ) {
			int numSpawns = respawnRequest.howManyForCol(x);
			for( int i = 0; i < numSpawns; i++ ) {
				Gem newGem = gemFactory.newRandomGem();
				newGem.setPosition(Board.CELL_PAD_X + Board.CELL_SIZE * x, Board.CELL_PAD_Y + Board.CELL_SIZE * (Board.MAX_SIZE_Y + i));
				foreGround.addActor(newGem);
				newGem.setCell(x, Board.MAX_SIZE_Y + i);
				GemLord.getInstance().gameScreen.getBoard().addToUncelledGems(newGem);
				//newGem.setFalling(true);
			}
		}
		respawnRequest.clear();
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
						//fall.fallBy(0, -(d - y));

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
