package de.cosh.anothermanager.Abilities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import de.cosh.anothermanager.GemLord;
import de.cosh.anothermanager.Characters.BaseCharacter;
import de.cosh.anothermanager.Characters.Debuff;

/**
 * Created by cosh on 16.01.14.
 */
public class AbilityPoison extends BaseAbility {
	public AbilityPoison() {
		abilityImageLocation = "data/textures/abilitypoison.png";
		abilityImage = new Image(GemLord.assets.get(abilityImageLocation, Texture.class));
	}

	@Override
	public boolean fire(final BaseCharacter target) {
		if (super.fire(target)) {
			abilityImage.addAction(Actions.sequence(Actions.scaleTo(2f, 2f, 0.15f), Actions.scaleTo(1f, 1f, 0.15f)));
			final Image projectile = new Image(abilityImage.getDrawable());
			projectile.setPosition(abilityImage.getX(), abilityImage.getY());
			abilityImage.getStage().addActor(projectile);
			projectile.addAction(Actions.sequence(Actions.moveTo(GemLord.VIRTUAL_WIDTH / 2, -50, 0.25f),
					Actions.removeActor(projectile)));
			GemLord.soundPlayer.playPoisonSound();

			final Debuff poisonDebuff = new Debuff();
			poisonDebuff.setup(5, 5, target);
			poisonDebuff.setDebuffImage(new Image(GemLord.assets.get(abilityImageLocation, Texture.class)));
			target.addDebuff(poisonDebuff);
			return true;
		}
		return false;
	}
}
