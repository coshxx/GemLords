package de.cosh.anothermanager.Abilities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import de.cosh.anothermanager.AnotherManager;
import de.cosh.anothermanager.Characters.BaseCharacter;

/**
 * Created by cosh on 15.01.14.
 */
public class AbilityFireball implements Ability {
    private int damage;
    private int cooldown;
    private int currentCooldown;
    private boolean abilityReady;
    private float x, y;
    private transient Image abilityImage;
    private transient BitmapFont bmf;
    private transient AnotherManager myGame;

    public AbilityFireball(AnotherManager myGame) {
        damage = 15;
        currentCooldown = 4;
        cooldown = 5;
        abilityReady = false;
        abilityImage = new Image(myGame.assets.get("data/abilityfireball.png", Texture.class));
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

            Image projectile = new Image(abilityImage.getDrawable());
            projectile.setPosition(abilityImage.getX(), abilityImage.getY());
            abilityImage.getStage().addActor(projectile);
            projectile.addAction(Actions.sequence(Actions.moveTo(myGame.VIRTUAL_WIDTH/2, -50, 0.25f), Actions.removeActor(projectile)));
            myGame.soundPlayer.playFireballStart();
            return true;
        }
        return false;
    }

    @Override
    public void turn() {
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

    @Override
    public void drawCooldown(SpriteBatch batch, float parentAlpha) {
        Integer cooldown = getCooldown();
        bmf.setColor(1f, 1f, 1f, parentAlpha);
        if (!abilityReady)
            bmf.draw(batch, cooldown.toString(), abilityImage.getX(), abilityImage.getY());
        else bmf.draw(batch, "Ready", abilityImage.getX(), abilityImage.getY());
    }
}
