package de.cosh.anothermanager.Items;

import com.badlogic.gdx.graphics.Texture;

import de.cosh.anothermanager.GemLord;
import de.cosh.anothermanager.Characters.BaseCharacter;


public class ItemApprenticeRobe extends BaseItem {

	public ItemApprenticeRobe() {
		super("robe");
		itemNumber = 0;
		setItemName("Apprentice Robe");
		setItemText("Grants an additional\n 25 health");
		setItemSlotType(ItemSlotType.ROBE_ARMOR);
	}

	@Override
	public void resetCooldown() {

	}

	@Override
	public int preFirstTurnBuff(BaseCharacter wearer) {
		wearer.preGameIncreaseHealth(25);
		return 25;
	}
}
