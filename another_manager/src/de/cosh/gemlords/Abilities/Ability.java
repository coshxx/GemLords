package de.cosh.gemlords.Abilities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import de.cosh.gemlords.Characters.BaseCharacter;

/**
 * Created by cosh on 14.01.14.
 */
public abstract interface Ability {

	public abstract void drawCooldown(Batch batch, float parentAlpha);

	// use ability
	public abstract boolean fire(BaseCharacter target);

	public abstract int getCurrentCooldown();

	// mhh
	public abstract Image getImage();

	// once every turn
	public abstract void turn();
}
