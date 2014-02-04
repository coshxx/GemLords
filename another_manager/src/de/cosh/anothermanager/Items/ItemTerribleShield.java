package de.cosh.anothermanager.Items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import de.cosh.anothermanager.AnotherManager;
import de.cosh.anothermanager.Characters.BaseCharacter;

import java.util.Random;


public class ItemTerribleShield extends BaseItem {

    private Random r;

	public ItemTerribleShield() {
		super(AnotherManager.assets.get("data/textures/shield.png", Texture.class));
		itemNumber = 5;
		setItemName("Terrible Shield");
		setItemText("20% chance to block\n5 damage");
		setItemSlotType(ItemSlotType.SHIELD);
        r = new Random();
	}

	@Override
	public void resetCooldown() {

	}

	@Override
	public int preFirstTurnBuff(BaseCharacter wearer) {
        return 0;
	}

    @Override
    public int tryToReduceDamage(int incomingDamage) {
        int inHundret = r.nextInt(100);
        if( inHundret < 20 ) {
            addAction(Actions.sequence(Actions.scaleTo(2f, 2f, 0.15f), Actions.scaleTo(1f, 1f, 0.15f)));
            AnotherManager.getInstance().soundPlayer.playBlock();
            return (incomingDamage-5 < 0) ? 0 : incomingDamage-5;
        }
        return incomingDamage;
    }
}
