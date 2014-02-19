package de.cosh.anothermanager.Items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import de.cosh.anothermanager.Characters.BaseCharacter;
import de.cosh.anothermanager.GemLord;

import java.util.Random;


public class ItemBetterShield extends BaseItem {

    private Random r;

	public ItemBetterShield() {
		super(GemLord.assets.get("data/textures/bettershield.png", Texture.class));
		itemNumber = 11;
		setItemName("Decent Shield");
		setItemText("20% chance to block\n5-10 damage\n10% chance to heal for\nthe blocked amount");
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
        int inHundret = r.nextInt(100+1);
        if( inHundret < 20 ) {
            addAction(Actions.sequence(Actions.scaleTo(2f, 2f, 0.15f), Actions.scaleTo(1f, 1f, 0.15f)));
            GemLord.getInstance().soundPlayer.playBlock();
            int reduce = MathUtils.random(5, 10);
            if( MathUtils.random(1, 100) <= 10 ) {
                System.out.println("The shield heals:" + reduce);
                GemLord.getInstance().player.increaseHealth(reduce);
            }
            System.out.println("The shield blocks: " + reduce );
            return (incomingDamage-reduce < 0) ? 0 : incomingDamage-reduce;
        }
        return incomingDamage;
    }
}
