package de.cosh.gemlords.Items;

import de.cosh.gemlords.Characters.BaseCharacter;


public class ItemApprenticeRobe extends BaseItem {

	public ItemApprenticeRobe() {
		super("robe");
		itemNumber = 13;
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
