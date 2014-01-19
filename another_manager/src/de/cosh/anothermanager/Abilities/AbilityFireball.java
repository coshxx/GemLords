package de.cosh.anothermanager.Abilities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import de.cosh.anothermanager.AnotherManager;
import de.cosh.anothermanager.Characters.BaseCharacter;
import de.cosh.anothermanager.Characters.Debuff;

/**
 * Created by cosh on 15.01.14.
 */
public class AbilityFireball extends BaseAbility {

    public AbilityFireball() {
        abilityImageLocation = "data/abilityfireball.png";
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

			AnotherManager.soundPlayer.playFireballStart();

			final Debuff fireBallDebuff = new Debuff();
			fireBallDebuff.setup(10, 2, target);
			fireBallDebuff.setDebuffImage(new Image(AnotherManager.assets.get(abilityImageLocation, Texture.class)));
			target.addDebuff(fireBallDebuff);
			return true;
		}
		return false;
	}
}
