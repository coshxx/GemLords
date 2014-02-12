package de.cosh.anothermanager.Items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import de.cosh.anothermanager.GemLord;
import de.cosh.anothermanager.Characters.BaseCharacter;
import de.cosh.anothermanager.Characters.Damage;
import de.cosh.anothermanager.SwapGame.Board;

import java.util.Random;


public class ItemBow extends BaseItem {
    private int cooldown;
    private Integer currentCooldown;
    private Random random;

	public ItemBow() {
		super(GemLord.assets.get("data/textures/itembow.png", Texture.class));
		itemNumber = 7;
		setItemName("Fox Bow");
		setItemText("Deal 5 - 12 damage\nCooldown: 5");
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
        if (currentCooldown <= 0) {
            GemLord.soundPlayer.playBow();
            int damage = random.nextInt( ( (12 - 5)+1 ));
            damage += 5;

            Damage dmg = new Damage();
            dmg.damage = damage;
            GemLord.getInstance().gameScreen.getBoard().getEnemy().damage(dmg);
            addAction(Actions.sequence(Actions.scaleTo(2f, 2f, 0.15f), Actions.scaleTo(1f, 1f, 0.15f)));
            currentCooldown = cooldown;

            Image projectile = new Image(GemLord.assets.get("data/textures/itemarrow.png", Texture.class));
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
        if( currentCooldown <= 0 )
            bmf.draw(batch, "Ready", getX(), getY()+50 );
        else bmf.draw(batch, currentCooldown.toString(), getX()+25, getY()+50);
    }
}
