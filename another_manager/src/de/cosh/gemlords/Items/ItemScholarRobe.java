package de.cosh.gemlords.Items;

import de.cosh.gemlords.Characters.BaseCharacter;


public class ItemScholarRobe extends BaseItem {

	public ItemScholarRobe() {
		super("robe_scholar");
		itemNumber = 2;
		setItemName("Scholars Robe");
		setItemText("Grants an additional\n 50 hp");
		setItemSlotType(ItemSlotType.ROBE_ARMOR);
	}

	@Override
	public void resetCooldown() {

	}

	@Override
	public int preFirstTurnBuff(BaseCharacter wearer) {
		wearer.preGameIncreaseHealth(50);
		return 50;
	}
}
