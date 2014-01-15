package de.cosh.anothermanager.Characters;

import com.badlogic.gdx.scenes.scene2d.Group;
import de.cosh.anothermanager.AnotherManager;

/**
 * Created by cosh on 10.12.13.
 */
public class Player extends BaseCharacter {
    private AnotherManager myGame;
    private int lives;
    private Enemy lastEnemey;
    public boolean[] levelDone;
    private int currentLevel;

    public Player(AnotherManager myGame) {
        super(myGame, 500);
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

    public void addToBoard(Group foreGround) {
        init(100);
        setHealthBarPosition(0, 25, myGame.VIRTUAL_WIDTH, 50);
        foreGround.addActor(getHealthBar());
    }
}
