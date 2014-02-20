package de.cosh.anothermanager.Items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import de.cosh.anothermanager.Characters.Damage;
import de.cosh.anothermanager.GemLord;
import de.cosh.anothermanager.Characters.BaseCharacter;

import java.util.Random;


public class ItemDagger extends BaseItem {
    private Random random;

	public ItemDagger() {
		super("itemdagger");
		itemNumber = 6;
		setItemName("Valors Dagger");
		setItemText("Increases your\ndamage by 2 - 5");
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
        int additionalDamage = MathUtils.random(2, 5);
        return additionalDamage;


    }
}
