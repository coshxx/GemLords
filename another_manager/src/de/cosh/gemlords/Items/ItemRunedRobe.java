package de.cosh.gemlords.Items;

import de.cosh.gemlords.Characters.BaseCharacter;
import de.cosh.gemlords.LanguageManager;


public class ItemRunedRobe extends BaseItem {

	public ItemRunedRobe() {
		super("robe_mage");
        LanguageManager lm = LanguageManager.getInstance();
		itemNumber = 17;
		setItemName(lm.getString("Runed Robe"));
		setItemText(lm.getString("Grants an additional\n 100 health\nIncreases crit chance\nby 5%"));
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
		wearer.preGameIncreaseHealth(75);
		return 75;
	}
}
