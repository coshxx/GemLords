package de.cosh.anothermanager.Characters;

import com.badlogic.gdx.scenes.scene2d.Actor;
import de.cosh.anothermanager.AnotherManager;

/**
 * Created by cosh on 11.01.14.
 */
public class BaseCharacter {
    private transient HealthBar healthBar;
    private int healthPoints;
    private transient AnotherManager myGame;

    public BaseCharacter(AnotherManager myGame, int hp) {
        this.healthPoints = hp;
        this.myGame = myGame;
        healthBar = new HealthBar();
        healthBar.init(hp, myGame);
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

    public void setHealthBarPosition(int left, int bot, int width, int height) {
        healthBar.setPosition(left, bot, width, height);
    }

    public Actor getHealthBar() {
        return healthBar;
    }
}
