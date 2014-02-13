package de.cosh.anothermanager.Abilities;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import de.cosh.anothermanager.GemLord;
import de.cosh.anothermanager.Characters.BaseCharacter;
import de.cosh.anothermanager.Characters.Damage;

/**
 * Created by cosh on 15.01.14.
 */
public class BaseAbility implements Ability {
	transient Image abilityImage;
	String abilityImageLocation;
	protected transient BitmapFont bmf;
	private int cooldown;
	private int currentCooldown;
	private Damage damage;
    private transient BaseCharacter owner;

	BaseAbility() {
        damage = new Damage();
		cooldown = 5;
		damage.damage = 10;
		abilityImageLocation = "data/textures/empty.png";
	}

    public void setOwner(BaseCharacter owner) {
        this.owner = owner;
    }

    public BaseCharacter getOwner() { return owner; }

	public void setAbilityDamage(int damage) {
		this.damage.damage = damage;

	}

	public void setCooldown(int cd) {
		cooldown = cd;
		currentCooldown = cd;
	}

	@Override
	public void drawCooldown(final SpriteBatch batch, final float parentAlpha) {
		final Integer cooldown = getCurrentCooldown();

		GemLord.getInstance();
		Skin s = GemLord.assets.get("data/ui/uiskin.json", Skin.class)	;
		bmf = s.getFont("default-font");
		bmf.setColor(1f, 1f, 1f, parentAlpha);
		if (currentCooldown > 0) {
			//bmf.draw(batch, cooldown.toString(), abilityImage.getX(), abilityImage.getY());
			bmf.drawMultiLine(batch, cooldown.toString(), abilityImage.getX(), abilityImage.getY()+45, 70, BitmapFont.HAlignment.CENTER);
		} else {
			//bmf.draw(batch, "Ready", abilityImage.getX(), abilityImage.getY());
			bmf.drawMultiLine(batch, "Ready", abilityImage.getX(), abilityImage.getY()+45, 70, BitmapFont.HAlignment.CENTER);
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
	public int getCurrentCooldown() {
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
