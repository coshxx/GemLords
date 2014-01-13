package de.cosh.anothermanager.Characters;

import com.badlogic.gdx.scenes.scene2d.Actor;
import de.cosh.anothermanager.AnotherManager;
import de.cosh.anothermanager.GUI.HealthBar;

/**
 * Created by cosh on 11.01.14.
 */
public class Character {
    private HealthBar healthBar;
    private int healthPoints;
    private AnotherManager myGame;

    public Character(AnotherManager myGame, int hp) {
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

    public void init() {
        healthBar = new HealthBar();
        healthBar.init(100, myGame);
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
