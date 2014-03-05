package de.cosh.gemlords.Abilities;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import de.cosh.gemlords.Characters.BaseCharacter;
import de.cosh.gemlords.Characters.Buff;
import de.cosh.gemlords.GemLord;

/**
 * Created by cosh on 14.01.14.
 */
public class AbilityHowl extends BaseAbility {
	public AbilityHowl() {
		abilityImageLocation = "enemywolf";
        TextureAtlas atlas = GemLord.assets.get("data/textures/pack.atlas", TextureAtlas.class);
		abilityImage = new Image(atlas.findRegion(abilityImageLocation));
        setAbilityDamage(0);
	}

	@Override
	public boolean fire(final BaseCharacter target) {
		if (super.fire(target)) {
            abilityImage.addAction(Actions.sequence(Actions.scaleTo(2f, 2f, 0.15f), Actions.scaleTo(1f, 1f, 0.15f)));
            GemLord.soundPlayer.playHowl();


            TextureAtlas atlas = GemLord.assets.get("data/textures/pack.atlas", TextureAtlas.class);
            Buff howlBuff = new Buff();
            howlBuff.setBuffImage(new Image(atlas.findRegion(abilityImageLocation)));
            howlBuff.setup(10, 30, 5, owner);
            owner.addBuff(howlBuff);
			return true;
		}
		return false;
	}
}
