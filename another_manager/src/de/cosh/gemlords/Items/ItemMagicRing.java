package de.cosh.gemlords.Items;

import de.cosh.gemlords.Characters.BaseCharacter;
import de.cosh.gemlords.LanguageManager;

import java.util.Random;


public class ItemMagicRing extends BaseItem {

    private Random random;

	public ItemMagicRing() {
		super("ring");
        LanguageManager lm = LanguageManager.getInstance();
		itemNumber = 15;
		setItemName(lm.getString("Magic Ring"));
		setItemText(lm.getString("Increases crit chance\nby 8% for swaps"));
		setItemSlotType(ItemSlotType.RING_PASSIVE);
        random = new Random();
	}

	@Override
	public void resetCooldown() {

	}

	@Override
	public int preFirstTurnBuff(BaseCharacter wearer) {
        return 0;
	}

    public int getCritChanceIncrease() {
        return 8;
    }
}
