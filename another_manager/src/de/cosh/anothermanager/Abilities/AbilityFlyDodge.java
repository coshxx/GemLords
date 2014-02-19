package de.cosh.anothermanager.Abilities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import de.cosh.anothermanager.Characters.BaseCharacter;
import de.cosh.anothermanager.GemLord;

/**
 * Created by cosh on 14.01.14.
 */
public class AbilityFlyDodge extends BaseAbility {
	public AbilityFlyDodge() {
		abilityImageLocation = "data/textures/itemamulet.png";
		abilityImage = new Image(GemLord.assets.get(abilityImageLocation, Texture.class));
        setCooldown(99);
    }
	@Override
	public boolean fire(final BaseCharacter target) {
        return false;
	}

    @Override
    public boolean tryDodge() {
        int chance = MathUtils.random(1, 100);
        if( chance <= 10 ) {
            abilityImage.addAction(Actions.sequence(Actions.scaleTo(2f, 2f, 0.15f), Actions.scaleTo(1f, 1f, 0.15f)));
            return true;
        }
        return false;
    }

    public void drawCooldown(final SpriteBatch batch, final float parentAlpha) {
        return;
    }
}
