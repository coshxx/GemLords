package de.cosh.anothermanager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by cosh on 16.12.13.
 */
public class Enemy extends Image {
    private HealthBar healthBar;
    private AnotherManager myGame;

    public Enemy(Texture t, AnotherManager myGame) {
        super(t);
        this.myGame = myGame;
    }

    public void init() {
        healthBar = new HealthBar();
        healthBar.init(100, myGame);
    }

    public void setHealthBarPosition(int left, int bot, int width, int height) {
        healthBar.setPosition(left, bot, width, height);
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        healthBar.draw(batch, parentAlpha);
    }

    public void damage(int damage) {
        healthBar.hit(damage);
    }

    @Override
    public void act(float delta) {
        healthBar.update(delta);
    }

    public int getHealth() {
        return healthBar.getHealthpoints();
    }
}
