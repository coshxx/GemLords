package de.cosh.gemlords.Items;

import de.cosh.gemlords.Characters.BaseCharacter;
import de.cosh.gemlords.LanguageManager;


public class ItemScholarRobe extends BaseItem {

	public ItemScholarRobe() {
		super("robe_scholar");
        LanguageManager lm = LanguageManager.getInstance();
		itemNumber = 2;
		setItemName(lm.getString("Scholars Robe"));
		setItemText(lm.getString("Grants an additional\n 50 hp"));
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
