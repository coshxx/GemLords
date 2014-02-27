package de.cosh.gemlords.Abilities;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import de.cosh.gemlords.Characters.BaseCharacter;
import de.cosh.gemlords.GemLord;

import java.util.Random;

/**
 * Created by cosh on 14.01.14.
 */
public class AbilityMinorHealthPotion extends BaseAbility {
    private Random random;

	public AbilityMinorHealthPotion() {
		abilityImageLocation = "minorhealthpotion";
        TextureAtlas atlas = GemLord.assets.get("data/textures/pack.atlas", TextureAtlas.class);
		abilityImage = new Image(atlas.findRegion("minorhealthpotion"));
        random = new Random();
        setAbilityDamage(0);
	}

	@Override
	public boolean fire(final BaseCharacter target) {
		if (super.fire(target)) {
            GemLord.soundPlayer.playGulp();
			abilityImage.addAction(Actions.sequence(Actions.scaleTo(2f, 2f, 0.15f), Actions.scaleTo(1f, 1f, 0.15f)));
            int healthRegenned = random.nextInt((20 - 10)+1);
            healthRegenned += 10;
			GemLord.getInstance().gameScreen.getBoard().getEnemy().increaseHealth(healthRegenned);
			return true;
		}
		return false;
	}
}
