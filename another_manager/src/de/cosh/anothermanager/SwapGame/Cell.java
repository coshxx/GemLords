package de.cosh.anothermanager.SwapGame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import de.cosh.anothermanager.GemLord;

/**
 * Created by cosh on 13.01.14.
 */
public class Cell extends Image {
	private Gem occupant;
	private int cellX, cellY;
	private boolean isEmpty;

	public Cell(GemLord myGame, int cellX, int cellY) {
		super(GemLord.assets.get("data/textures/cell_back.png", Texture.class));
		this.cellX = cellX;
		this.cellY = cellY;
		this.isEmpty = false;
	}

	public Gem getGem() {
		if( isEmpty )
			return null;
		return occupant;
	}

	public void putGem(Gem gem) {
		occupant = gem;
		occupant.setBounds(getX(), getY(), 80, 80);
		occupant.setCell(cellX, cellY);
	}

	public void setGem(Gem gem) {
		occupant = gem;
		occupant.setCell(cellX, cellY);
	}
	
	public boolean isEmpty() {
		return isEmpty;
	}
	public void setEmpty(boolean b) {
		isEmpty = b;
	}
	
}
