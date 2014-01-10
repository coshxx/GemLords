package de.cosh.anothermanager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by cosh on 10.12.13.
 */
public class Player extends Actor {
    private AnotherManager myGame;
    private int lives;


    public boolean[] levelDone;
    private int currentLevel;

    private HealthBar healthBar;

    public Player(AnotherManager myGame) {
        this.myGame = myGame;
        currentLevel = 0;
        lives = 5;
        healthBar = new HealthBar();
        levelDone = new boolean[200];
        for( int x = 0; x < 200; x++ )
            levelDone[x] = false;
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

    public int getHealth() {
        return healthBar.getHealthpoints();
    }

    public void init() {
        lives = 5;
        healthBar = new HealthBar();
        healthBar.init(100, myGame);
    }

    public void levelDone() {
        levelDone[currentLevel] = true;
        currentLevel++;
    }
}
