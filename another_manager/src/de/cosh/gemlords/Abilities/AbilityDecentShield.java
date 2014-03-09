package de.cosh.gemlords.Abilities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import de.cosh.gemlords.Characters.BaseCharacter;
import de.cosh.gemlords.Characters.Damage;
import de.cosh.gemlords.GemLord;

/**
 * Created by cosh on 14.01.14.
 */
public class AbilityDecentShield extends BaseAbility {
	public AbilityDecentShield() {
		abilityImageLocation = "bettershield";
        TextureAtlas atlas = GemLord.assets.get("data/textures/pack.atlas", TextureAtlas.class);
		abilityImage = new Image(atlas.findRegion("bettershield"));
        setCooldown(99);
    }
	@Override
	public boolean fire(final BaseCharacter target) {
        return false;
	}

    public void update(float delta) {
        needsUpdate = false;
    }

    public void tryReduce(Damage damage) {
        int inHundret = MathUtils.random(1, 100);
        if( inHundret < 20 ) {
            abilityImage.addAction(Actions.sequence(Actions.scaleTo(2f, 2f, 0.15f), Actions.scaleTo(1f, 1f, 0.15f)));
            GemLord.getInstance().soundPlayer.playBlock();
            int reduce = MathUtils.random(5, 10);
            damage.damage -= reduce;
            if( damage.damage < 0 )
                damage.damage = 0;
            if( MathUtils.random(1, 100) <= 50 ) {
                GemLord.getInstance().gameScreen.getBoard().getEnemy().increaseHealth(reduce);
            }
        }
    }

    @Override
    public void drawCooldown(final Batch batch, final float parentAlpha) {
        return;
    }
}
