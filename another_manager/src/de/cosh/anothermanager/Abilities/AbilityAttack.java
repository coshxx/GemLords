package de.cosh.anothermanager.Abilities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import de.cosh.anothermanager.GemLord;
import de.cosh.anothermanager.Characters.BaseCharacter;

/**
 * Created by cosh on 14.01.14.
 */
public class AbilityAttack extends BaseAbility {
	public AbilityAttack( ) {
		abilityImageLocation = "data/textures/abilityattack.png";
		abilityImage = new Image(GemLord.assets.get(abilityImageLocation, Texture.class));
	}

	@Override
	public boolean fire(final BaseCharacter target) {
		if (super.fire(target)) {
			abilityImage.addAction(Actions.sequence(Actions.scaleTo(2f, 2f, 0.15f), Actions.scaleTo(1f, 1f, 0.15f)));
			GemLord.soundPlayer.playAbilityAttack();
			return true;
		}
		return false;
	}
}
