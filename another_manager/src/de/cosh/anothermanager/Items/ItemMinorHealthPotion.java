package de.cosh.anothermanager.Items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import de.cosh.anothermanager.AnotherManager;
import de.cosh.anothermanager.Characters.BaseCharacter;
import de.cosh.anothermanager.SwapGame.Board;

import java.util.Random;

/**
 * Created by cosh on 20.01.14.
 */
public class ItemMinorHealthPotion extends BaseItem implements UseItem {
	private int cooldown;
	private Integer currentCooldown;
    private Random random;

	public ItemMinorHealthPotion() {
		super(AnotherManager.assets.get("data/textures/minorhealthpotion.png", Texture.class));
		itemNumber = 1;
		setItemName("Minor potion");
		setItemText("Recover 10-20 hp\nCooldown: 10");
		setItemSlotType(ItemSlotType.POTION);
		cooldown = 10;
		currentCooldown = 0;
        random = new Random();
	}

	@Override
	public void onUse() {
        if( AnotherManager.getInstance().gameScreen.getBoard().getBoardState() != Board.BoardState.STATE_IDLE )
            return;
		if (currentCooldown <= 0) {
			AnotherManager.getInstance();
			AnotherManager.soundPlayer.playGulp();

            int healthRegenned = random.nextInt((20 - 10)+1);
            healthRegenned += 10;
			AnotherManager.getInstance().player.increaseHealth(healthRegenned);
			addAction(Actions.sequence(Actions.scaleTo(2f, 2f, 0.15f), Actions.scaleTo(1f, 1f, 0.15f)));
			currentCooldown = cooldown;
		}
	}

	@Override
	public void drawCooldown(SpriteBatch batch, float parentAlpha) {
        bmf.setColor(1f, 1f, 1f, getColor().a * parentAlpha);
        if( currentCooldown <= 0 )
            bmf.draw(batch, "Ready", getX(), getY()+50 );
        else bmf.draw(batch, currentCooldown.toString(), getX()+25, getY()+50);
	}

	@Override
	public void turn() {
		currentCooldown--;
	}

	@Override
	public void resetCooldown() {
		currentCooldown = 0;
	}

	@Override
	public int preFirstTurnBuff(BaseCharacter wearer) {
		return 0;
	}
}
