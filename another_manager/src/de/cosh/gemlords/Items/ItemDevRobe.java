package de.cosh.gemlords.Items;

import de.cosh.gemlords.Characters.BaseCharacter;
import de.cosh.gemlords.LanguageManager;


public class ItemDevRobe extends BaseItem {

	public ItemDevRobe() {
		super("robe");
        LanguageManager lm = LanguageManager.getInstance();
		itemNumber = 1337;
		setItemName("Dev Robe");
		setItemText("Grants an additional\n 9999 hp");
		setItemSlotType(ItemSlotType.ROBE_ARMOR);
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
