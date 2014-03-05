package de.cosh.gemlords.Items;

import com.badlogic.gdx.math.MathUtils;
import de.cosh.gemlords.Characters.BaseCharacter;
import de.cosh.gemlords.Characters.Damage;
import de.cosh.gemlords.LanguageManager;

import java.util.Random;


public class ItemRunedDagger extends BaseItem {
    private Random random;

	public ItemRunedDagger() {
		super("itemruneddagger");
        LanguageManager lm = LanguageManager.getInstance();
		itemNumber = 16;
		setItemName(lm.getString("Runed Dagger"));
		setItemText(lm.getString("Increases your\ndamage by 3 - 6"));
		setItemSlotType(ItemSlotType.WEAPON_PASSIVE);
        random = new Random();
	}

	@Override
	public void resetCooldown() {

	}

	@Override
	public int preFirstTurnBuff(BaseCharacter wearer) {
        return 0;
	}

    public int getAdditionalDamage(Damage damage) {
        int additionalDamage = MathUtils.random(3, 6);
        return additionalDamage;


    }
}
