package de.cosh.anothermanager.Items;

import com.badlogic.gdx.graphics.Texture;

import de.cosh.anothermanager.GemLord;
import de.cosh.anothermanager.Characters.BaseCharacter;


public class ItemDevRobe extends BaseItem {

	public ItemDevRobe() {
		super(GemLord.assets.get("data/textures/robe.png", Texture.class));
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
		wearer.preGameIncreaseHealth(9999);
		return 9999;
	}
}
