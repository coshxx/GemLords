package de.cosh.anothermanager.Abilities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import de.cosh.anothermanager.Characters.BaseCharacter;

/**
 * Created by cosh on 14.01.14.
 */
public abstract interface Ability {

	public abstract void drawCooldown(SpriteBatch batch, float parentAlpha);

	// use ability
	public abstract boolean fire(BaseCharacter target);

	public abstract int getCooldown();

	// mhh
	public abstract Image getImage();

	// once every turn
	public abstract void turn();
}
