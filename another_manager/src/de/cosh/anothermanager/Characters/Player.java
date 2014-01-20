package de.cosh.anothermanager.Characters;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;

import de.cosh.anothermanager.AnotherManager;

/**
 * Created by cosh on 10.12.13.
 */
public class Player extends BaseCharacter {
	private Enemy lastEnemey;
	public boolean[] levelDone;
	private int lives;
	private final AnotherManager myGame;

	public Player(final AnotherManager myGame) {
		super();
		this.myGame = myGame;
		levelDone = new boolean[200];
		for (int x = 0; x < 200; x++) {
			levelDone[x] = false;
		}
		lives = 5;
	}

	@Override
	public void addToBoard(final Group foreGround) {
		super.addToBoard(foreGround);
		init(500);
		setHealthBarPosition(0, 25, myGame.VIRTUAL_WIDTH, 50);
		foreGround.addActor(getHealthBar());
	}

	public void decreaseLife() {
		lives--;
	}

	public void draw(final SpriteBatch batch, final float parentAlpha) {
		for (int i = 0; i < getDebuffs().size; i++) {
			getDebuffs().get(i).drawCooldown(batch, parentAlpha);
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

}
