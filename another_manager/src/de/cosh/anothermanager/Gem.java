package de.cosh.anothermanager;

import java.awt.Point;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Gem {
	public Point location_on_board;
	
	public Gem() {
		location_on_board = new Point();
	}
	
	public void setPositionOnBoard(Point p) {
		location_on_board = p;
	}

	public void draw(SpriteBatch batch) {
		
	}
}
