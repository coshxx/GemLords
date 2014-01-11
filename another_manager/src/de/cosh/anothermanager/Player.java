package de.cosh.anothermanager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by cosh on 10.12.13.
 */
public class Player extends Character {
    private AnotherManager myGame;
    private int lives;
    private Enemy lastEnemey;
    public boolean[] levelDone;
    private int currentLevel;
    private HealthBar healthBar;

    public Player(AnotherManager myGame) {
        super(myGame, 100);
        this.myGame = myGame;
        currentLevel = 0;
        levelDone = new boolean[200];
        for (int x = 0; x < 200; x++)
            levelDone[x] = false;
        lives = 5;
    }

    public void levelDone() {
        levelDone[currentLevel] = true;
        currentLevel++;
    }

    public void setLastEnemy(Enemy e) {
        lastEnemey = e;
    }

    public Enemy getLastEnemy() {
        return lastEnemey;
    }

    public int getLives() {
        return lives;
    }

    public void decreaseLife() {
        lives--;
    }
}
