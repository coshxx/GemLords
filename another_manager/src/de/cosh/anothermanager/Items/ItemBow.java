package de.cosh.anothermanager.Items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import de.cosh.anothermanager.AnotherManager;
import de.cosh.anothermanager.Characters.BaseCharacter;
import de.cosh.anothermanager.SwapGame.Board;

import java.util.Random;


public class ItemBow extends BaseItem {
    private int cooldown;
    private Integer currentCooldown;
    private Random random;

	public ItemBow() {
		super(AnotherManager.assets.get("data/textures/itembow.png", Texture.class));
		itemNumber = 7;
		setItemName("Fox Bow");
		setItemText("Deal 5 - 20 damage\nCooldown: 5");
		setItemSlotType(ItemSlotType.ACTIVE);
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
        if( AnotherManager.getInstance().gameScreen.getBoard().getBoardState() != Board.BoardState.STATE_IDLE )
            return;
        if (currentCooldown <= 0) {
            AnotherManager.getInstance();
            //AnotherManager.soundPlayer.playGulp();

            int damage = random.nextInt( ( 20 - 5 ));
            damage += 5;

            AnotherManager.getInstance().gameScreen.getBoard().getEnemy().damage(damage);
            addAction(Actions.sequence(Actions.scaleTo(2f, 2f, 0.15f), Actions.scaleTo(1f, 1f, 0.15f)));
            currentCooldown = cooldown;

            Image projectile = new Image(AnotherManager.assets.get("data/textures/itemarrow.png", Texture.class));
            projectile.setPosition(getX(), getY());

            float targetX = AnotherManager.getInstance().gameScreen.getBoard().getEnemy().getImage().getX();
            float targetY = AnotherManager.getInstance().gameScreen.getBoard().getEnemy().getImage().getY();

            projectile.addAction(Actions.sequence(
                    Actions.moveTo(targetX, targetY, 0.25f),
                    Actions.fadeOut(0.25f),
                    Actions.removeActor())
            );

            AnotherManager.getInstance().gameScreen.getBoard().getEffectGroup().addActor(projectile);
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
