package de.cosh.gemlords.Items;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import de.cosh.gemlords.Characters.BaseCharacter;
import de.cosh.gemlords.LanguageManager;

import java.util.Random;


public class ItemAmulet extends BaseItem {
    private Random r;

	public ItemAmulet() {
		super("itemamulet");
        LanguageManager lm = LanguageManager.getInstance();
		itemNumber = 8;
		setItemName(lm.getString("Amulet of Evasion"));
		setItemText(lm.getString("10% chance to\ncompletely avoid\nall damage"));
		setItemSlotType(ItemSlotType.AMULET_PASSIVE);
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
        if( inHundret < 10 ) {
            addAction(Actions.sequence(Actions.scaleTo(2f, 2f, 0.15f), Actions.scaleTo(1f, 1f, 0.15f)));
            //GemLord.getInstance().soundPlayer.playBlock();
            return 0;
        }
        return incomingDamage;
    }
}
