package de.cosh.gemlords.Abilities;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import de.cosh.gemlords.Characters.BaseCharacter;
import de.cosh.gemlords.Characters.Damage;
import de.cosh.gemlords.Characters.Enemy;
import de.cosh.gemlords.GemLord;
import de.cosh.gemlords.LanguageManager;

/**
 * Created by cosh on 15.01.14.
 */
public class BaseAbility implements Ability {
    protected transient BitmapFont bmf;
    protected float fireTime = 0f;
    protected float fireDuration = 250f;
    transient Image abilityImage;
    String abilityImageLocation;
    private int cooldown;
    private int currentCooldown;
    private Damage damage;
    protected transient Enemy owner;

    protected boolean needsUpdate;
    protected float abilityPreDelay = 0.5f;
    protected float abilityPostDelay = 0.25f;
    protected float passedTime;
    protected boolean executedAbility;

    BaseAbility() {
        damage = new Damage();
        cooldown = 5;
        damage.damage = 10;
        abilityImageLocation = "data/textures/empty.png";
        needsUpdate = true;
        executedAbility = false;
        passedTime = 0f;
    }

    public BaseCharacter getOwner() {
        return owner;
    }

    public void setOwner(Enemy owner) {
        this.owner = owner;
    }

    public void setAbilityDamage(int damage) {
        this.damage.damage = damage;

    }

    public void drawFire(SpriteBatch batch, float parentAlpha) {
        return;
    }

    public void update(float delta) {
        passedTime += delta;
        if (passedTime > abilityPreDelay && !executedAbility) {
            executedAbility = true;
            passedTime = 0f;
            fire(GemLord.getInstance().player);
        }
        else if( passedTime > abilityPostDelay && executedAbility ) {
            executedAbility = false;
            needsUpdate = false;
            passedTime = 0f;
        }
    }

    public void setCooldown(int cd) {
        cooldown = cd;
        currentCooldown = cd;
    }

    public boolean needsDraw() {
        return false;
    }

    @Override
    public void drawCooldown(final SpriteBatch batch, final float parentAlpha) {
        final Integer cooldown = getCurrentCooldown();
        GemLord.getInstance();
        Skin s = GemLord.assets.get("data/ui/uiskin.json", Skin.class);
        bmf = s.getFont("default-font");
        bmf.setColor(1f, 1f, 1f, parentAlpha);
        if (currentCooldown > 0) {
            //bmf.draw(batch, cooldown.toString(), abilityImage.getX(), abilityImage.getY());
            bmf.drawMultiLine(batch, cooldown.toString(), abilityImage.getX(), abilityImage.getY() + 45, 70, BitmapFont.HAlignment.CENTER);
        } else {
            LanguageManager lm = LanguageManager.getInstance();
            bmf.drawMultiLine(batch, lm.getString("Ready"), abilityImage.getX(), abilityImage.getY() + 45, 70, BitmapFont.HAlignment.CENTER);
        }
    }

    @Override
    public boolean fire(final BaseCharacter target) {
        if (currentCooldown == 0) {
            target.damage(damage);
            currentCooldown = cooldown;
            return true;
        }
        return false;
    }

    @Override
    public int getCurrentCooldown() {
        return currentCooldown;
    }

    @Override
    public Image getImage() {
        return abilityImage;
    }

    @Override
    public void turn() {
        if (currentCooldown > 0) {
            currentCooldown--;
        }
    }

    public boolean tryDodge() {
        return false;
    }

    public int tryIncreaseDamage() {
        return 0;
    }

    public void setNeedsUpdate(boolean b) {
        needsUpdate = b;
        executedAbility = false;
    }

    public boolean needsUpdate() {
        return needsUpdate;
    }
}
