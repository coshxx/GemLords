package de.cosh.gemlords.Items;

import de.cosh.gemlords.Characters.BaseCharacter;
import de.cosh.gemlords.LanguageManager;

import java.util.Random;


public class ItemRing extends BaseItem {

    private Random random;

	public ItemRing() {
		super("ring");
        LanguageManager lm = LanguageManager.getInstance();
		itemNumber = 9;
		setItemName(lm.getString("Basic Ring"));
		setItemText(lm.getString("Increases crit chance\nby 5% for swaps"));
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
        return 5;
    }
}
