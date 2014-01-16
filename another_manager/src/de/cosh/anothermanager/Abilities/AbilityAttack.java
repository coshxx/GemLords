package de.cosh.anothermanager.Abilities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import de.cosh.anothermanager.AnotherManager;
import de.cosh.anothermanager.Characters.BaseCharacter;

/**
 * Created by cosh on 14.01.14.
 */
public class AbilityAttack extends BaseAbility {
    public AbilityAttack(AnotherManager myGame, int damage, int cooldown, boolean abilityReady) {
        super(myGame, damage, cooldown, abilityReady);
        abilityImage = new Image(myGame.assets.get("data/abilityattack.png", Texture.class));
    }

    @Override
    public boolean fire(BaseCharacter target) {
        if (super.fire(target)) {
            abilityImage.addAction(Actions.sequence(Actions.scaleTo(2f, 2f, 0.15f), Actions.scaleTo(1f, 1f, 0.15f)));
            myGame.soundPlayer.playAbilityAttack();
            return true;
        }
        return false;
    }
}
