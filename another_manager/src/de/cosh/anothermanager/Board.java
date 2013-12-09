package de.cosh.anothermanager;

import java.awt.Point;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Board {
	private final int SIZE_X = 8;
	private final int SIZE_Y = 7;
	
	private Gem[] gems;

	public Board() {
	}

	public void draw(SpriteBatch batch) {
		for( Gem g : gems ) {
			g.draw(batch);
		}
	}

	public void init() {
		gems = new Gem[SIZE_X*SIZE_Y];
		for (int x = 0; x < SIZE_X; x++) {
			for (int y = 0; y < SIZE_Y; y++) {
				gems[x*y] = new Gem();
				gems[x*y].setPositionOnBoard(new Point(x * 32, y * 32));
			}
		}
	}

}
