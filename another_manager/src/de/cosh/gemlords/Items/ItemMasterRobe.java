package de.cosh.gemlords.Items;

import de.cosh.gemlords.Characters.BaseCharacter;
import de.cosh.gemlords.LanguageManager;


public class ItemMasterRobe extends BaseItem {

	public ItemMasterRobe() {
		super("robe_master");
        LanguageManager lm = LanguageManager.getInstance();
		itemNumber = 17;
		setItemName(lm.getString("Master Robe"));
		setItemText(lm.getString("Grants an additional\n 125 health\nIncreases crit chance\nby 6%"));
		setItemSlotType(ItemSlotType.ROBE_ARMOR);
	}

	@Override
	public void resetCooldown() {

	}

    public int getCritChanceIncrease() {
        return 6;
    }

	@Override
	public int preFirstTurnBuff(BaseCharacter wearer) {
		wearer.preGameIncreaseHealth(125);
		return 125;
	}
}
