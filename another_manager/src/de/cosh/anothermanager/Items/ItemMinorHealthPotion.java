package de.cosh.anothermanager.Items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import de.cosh.anothermanager.Abilities.Ability;
import de.cosh.anothermanager.AnotherManager;
import de.cosh.anothermanager.Characters.BaseCharacter;

/**
 * Created by cosh on 20.01.14.
 */
public class ItemMinorHealthPotion extends BaseItem implements UseItem {
    private int cooldown;
    private Integer currentCooldown;
    private BitmapFont bmf;

    public ItemMinorHealthPotion() {
        super(AnotherManager.assets.get("data/textures/minorhealthpotion.png", Texture.class));
        itemNumber = 1;
        setItemName("Minor potion");
        setItemText("Recover 10 hp\nCooldown: 5");
        setItemSlotType(ItemSlotType.POTION);

        cooldown = 5;
        currentCooldown = 5;
        bmf = new BitmapFont();
    }

    @Override
    public void onUse() {
        if (currentCooldown <= 0) {
            AnotherManager.getInstance().soundPlayer.playGulp();
            AnotherManager.getInstance().player.increaseHealth(10);
            addAction(Actions.sequence(Actions.scaleTo(2f, 2f, 0.15f), Actions.scaleTo(1f, 1f, 0.15f)));
            currentCooldown = cooldown;
        }
    }

    @Override
    public void drawCooldown(SpriteBatch batch, float parentAlpha) {
        if( currentCooldown <= 0 )
            bmf.draw(batch, "Ready", getX(), getY() );
        else bmf.draw(batch, currentCooldown.toString(), getX(), getY());
    }

    @Override
    public void turn() {
        currentCooldown--;
    }

    @Override
    public void resetCooldown() {
        currentCooldown = cooldown;
    }
}
