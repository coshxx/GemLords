package de.cosh.anothermanager.Items;

import com.badlogic.gdx.graphics.Texture;
import de.cosh.anothermanager.AnotherManager;
import de.cosh.anothermanager.Characters.BaseCharacter;
import de.cosh.anothermanager.Characters.Damage;

import java.util.Random;


public class ItemRing extends BaseItem {

    private Random random;

	public ItemRing() {
		super(AnotherManager.assets.get("data/textures/ring.png", Texture.class));
		itemNumber = 9;
		setItemName("Basic Ring");
		setItemText("Grants 10% crit chance\nfor basic swaps");
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

    public int getAdditionalDamage(Damage originalDamage) {
        int additionalDamage = 0;

        int randChance = random.nextInt(100);

        if( randChance <= 10 ) {
            additionalDamage = originalDamage.damage;
            originalDamage.isCrit = true;
            System.out.println("The ring crits: " + additionalDamage);
        }
        return additionalDamage;
    }
}
