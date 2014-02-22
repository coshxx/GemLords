package de.cosh.gemlords.Items;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import de.cosh.gemlords.GemLord;
import de.cosh.gemlords.Characters.BaseCharacter;
import de.cosh.gemlords.Characters.Player;
import de.cosh.gemlords.LanguageManager;
import de.cosh.gemlords.SwapGame.Board;

/**
 * Created by cosh on 20.01.14.
 */
 
public class ItemPocketWatch extends BaseItem implements UseItem {
	private int cooldown;
	private Integer currentCooldown;
	private BitmapFont bmf;

	public ItemPocketWatch() {
		super("pocketwatch");
        LanguageManager lm = LanguageManager.getInstance();
		itemNumber = 4;
		setItemName("Simaohs Pocketwatch");
		setItemText("Undo 100%\nof received damage\nCooldown: 99");
		setItemSlotType(ItemSlotType.WATCH_ACTIVE);
		Skin s = GemLord.assets.get("data/ui/uiskin.json", Skin.class);
		bmf = s.getFont("default-font");

		cooldown = 99;
		currentCooldown = 0;
		bmf = new BitmapFont();
	}

	@Override
	public void onUse() {
        if( GemLord.getInstance().gameScreen.getBoard().getBoardState() != Board.BoardState.STATE_IDLE )
            return;
        if( !GemLord.getInstance().gameScreen.getBoard().isPlayerTurn() )
            return;

		if (currentCooldown <= 0) {
			GemLord.getInstance();
			GemLord.soundPlayer.playPocketwatch();
			Player player = GemLord.getInstance().player;
			player.increaseHealth(player.getLastTurnDamageReceived());
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
