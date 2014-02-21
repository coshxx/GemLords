package de.cosh.gemlords.Items;

import de.cosh.gemlords.Characters.BaseCharacter;


public class ItemMageRobe extends BaseItem {

	public ItemMageRobe() {
		super("robe_mage");
		itemNumber = 10;
		setItemName("Mages Robe");
		setItemText("Grants an additional\n 75 health\nIncreases crit chance\nby 3%");
		setItemSlotType(ItemSlotType.ROBE_ARMOR);
	}

	@Override
	public void resetCooldown() {

	}

    public int getCritChanceIncrease() {
        return 3;
    }

	@Override
	public int preFirstTurnBuff(BaseCharacter wearer) {
		wearer.preGameIncreaseHealth(50);
		return 50;
	}
}