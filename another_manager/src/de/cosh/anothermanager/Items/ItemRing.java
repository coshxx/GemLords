package de.cosh.anothermanager.Items;

import com.badlogic.gdx.graphics.Texture;
import de.cosh.anothermanager.GemLord;
import de.cosh.anothermanager.Characters.BaseCharacter;
import de.cosh.anothermanager.Characters.Damage;

import java.util.Random;


public class ItemRing extends BaseItem {

    private Random random;

	public ItemRing() {
		super(GemLord.assets.get("data/textures/ring.png", Texture.class));
		itemNumber = 9;
		setItemName("Basic Ring");
		setItemText("Increases crit chance\nby 5% for swaps");
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
