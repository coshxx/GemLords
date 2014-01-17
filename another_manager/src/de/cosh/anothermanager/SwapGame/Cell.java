package de.cosh.anothermanager.SwapGame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import de.cosh.anothermanager.AnotherManager;

/**
 * Created by cosh on 13.01.14.
 */
public class Cell extends Image {
	private final AnotherManager myGame;
	private Gem occupant;

	public Cell(final AnotherManager myGame) {
		super(myGame.assets.get("data/cell_back.png", Texture.class));
		this.myGame = myGame;
	}

	public Gem getGem() {
		return occupant;
	}

	public void putGem(final Gem gem) {
		occupant = gem;
		occupant.setPosition(getX(), getY());
	}

	public void setGem(final Gem gem) {
		occupant = gem;
	}
}
