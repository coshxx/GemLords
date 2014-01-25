package de.cosh.anothermanager.Characters;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;

import de.cosh.anothermanager.AnotherManager;
import de.cosh.anothermanager.Items.BaseItem;

/**
 * Created by cosh on 10.12.13.
 */
public class Player extends BaseCharacter {
	private Enemy lastEnemey;
	public boolean[] levelDone;
	private int lives;
	private PlayerInventory playerInventory;
	private ActionBar actionBar;

	public PlayerInventory getInventory() {
		return playerInventory;
	}

	public Player(final AnotherManager myGame) {
		super();
		levelDone = new boolean[200];
		for (int x = 0; x < 200; x++) {
			levelDone[x] = false;
		}
		lives = 5;

		playerInventory = new PlayerInventory();
		actionBar = new ActionBar();
	}

	@Override
	public void addToBoard(final Group foreGround) {
		super.addToBoard(foreGround);
		setHealthBarPosition(0, 25, AnotherManager.VIRTUAL_WIDTH, 50);
		foreGround.addActor(getHealthBar());
		actionBar.addToBoard(foreGround);
	}

	public void decreaseLife() {
		lives--;
	}

	public void draw(final SpriteBatch batch, final float parentAlpha) {
		for (int i = 0; i < getDebuffs().size; i++) {
			getDebuffs().get(i).drawCooldown(batch, parentAlpha);
		}
		for (int i = 0; i < getBuffs().size; i++) {
			getBuffs().get(i).drawCooldown(batch, parentAlpha);
		}
		for (int i = 0; i < playerInventory.getAllItems().size; i++ ) {
			BaseItem item = playerInventory.getAllItems().get(i);
			if( item.isAddedToActionBar() ) {
				if( item.getItemSlotType() == BaseItem.ItemSlotType.POTION )
					item.drawCooldown(batch, parentAlpha);
			}
		}
	}

	@Override
	public void turn() {
		super.turn();
		for( int i = 0; i < playerInventory.getAllItems().size; i++ ) {
			BaseItem currentItem = playerInventory.getAllItems().get(i);
			if( currentItem.isAddedToActionBar() ) {
				if( currentItem.getItemSlotType() == BaseItem.ItemSlotType.POTION ) {
					currentItem.turn();
				}
			}
		}
	}

	public Enemy getLastEnemy() {
		return lastEnemey;
	}

	public int getLives() {
		return lives;
	}

	public void levelDone(int i) {
		levelDone[i] = true;
	}

	public void setLastEnemy(final Enemy e) {
		lastEnemey = e;
	}

	public int getItemBuffsHP() {
		int count = 0;
		for (BaseItem i : playerInventory.getAllItems() ) {
			if( i.isAddedToActionBar() ) {
				if( i.getItemSlotType() == BaseItem.ItemSlotType.ARMOR ) {
					count += i.preFirstTurnBuff(this);
				}
			}
		}
		return count;
	}


	public ActionBar getActionBar() {
		return actionBar;
	}
}
