package de.cosh.anothermanager.Characters;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;

import de.cosh.anothermanager.AnotherManager;

/**
 * Created by cosh on 11.01.14.
 */
public class BaseCharacter {
	private Group characterGroup;
	private final Array<Debuff> debuffs;
	private transient HealthBar healthBar;
	private int healthPoints;
	private transient AnotherManager myGame;

	public BaseCharacter(final AnotherManager myGame, final int hp) {
		this.healthPoints = hp;
		this.myGame = myGame;
		healthBar = new HealthBar();
		healthBar.init(hp, myGame);
		debuffs = new Array<Debuff>();
	}

	public void addDebuff(final Debuff debuff) {
		debuffs.add(debuff);
		debuff.setPosition(healthBar.getWidth() - (45 * debuffs.size), healthBar.getY() + healthBar.getHeight() + 30);
		debuff.addDebuffToGroup(getCharacterGroup());
	}

	public void addToBoard(final Group foreGround) {
		characterGroup = foreGround;
	}

	public void damage(final int damage) {
		healthBar.hit(damage);
	}

	public Group getCharacterGroup() {
		return characterGroup;
	}

	public Array<Debuff> getDebuffs() {
		return debuffs;
	}

	public int getHealth() {
		return healthBar.getHealthpoints();
	}

	public Actor getHealthBar() {
		return healthBar;
	}

	public void init(final int hp) {
		healthBar = new HealthBar();
		healthBar.init(hp, myGame);
	}

	public void setHealth(final int hp) {
		healthPoints = hp;
		healthBar.init(healthPoints, myGame);
	}

	public void setHealthBarPosition(final int left, final int bot, final int width, final int height) {
		healthBar.setPosition(left, bot, width, height);
	}

	public void turn() {
		for (int i = 0; i < getDebuffs().size; i++) {
			if (getDebuffs().get(i).turn()) {
				debuffs.removeIndex(i);
				i--;
			}
		}
	}
}
