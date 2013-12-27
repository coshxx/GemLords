package de.cosh.anothermanager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by cosh on 10.12.13.
 */
public class Player extends Actor {
    private int currentLevel;
    private int lives;

    private HealthBar healthBar;

    public Player(AnotherManager myGame) {
        currentLevel = 0;
        lives = 5;
        healthBar = new HealthBar();
        healthBar.init(100, myGame);
    }

    public void setHealthBarPosition(int left, int bot, int width, int height) {
        healthBar.setPosition(left, bot, width, height);
    }

    @Override
    public void draw (SpriteBatch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        healthBar.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        healthBar.update(delta);
    }

    public void damage(int damage) {
        healthBar.hit(damage);
    }
}
