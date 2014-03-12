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


public class ItemSword extends BaseItem {
    private int cooldown;
    private Integer currentCooldown;
    private Random random;

	public ItemSword() {
		super("itemsword");
        LanguageManager lm = LanguageManager.getInstance();
		itemNumber = 19;
		setItemName(lm.getString("Sword"));
		setItemText(lm.getString("Throw your Sword for\n1-60 Damage\nCooldown: 10"));
		setItemSlotType(ItemSlotType.SWORD_THROW);
        cooldown = 10;
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
            GemLord.soundPlayer.playSwordThrow();
            int damage = random.nextInt( ( (60 - 1)+1 ));
            damage += 1;

            Damage dmg = new Damage();
            dmg.damage = damage;
            GemLord.getInstance().gameScreen.getBoard().getEnemy().damage(dmg);
            addAction(Actions.sequence(Actions.scaleTo(2f, 2f, 0.15f), Actions.scaleTo(1f, 1f, 0.15f)));
            currentCooldown = cooldown;
            TextureAtlas atlas = GemLord.assets.get("data/textures/pack.atlas", TextureAtlas.class);
            Image projectile = new Image(atlas.findRegion("itemsword"));
            projectile.setPosition(getX(), getY());

            float targetX = GemLord.getInstance().gameScreen.getBoard().getEnemy().getImage().getX();
            float targetY = GemLord.getInstance().gameScreen.getBoard().getEnemy().getImage().getY();

            projectile.addAction(Actions.parallel(
                    Actions.sequence(
                        Actions.moveTo(targetX, targetY, 0.5f),
                        Actions.fadeOut(0.5f),
                        Actions.removeActor()
                        ),
                        Actions.rotateBy(359, 0.5f)
                    ));

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
