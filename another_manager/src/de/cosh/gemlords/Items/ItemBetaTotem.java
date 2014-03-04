package de.cosh.gemlords.Items;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import de.cosh.gemlords.Characters.BaseCharacter;
import de.cosh.gemlords.Characters.Buff;
import de.cosh.gemlords.GemLord;
import de.cosh.gemlords.LanguageManager;
import de.cosh.gemlords.SwapGame.Board;

/**
 * Created by cosh on 20.01.14.
 */
public class ItemBetaTotem extends BaseItem implements UseItem {
	private int cooldown;
	private Integer currentCooldown;

	public ItemBetaTotem() {
		super("totem");
        LanguageManager lm = LanguageManager.getInstance();
		itemNumber = 14;
		setItemName(lm.getString("Beta Totem"));
		setItemText(lm.getString("Recover 1-4 health\neach turn\nCooldown: 99"));
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
			totemBuff.setup(1, 4, 99, GemLord.getInstance().player);
			GemLord.getInstance().player.addBuff(totemBuff);
			addAction(Actions.sequence(Actions.scaleTo(2f, 2f, 0.15f), Actions.scaleTo(1f, 1f, 0.15f)));
			currentCooldown = cooldown;
		}
	}

	@Override
    public void drawCooldown(Batch batch, float parentAlpha) {
        bmf.setColor(1f, 1f, 1f, getColor().a * parentAlpha);
        LanguageManager lm = LanguageManager.getInstance();
        if( currentCooldown <= 0 ) {
            bmf.setColor(0, 1, 0, parentAlpha);
            bmf.draw(batch, lm.getString("Ready"), getX() + (getWidth()/2)-(bmf.getBounds(lm.getString("Ready")).width/2), getY()+50 );
        }
        else {
            bmf.setScale(2f);
            bmf.setColor(1, 0, 0, parentAlpha);
            bmf.draw(batch, currentCooldown.toString(), getX() + (getWidth()/2)-(bmf.getBounds(currentCooldown.toString()).width/2), getY() + (getHeight()/2+bmf.getBounds(currentCooldown.toString()).height/2));
            bmf.setScale(1f);
        }
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
