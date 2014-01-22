package de.cosh.anothermanager.Items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import de.cosh.anothermanager.Abilities.Ability;
import de.cosh.anothermanager.AnotherManager;
import de.cosh.anothermanager.Characters.BaseCharacter;
import de.cosh.anothermanager.Characters.Buff;

/**
 * Created by cosh on 20.01.14.
 */
public class ItemTotem extends BaseItem implements UseItem {
    private int cooldown;
    private Integer currentCooldown;
    private BitmapFont bmf;

    public ItemTotem() {
        super(AnotherManager.assets.get("data/textures/totem.png", Texture.class));
        itemNumber = 3;
        setItemName("Alpha Totem");
        setItemText("Recover 5 hp\neach turn\nOne use");
        setItemSlotType(ItemSlotType.POTION);

        cooldown = 0;
        currentCooldown = 0;
        bmf = new BitmapFont();
    }

    @Override
    public void onUse() {
        if (currentCooldown <= 0) {
            AnotherManager.getInstance().soundPlayer.playTotem();
            Buff totemBuff = new Buff();
            totemBuff.setBuffImage(new Image(AnotherManager.assets.get("data/textures/totem.png", Texture.class)));
            totemBuff.setup(5, 99, AnotherManager.getInstance().player);
            AnotherManager.getInstance().player.addBuff(totemBuff);
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

    @Override
    public int preFirstTurnBuff(BaseCharacter wearer) {
        return 0;
    }
}
