package de.cosh.anothermanager.Characters;

import java.awt.Font;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
	private final Array<Buff> buffs;
	private transient HealthBar healthBar;
	private int healthPoints;

	public BaseCharacter() {
		healthBar = new HealthBar();
		debuffs = new Array<Debuff>();
		buffs = new Array<Buff>();
	}

	public void addDebuff(final Debuff debuff) {
		debuffs.add(debuff);
		debuff.setPosition(healthBar.getWidth() - (45 * debuffs.size), healthBar.getY() + healthBar.getHeight() + 30);
		debuff.addDebuffToGroup(getCharacterGroup());
	}
	
	public void addBuff(final Buff buff) {
		buffs.add(buff);
		buff.setPosition(0, healthBar.getY() + healthBar.getHeight() + 30);
		buff.addBuffToGroup(getCharacterGroup());
		
		
	}

	public void addToBoard(final Group foreGround) {
		characterGroup = foreGround;
	}

	public void damage(final int damage) {
        healthPoints -= damage;
		healthBar.hit(damage);
	}

	public Group getCharacterGroup() {
		return characterGroup;
	}

	public Array<Debuff> getDebuffs() {
		return debuffs;
	}
	
	public Array<Buff> getBuffs() {
		return buffs;
	}

	public int getHealth() {
		return healthPoints;
	}

	public Actor getHealthBar() {
		return healthBar;
	}

	public void init(final int hp) {
        healthPoints = hp;
		healthBar = new HealthBar();
		healthBar.init(hp);
		debuffs.clear();
		buffs.clear();
	}

	public void setHealth(final int hp) {
		healthPoints = hp;
		healthBar.init(healthPoints);
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
		for (int i = 0; i < getBuffs().size; i++) {
			if( getBuffs().get(i).turn() ) {
				buffs.removeIndex(i);
				i--;
			}
		}
	}

    public void increaseHealth(int hp) {
        healthPoints += hp;
        healthBar.increaseHealth(hp);
    }
}
