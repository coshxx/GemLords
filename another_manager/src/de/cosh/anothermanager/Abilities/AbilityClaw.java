package de.cosh.anothermanager.Abilities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import de.cosh.anothermanager.AnotherManager;
import de.cosh.anothermanager.Characters.BaseCharacter;
import de.cosh.anothermanager.Characters.Debuff;

/**
 * Created by cosh on 16.01.14.
 */
public class AbilityClaw extends BaseAbility {
    public AbilityClaw() {
        abilityImageLocation = "data/textures/abilityclaw.png";
        abilityImage = new Image(AnotherManager.assets.get(abilityImageLocation, Texture.class));
    }

	@Override
	public boolean fire(final BaseCharacter target) {
		if (super.fire(target)) {
			abilityImage.addAction(Actions.sequence(Actions.scaleTo(2f, 2f, 0.15f), Actions.scaleTo(1f, 1f, 0.15f)));
			final Image projectile = new Image(abilityImage.getDrawable());
			projectile.setPosition(abilityImage.getX(), abilityImage.getY());
			abilityImage.getStage().addActor(projectile);
			projectile.addAction(Actions.sequence(Actions.moveTo(AnotherManager.VIRTUAL_WIDTH / 2, -50, 0.25f),
					Actions.removeActor(projectile)));
			AnotherManager.soundPlayer.playClawSound();

			final Debuff clawDebuff = new Debuff();
			clawDebuff.setup(10, 10, target);
			clawDebuff.setDebuffImage(new Image(AnotherManager.assets.get(abilityImageLocation, Texture.class)));
			
			final Image fullscreenEffect = new Image(AnotherManager.assets.get("data/textures/clawfullscreen.png", Texture.class));
			fullscreenEffect.setBounds(0, 0, AnotherManager.VIRTUAL_WIDTH, AnotherManager.VIRTUAL_HEIGHT);;
			fullscreenEffect.addAction(Actions.alpha(0f));
			fullscreenEffect.addAction(Actions.sequence(
					Actions.alpha(0.75f, 0.25f),
					Actions.alpha(0.0f, 0.25f),
					Actions.removeActor(fullscreenEffect)));
			abilityImage.getStage().addActor(fullscreenEffect);
			target.addDebuff(clawDebuff);
			return true;
		}
		return false;
	}
}
