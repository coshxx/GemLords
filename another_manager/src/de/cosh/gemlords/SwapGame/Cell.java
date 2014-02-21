package de.cosh.gemlords.SwapGame;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import de.cosh.gemlords.GemLord;

/**
 * Created by cosh on 13.01.14.
 */
public class Cell extends Image {
	private Gem occupant;
	private int cellX, cellY;
	private boolean isEmpty;

	public Cell(GemLord myGame, int cellX, int cellY) {
        TextureAtlas atlas = GemLord.assets.get("data/textures/pack.atlas", TextureAtlas.class);
		setDrawable(new TextureRegionDrawable(atlas.findRegion("cell_back")));
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
