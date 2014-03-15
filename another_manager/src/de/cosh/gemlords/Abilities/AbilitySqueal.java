package de.cosh.gemlords.Abilities;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import de.cosh.gemlords.Characters.BaseCharacter;
import de.cosh.gemlords.Characters.Debuff;
import de.cosh.gemlords.GemLord;

/**
 * Created by cosh on 16.01.14.
 */
public class AbilitySqueal extends BaseAbility {
	public AbilitySqueal() {
		abilityImageLocation = "abilitysqueal";
        TextureAtlas atlas = GemLord.assets.get("data/textures/pack.atlas", TextureAtlas.class);
		abilityImage = new Image(atlas.findRegion(abilityImageLocation));
        setAbilityDamage(50);
	}

	@Override
	public boolean fire(final BaseCharacter target) {
		if (super.fire(target)) {
            GemLord.soundPlayer.playSqueal();
			return true;
		}
		return false;
	}
}
