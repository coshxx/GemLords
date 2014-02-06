package de.cosh.anothermanager.Items;

import com.badlogic.gdx.graphics.Texture;
import de.cosh.anothermanager.AnotherManager;
import de.cosh.anothermanager.Characters.BaseCharacter;


public class ItemDagger extends BaseItem {

	public ItemDagger() {
		super(AnotherManager.assets.get("data/textures/itemdagger.png", Texture.class));
		itemNumber = 6;
		setItemName("Valors Dagger");
		setItemText("Increases your\ndamage by 2");
		setItemSlotType(ItemSlotType.WEAPON);
	}

	@Override
	public void resetCooldown() {

	}

	@Override
	public int preFirstTurnBuff(BaseCharacter wearer) {
        return 0;
	}

    public int getAdditionalDamage() {
        return 2;
    }
}
