package de.cosh.anothermanager.Abilities;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import de.cosh.anothermanager.AnotherManager;
import de.cosh.anothermanager.Characters.BaseCharacter;

/**
 * Created by cosh on 15.01.14.
 */
public abstract class BaseAbility implements Ability {
	protected transient Image abilityImage;
	private boolean abilityReady;
	private transient BitmapFont bmf;
	private final int cooldown;

	private int currentCooldown;
	private final int damage;
	protected transient AnotherManager myGame;

	public BaseAbility(final AnotherManager myGame, final int damage, final int cooldown, final boolean abilityReady) {
		this.damage = damage;
		this.cooldown = cooldown;
		this.currentCooldown = cooldown;
		this.myGame = myGame;
		this.abilityReady = abilityReady;
		this.bmf = new BitmapFont();
	}

	@Override
	public void drawCooldown(final SpriteBatch batch, final float parentAlpha) {
		final Integer cooldown = getCooldown();
		bmf.setColor(1f, 1f, 1f, parentAlpha);
		if (!abilityReady) {
			bmf.draw(batch, cooldown.toString(), abilityImage.getX(), abilityImage.getY());
		} else {
			bmf.draw(batch, "Ready", abilityImage.getX(), abilityImage.getY());
		}
	}

	@Override
	public boolean fire(final BaseCharacter target) {
		if (abilityReady) {
			target.damage(damage);
			abilityReady = false;
			currentCooldown = cooldown;
			return true;
		}
		return false;
	}

	@Override
	public int getCooldown() {
		return currentCooldown;
	}

	@Override
	public Image getImage() {
		return abilityImage;
	}

	@Override
	public void turn() {
		if (!abilityReady) {
			currentCooldown--;
		}
		if (currentCooldown == 0) {
			currentCooldown = cooldown;
			abilityReady = true;
		}
	}
}
