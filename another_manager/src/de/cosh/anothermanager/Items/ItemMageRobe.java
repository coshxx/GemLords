package de.cosh.anothermanager.Items;

import com.badlogic.gdx.graphics.Texture;
import de.cosh.anothermanager.Characters.BaseCharacter;
import de.cosh.anothermanager.GemLord;


public class ItemMageRobe extends BaseItem {

	public ItemMageRobe() {
		super(GemLord.assets.get("data/textures/robe_mage.png", Texture.class));
		itemNumber = 10;
		setItemName("Mages Robe");
		setItemText("Grants an additional\n 75 health\nIncreases crit chance\nby 3%");
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
		wearer.preGameIncreaseHealth(50);
		return 50;
	}
}
