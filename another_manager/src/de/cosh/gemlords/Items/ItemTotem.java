package de.cosh.gemlords.Items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import de.cosh.gemlords.GemLord;
import de.cosh.gemlords.Characters.BaseCharacter;
import de.cosh.gemlords.Characters.Buff;
import de.cosh.gemlords.SwapGame.Board;

/**
 * Created by cosh on 20.01.14.
 */
public class ItemTotem extends BaseItem implements UseItem {
	private int cooldown;
	private Integer currentCooldown;

	public ItemTotem() {
		super("totem");
		itemNumber = 3;
		setItemName("Alpha Totem");
		setItemText("Recover 1-3 health\neach turn\nCooldown: 99");
		setItemSlotType(ItemSlotType.TOTEM_ACTIVE);
		cooldown = 99;
		currentCooldown = 0;
	}

	@Override
	public void onUse() {
        if( GemLord.getInstance().gameScreen.getBoard().getBoardState() != Board.BoardState.STATE_IDLE )
            return;
        if( !GemLord.getInstance().gameScreen.getBoard().isPlayerTurn() )
            return;

		if (currentCooldown <= 0) {
			GemLord.getInstance();
			GemLord.soundPlayer.playTotem();
			Buff totemBuff = new Buff();
            TextureAtlas atlas = GemLord.assets.get("data/textures/pack.atlas", TextureAtlas.class);
			totemBuff.setBuffImage(new Image(atlas.findRegion("totem")));
			totemBuff.setup(1, 3, 99, GemLord.getInstance().player);
			GemLord.getInstance().player.addBuff(totemBuff);
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