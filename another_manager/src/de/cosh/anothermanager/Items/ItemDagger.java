package de.cosh.anothermanager.Items;

import com.badlogic.gdx.graphics.Texture;
import de.cosh.anothermanager.GemLord;
import de.cosh.anothermanager.Characters.BaseCharacter;

import java.util.Random;


public class ItemDagger extends BaseItem {
    private Random random;

	public ItemDagger() {
		super(GemLord.assets.get("data/textures/itemdagger.png", Texture.class));
		itemNumber = 6;
		setItemName("Valors Dagger");
		setItemText("Increases your\ndamage by 1 - 5");
		setItemSlotType(ItemSlotType.WEAPONPASSIVE);
        random = new Random();
	}

	@Override
	public void resetCooldown() {

	}

	@Override
	public int preFirstTurnBuff(BaseCharacter wearer) {
        return 0;
	}

    public int getAdditionalDamage(int originalDamage) {
        int damage = random.nextInt((5 - 1)+1);
        damage += 1;
        return damage;
    }
}
