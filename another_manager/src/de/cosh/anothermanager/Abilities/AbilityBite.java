package de.cosh.anothermanager.Abilities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import de.cosh.anothermanager.AnotherManager;
import de.cosh.anothermanager.Characters.BaseCharacter;

/**
 * Created by cosh on 14.01.14.
 */
public class AbilityBite extends BaseAbility {
	public AbilityBite() {
		abilityImageLocation = "data/textures/abilitybite.png";
		abilityImage = new Image(AnotherManager.assets.get(abilityImageLocation, Texture.class));
        setAbilityDamage(20);
	}

	@Override
	public boolean fire(final BaseCharacter target) {
		if (super.fire(target)) {
			abilityImage.addAction(Actions.sequence(Actions.scaleTo(2f, 2f, 0.15f), Actions.scaleTo(1f, 1f, 0.15f)));
			AnotherManager.soundPlayer.playAbilityAttack();
            getOwner().increaseHealth(15);
			return true;
		}
		return false;
	}
}
