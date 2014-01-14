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
public class AbilityAttack implements Ability {
    private int damage;
    private int cooldown;
    private int currentCooldown;
    private boolean abilityReady;
    private float x, y;
    private Image abilityImage;
    private BitmapFont bmf;
    private AnotherManager myGame;

    public AbilityAttack(AnotherManager myGame) {
        damage = 15;
        currentCooldown = 2;
        cooldown = 2;
        abilityReady = true;
        abilityImage = new Image(myGame.assets.get("data/abilityattack.png", Texture.class));
        bmf = new BitmapFont();
        this.myGame = myGame;
    }

    @Override
    public boolean fire(BaseCharacter target) {
        if (abilityReady) {
            target.damage(damage);
            abilityReady = false;
            currentCooldown = cooldown;
            abilityImage.addAction(Actions.sequence(Actions.scaleTo(2f, 2f, 0.15f), Actions.scaleTo(1f, 1f, 0.15f)));
            myGame.soundPlayer.playAbilityAttack();
            return true;
        }
        return false;
    }

    @Override
    public void update() {
        if( !abilityReady ) {
            currentCooldown--;
        }
        if (currentCooldown == 0) {
            currentCooldown = cooldown;
            abilityReady = true;
        }
    }

    @Override
    public Image getImage() {
        return abilityImage;
    }

    @Override
    public int getCooldown() {
        return currentCooldown;
    }

    public void drawCooldown(SpriteBatch batch, float parentAlpha) {
        Integer cooldown = getCooldown();
        if (!abilityReady)
            bmf.draw(batch, cooldown.toString(), abilityImage.getX(), abilityImage.getY());
        else bmf.draw(batch, "Ready", abilityImage.getX(), abilityImage.getY());
    }
}
