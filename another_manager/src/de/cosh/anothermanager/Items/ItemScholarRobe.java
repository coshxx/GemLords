package de.cosh.anothermanager.Items;

import com.badlogic.gdx.graphics.Texture;

import de.cosh.anothermanager.GemLord;
import de.cosh.anothermanager.Characters.BaseCharacter;


public class ItemScholarRobe extends BaseItem {

	public ItemScholarRobe() {
		super(GemLord.assets.get("data/textures/robe_scholar.png", Texture.class));
		itemNumber = 2;
		setItemName("Scholars Robe");
		setItemText("Grants an additional\n 50 hp");
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
