package de.cosh.gemlords.Items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import de.cosh.gemlords.Characters.BaseCharacter;
import de.cosh.gemlords.Characters.Damage;
import de.cosh.gemlords.GemLord;
import de.cosh.gemlords.LanguageManager;
import de.cosh.gemlords.SwapGame.Board;

import java.util.Random;


public class ItemHuntingBow extends BaseItem {
    private int cooldown;
    private Integer currentCooldown;
    private Random random;

	public ItemHuntingBow() {
		super("itemhuntingbow");
        LanguageManager lm = LanguageManager.getInstance();
		itemNumber = 12;
		setItemName(lm.getString("Hunting Bow"));
		setItemText(lm.getString("Deal 10 - 25 damage\nCooldown: 5"));
		setItemSlotType(ItemSlotType.BOW_ACTIVE);
        cooldown = 5;
        currentCooldown = 0;
        random = new Random();
	}

	@Override
	public void resetCooldown() {
        currentCooldown = 0;
	}

    @Override
    public void onUse() {
        if( GemLord.getInstance().gameScreen.getBoard().getBoardState() != Board.BoardState.STATE_IDLE )
            return;
        if( !GemLord.getInstance().gameScreen.getBoard().isPlayerTurn() )
            return;

        if (currentCooldown <= 0) {
            GemLord.soundPlayer.playBow();
            int damage = random.nextInt( ( (25 - 10)+1 ));
            damage += 10;

            Damage dmg = new Damage();
            dmg.damage = damage;
            GemLord.getInstance().gameScreen.getBoard().getEnemy().damage(dmg);
            addAction(Actions.sequence(Actions.scaleTo(2f, 2f, 0.15f), Actions.scaleTo(1f, 1f, 0.15f)));
            currentCooldown = cooldown;
            TextureAtlas atlas = GemLord.assets.get("data/textures/pack.atlas", TextureAtlas.class);
            Image projectile = new Image(atlas.findRegion("itemarrow"));
            projectile.setPosition(getX(), getY());

            float targetX = GemLord.getInstance().gameScreen.getBoard().getEnemy().getImage().getX();
            float targetY = GemLord.getInstance().gameScreen.getBoard().getEnemy().getImage().getY();

            projectile.addAction(Actions.sequence(
                    Actions.moveTo(targetX, targetY, 0.25f),
                    Actions.fadeOut(0.25f),
                    Actions.removeActor())
            );

            GemLord.getInstance().gameScreen.getBoard().getEffectGroup().addActor(projectile);
        }
    }

	@Override
	public int preFirstTurnBuff(BaseCharacter wearer) {
        return 0;
	}

    @Override
    public void turn() {
        currentCooldown--;
    }

    @Override
    public void drawCooldown(SpriteBatch batch, float parentAlpha) {
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
}
