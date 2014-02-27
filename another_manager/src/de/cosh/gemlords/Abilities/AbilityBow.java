package de.cosh.gemlords.Abilities;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import de.cosh.gemlords.Characters.BaseCharacter;
import de.cosh.gemlords.Characters.Damage;
import de.cosh.gemlords.GemLord;

import java.util.Random;

/**
 * Created by cosh on 14.01.14.
 */
public class AbilityBow extends BaseAbility {
    private Random random;

    public AbilityBow() {
        abilityImageLocation = "itemhuntingbow";
        TextureAtlas atlas = GemLord.assets.get("data/textures/pack.atlas", TextureAtlas.class);
        abilityImage = new Image(atlas.findRegion(abilityImageLocation));
        setCooldown(99);
        setAbilityDamage(0);
        random = new Random();
    }

    @Override
    public boolean fire(final BaseCharacter target) {
        if (super.fire(target)) {
            GemLord.soundPlayer.playBow();
            int damage = random.nextInt(((25 - 10) + 1));
            damage += 10;

            Damage dmg = new Damage();
            dmg.damage = damage;
            GemLord.getInstance().player.damage(dmg);

            abilityImage.addAction(Actions.sequence(Actions.scaleTo(2f, 2f, 0.15f), Actions.scaleTo(1f, 1f, 0.15f)));
            TextureAtlas atlas = GemLord.assets.get("data/textures/pack.atlas", TextureAtlas.class);
            Image projectile = new Image(atlas.findRegion("itemarrow"));
            projectile.setPosition(abilityImage.getX(), abilityImage.getY());

            float targetX = GemLord.VIRTUAL_WIDTH / 2;
            float targetY = 0;

            projectile.addAction(Actions.sequence(
                    Actions.moveTo(targetX, targetY, 0.25f),
                    Actions.fadeOut(0.25f),
                    Actions.removeActor())
            );

            GemLord.getInstance().gameScreen.getBoard().getEffectGroup().addActor(projectile);
            return true;
        }
        return false;
    }

    @Override
    public int tryIncreaseDamage() {
        return 0;
    }
}
