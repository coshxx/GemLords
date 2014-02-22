package de.cosh.gemlords.Abilities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import de.cosh.gemlords.Characters.BaseCharacter;
import de.cosh.gemlords.GemLord;

/**
 * Created by cosh on 14.01.14.
 */
public class AbilityFlyDagger extends BaseAbility {
	public AbilityFlyDagger() {
		abilityImageLocation = "itemdagger";
        TextureAtlas atlas = GemLord.assets.get("data/textures/pack.atlas", TextureAtlas.class);
		abilityImage = new Image(atlas.findRegion(abilityImageLocation));
        setCooldown(99);
    }
	@Override
	public boolean fire(final BaseCharacter target) {
        return false;
	}
    
    public void update(float delta) {
        needsUpdate = false;
    }

    public void drawCooldown(final SpriteBatch batch, final float parentAlpha) {
        return;
    }

    @Override
    public int tryIncreaseDamage() {
        return MathUtils.random(2, 5);
    }
}
