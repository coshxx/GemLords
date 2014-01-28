package de.cosh.anothermanager.SwapGame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import de.cosh.anothermanager.AnotherManager;

/**
 * Created by cosh on 13.01.14.
 */
public class Cell extends Image {
	private Gem occupant;
	private int cellX, cellY;

	public Cell(AnotherManager myGame, int cellX, int cellY) {
		super(AnotherManager.assets.get("data/textures/cell_back.png", Texture.class));
		this.cellX = cellX;
		this.cellY = cellY;
	}

	public Gem getGem() {
		return occupant;
	}

	public void putGem(Gem gem) {
		occupant = gem;
		occupant.setBounds(getX(), getY(), 80, 80);
		occupant.setCell(cellX, cellY);
	}

	public void setGem(Gem gem) {
		occupant = gem;
		gem.setCell(cellX, cellY);
	}
}
