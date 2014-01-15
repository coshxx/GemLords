package de.cosh.anothermanager.Characters;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import de.cosh.anothermanager.AnotherManager;

/**
 * Created by cosh on 11.01.14.
 */
public class BaseCharacter {
    private transient HealthBar healthBar;
    private int healthPoints;
    private transient AnotherManager myGame;
    private Array<Debuff> debuffs;
    private Group characterGroup;

    public BaseCharacter(AnotherManager myGame, int hp) {
        this.healthPoints = hp;
        this.myGame = myGame;
        healthBar = new HealthBar();
        healthBar.init(hp, myGame);
        debuffs = new Array<Debuff>();
    }

    public int getHealth() {
        return healthBar.getHealthpoints();
    }

    public void setHealth(int hp) {
        healthPoints = hp;
        healthBar.init(healthPoints, myGame);
    }

    public void init(int hp) {
        healthBar = new HealthBar();
        healthBar.init(hp, myGame);
    }

    public void damage(int damage) {
        healthBar.hit(damage);
    }

    public void turn() {
        for( int i = 0; i < getDebuffs().size; i++ ) {
            if( getDebuffs().get(i).turn()) {
                debuffs.removeIndex(i);
                i--;
            }
        }
    }

    public void setHealthBarPosition(int left, int bot, int width, int height) {
        healthBar.setPosition(left, bot, width, height);
    }

    public Actor getHealthBar() {
        return healthBar;
    }

    public void addDebuff(Debuff debuff) {
        debuffs.add(debuff);
        debuff.setPosition( healthBar.getWidth()-45, healthBar.getY() + healthBar.getHeight() + 30 );
        debuff.addDebuff(getCharacterGroup());
    }

    public Array<Debuff> getDebuffs() {
        return debuffs;
    }

    public void addToBoard(Group foreGround) {
        characterGroup = foreGround;
    }

    public Group getCharacterGroup() { return characterGroup; }
}
