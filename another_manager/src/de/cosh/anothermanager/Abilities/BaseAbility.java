package de.cosh.anothermanager.Abilities;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import de.cosh.anothermanager.AnotherManager;
import de.cosh.anothermanager.Characters.BaseCharacter;

/**
 * Created by cosh on 15.01.14.
 */
public abstract class BaseAbility implements Ability {
    private boolean abilityReady;
    private int currentCooldown;
    private int cooldown;
    private int damage;

    private transient BitmapFont bmf;
    protected transient AnotherManager myGame;
    protected transient Image abilityImage;

    public BaseAbility(AnotherManager myGame, int damage, int cooldown, boolean abilityReady){
        this.damage = damage;
        this.cooldown = cooldown;
        this.currentCooldown = cooldown;
        this.myGame = myGame;
        this.abilityReady = abilityReady;
        this.bmf = new BitmapFont();
    }
    @Override
    public boolean fire(BaseCharacter target) {
        if (abilityReady) {
            target.damage(damage);
            abilityReady = false;
            currentCooldown = cooldown;
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
