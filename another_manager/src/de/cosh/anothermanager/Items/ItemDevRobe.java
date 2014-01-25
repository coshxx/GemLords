package de.cosh.anothermanager.Items;

import com.badlogic.gdx.graphics.Texture;

import de.cosh.anothermanager.AnotherManager;
import de.cosh.anothermanager.Characters.BaseCharacter;


public class ItemDevRobe extends BaseItem {

	public ItemDevRobe() {
		super(AnotherManager.assets.get("data/textures/robe.png", Texture.class));
		itemNumber = 1337;
		setItemName("Dev Robe");
		setItemText("Grants an additional\n 9999 hp");
		setItemSlotType(ItemSlotType.ARMOR);
	}

	@Override
	public void resetCooldown() {

	}

	@Override
	public int preFirstTurnBuff(BaseCharacter wearer) {
		wearer.increaseHealth(9999);
		return 9999;
	}
}
