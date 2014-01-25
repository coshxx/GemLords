package de.cosh.anothermanager.SwapGame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import de.cosh.anothermanager.AnotherManager;

/**
 * Created by cosh on 13.01.14.
 */
public class Cell extends Image {
	private Gem occupant;

	public Cell(final AnotherManager myGame) {
		super(AnotherManager.assets.get("data/textures/cell_back.png", Texture.class));
	}

	public Gem getGem() {
		return occupant;
	}

	public void putGem(final Gem gem) {
		occupant = gem;
		occupant.setBounds(getX(), getY(), 80, 80);
	}

	public void setGem(final Gem gem) {
		occupant = gem;
	}
	
}
