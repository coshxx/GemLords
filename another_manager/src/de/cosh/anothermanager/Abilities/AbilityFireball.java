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
	public AbilityFireball(final AnotherManager myGame, final int damage, final int cooldown, final boolean abilityReady) {
		super(myGame, damage, cooldown, abilityReady);
		abilityImage = new Image(myGame.assets.get("data/abilityfireball.png", Texture.class));
	}

	@Override
	public boolean fire(final BaseCharacter target) {
		if (super.fire(target)) {
			abilityImage.addAction(Actions.sequence(Actions.scaleTo(2f, 2f, 0.15f), Actions.scaleTo(1f, 1f, 0.15f)));
			final Image projectile = new Image(abilityImage.getDrawable());
			projectile.setPosition(abilityImage.getX(), abilityImage.getY());
			abilityImage.getStage().addActor(projectile);
			projectile.addAction(Actions.sequence(Actions.moveTo(myGame.VIRTUAL_WIDTH / 2, -50, 0.25f),
					Actions.removeActor(projectile)));

			myGame.soundPlayer.playFireballStart();

			final Debuff fireBallDebuff = new Debuff(myGame);
			fireBallDebuff.setup(10, 2, target);
			fireBallDebuff.setDebuffImage(new Image(myGame.assets.get("data/abilityfireball.png", Texture.class)));
			target.addDebuff(fireBallDebuff);
			return true;
		}
		return false;
	}
}
