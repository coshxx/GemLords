package de.cosh.gemlords.Characters;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;

/**
 * Created by cosh on 11.01.14.
 */
public class BaseCharacter {
	private Group characterGroup;
	private final Array<Debuff> debuffs;
	private final Array<Buff> buffs;
	private HealthBar healthBar;

	BaseCharacter() {
		healthBar = new HealthBar();
		debuffs = new Array<Debuff>();
		buffs = new Array<Buff>();
	}

	public void addDebuff(final Debuff debuff) {
		debuffs.add(debuff);
		debuff.setPosition(healthBar.getWidth() - (45 * debuffs.size), healthBar.getY() + healthBar.getHeight());
		debuff.addDebuffToGroup(getCharacterGroup());
	}

	public void addBuff(final Buff buff) {
		buffs.add(buff);
		buff.setPosition(0, healthBar.getY() + healthBar.getHeight());
		buff.addBuffToGroup(getCharacterGroup());
	}

	void addToBoard(final Group foreGround) {
		characterGroup = foreGround;
	}

	public void damage(Damage damage) {
		healthBar.hit(damage);
	}

	Group getCharacterGroup() {
		return characterGroup;
	}

	Array<Debuff> getDebuffs() {
		return debuffs;
	}

	Array<Buff> getBuffs() {
		return buffs;
	}

	public int getHealth() {
		return healthBar.getHp(); }

	public HealthBar getHealthBar() {
		return healthBar;
	}

	public void init(final int hp) {
		healthBar = new HealthBar();
		healthBar.init(hp);
		debuffs.clear();
		buffs.clear();
	}

	public void setHealth(final int hp) {
		healthBar.init(hp);
	}

	void setHealthBarPosition(final int left, final int bot, final int width, final int height) {
		healthBar.setPosition(left, bot, width, height);
        healthBar.setY(bot);
	}

	void turn() {
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
		healthBar.increaseHealth(hp);
	}

    public void preGameIncreaseHealth(int hp) {
        getHealthBar().increaseHealth(hp);
    }
}
