package de.cosh.anothermanager.Items;

import com.badlogic.gdx.graphics.Texture;
import de.cosh.anothermanager.AnotherManager;

/**
 * Created by cosh on 20.01.14.
 */
public class ItemMinorHealthPotion extends BaseItem {

    public ItemMinorHealthPotion() {
        super(AnotherManager.assets.get("data/textures/minorhealthpotion.png", Texture.class));
        itemNumber = 1;
        setItemName("Minor potion");
        setItemText("Recover 10 hp\nCooldown: 5");
    }
}
