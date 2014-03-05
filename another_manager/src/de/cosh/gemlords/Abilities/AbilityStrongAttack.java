package de.cosh.gemlords.Abilities;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import de.cosh.gemlords.Characters.BaseCharacter;
import de.cosh.gemlords.GemLord;

/**
 * Created by cosh on 14.01.14.
 */
public class AbilityStrongAttack extends BaseAbility {
	public AbilityStrongAttack() {
		abilityImageLocation = "abilitystrongattack";
        TextureAtlas atlas = GemLord.assets.get("data/textures/pack.atlas", TextureAtlas.class);
		abilityImage = new Image(atlas.findRegion(abilityImageLocation));
        setAbilityDamage(30);
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
