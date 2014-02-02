package de.cosh.anothermanager.Items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import de.cosh.anothermanager.AnotherManager;
import de.cosh.anothermanager.Characters.BaseCharacter;
import de.cosh.anothermanager.Characters.Player;

/**
 * Created by cosh on 20.01.14.
 */
 
public class ItemPocketWatch extends BaseItem implements UseItem {
	private int cooldown;
	private Integer currentCooldown;
	private BitmapFont bmf;

	public ItemPocketWatch() {
		super(AnotherManager.assets.get("data/textures/pocketwatch.png", Texture.class));
		itemNumber = 4;
		setItemName("Simaohs Pocketwatch");
		setItemText("Undo 50% of received damage\nin the last turn");
		setItemSlotType(ItemSlotType.ACTIVE);
		Skin s = AnotherManager.assets.get("data/ui/uiskin.json", Skin.class);
		bmf = s.getFont("default-font");

		cooldown = 99;
		currentCooldown = 0;
		bmf = new BitmapFont();
	}

	@Override
	public void onUse() {
		if (currentCooldown <= 0) {
			AnotherManager.getInstance();
			AnotherManager.soundPlayer.playPocketwatch();
			Player player = AnotherManager.getInstance().player;
			player.increaseHealth(player.getLastTurnDamageReceived()/2);
			addAction(Actions.sequence(Actions.scaleTo(2f, 2f, 0.15f), Actions.scaleTo(1f, 1f, 0.15f)));
			currentCooldown = cooldown;
		}
	}

	@Override
	public void drawCooldown(SpriteBatch batch, float parentAlpha) {
		if( currentCooldown <= 0 )
			bmf.draw(batch, "Ready", getX(), getY() );
		else bmf.draw(batch, currentCooldown.toString(), getX(), getY());
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
