package de.cosh.gemlords.Items;

import de.cosh.gemlords.Characters.BaseCharacter;
import de.cosh.gemlords.LanguageManager;


public class ItemApprenticeRobe extends BaseItem {
	public ItemApprenticeRobe() {
		super("robe");
        LanguageManager lm = LanguageManager.getInstance();
		itemNumber = 13;
		setItemName(lm.getString("Apprentice Robe"));
		setItemText(lm.getString("Grants an additional\n 25 health"));
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
