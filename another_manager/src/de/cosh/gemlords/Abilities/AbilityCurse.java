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
public class AbilityCurse extends BaseAbility {
	public AbilityCurse() {
		abilityImageLocation = "abilitycurse";
        TextureAtlas atlas = GemLord.assets.get("data/textures/pack.atlas", TextureAtlas.class);
		abilityImage = new Image(atlas.findRegion(abilityImageLocation));
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
			GemLord.soundPlayer.playCurse();

			final Debuff curseDebuff = new Debuff();
			curseDebuff.setup(10, 99, target);
            TextureAtlas atlas = GemLord.assets.get("data/textures/pack.atlas", TextureAtlas.class);
			curseDebuff.setDebuffImage(new Image(atlas.findRegion(abilityImageLocation)));
			target.addDebuff(curseDebuff);
			return true;
		}
		return false;
	}
}
