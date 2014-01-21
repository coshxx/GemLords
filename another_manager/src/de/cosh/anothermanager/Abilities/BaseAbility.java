package de.cosh.anothermanager.Abilities;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import de.cosh.anothermanager.Characters.BaseCharacter;

/**
 * Created by cosh on 15.01.14.
 */
public abstract class BaseAbility implements Ability {
	protected transient Image abilityImage;
    protected String abilityImageLocation;
	private transient BitmapFont bmf;
	private int cooldown;
	private int currentCooldown;
	private int damage;

    public BaseAbility() {
        cooldown = 5;
        damage = 10;
        abilityImageLocation = "data/textures/empty.png";
        bmf = new BitmapFont();
    }

    public void setAbilityDamage(int damage) {
        this.damage = damage;

    }

    public void setCooldown(int cd) {
        cooldown = cd;
        currentCooldown = cd;
    }

	@Override
	public void drawCooldown(final SpriteBatch batch, final float parentAlpha) {
		final Integer cooldown = getCooldown();
		bmf.setColor(1f, 1f, 1f, parentAlpha);
		if (currentCooldown > 0) {
			bmf.draw(batch, cooldown.toString(), abilityImage.getX(), abilityImage.getY());
		} else {
			bmf.draw(batch, "Ready", abilityImage.getX(), abilityImage.getY());
		}
	}

	@Override
	public boolean fire(final BaseCharacter target) {
		if (currentCooldown == 0) {
			target.damage(damage);
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
		if (currentCooldown > 0) {
			currentCooldown--;
		}
	}
}
