package de.cosh.anothermanager.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import de.cosh.anothermanager.AnotherManager;

/**
 * Created by cosh on 27.12.13.
 */
public class HealthBar extends Actor {
    private int healthpoints, maxHP;
    private AnotherManager myGame;

    private Texture emptyT, fullT;
    private NinePatch empty, full;

    private int left, bot, width, height;
    private float done;

    private BitmapFont bmf;

    public void init(int hp, AnotherManager myGame) {
        this.healthpoints = hp;
        this.maxHP = hp;
        this.myGame = myGame;

        emptyT = new Texture(Gdx.files.internal("data/empty.png"));
        fullT = new Texture(Gdx.files.internal("data/full.png"));

        empty = new NinePatch(new TextureRegion(emptyT, 24, 24), 8, 8, 8, 8);
        full = new NinePatch(new TextureRegion(fullT, 24, 24), 8, 8, 8, 8);
        done = 1f;

        bmf = new BitmapFont();
    }

    public void setPosition(int left, int bot, int width, int height) {
        this.left = left;
        this.bot = bot;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        empty.draw(batch, left, bot, width, height);
        if( healthpoints > 0 )
            full.draw(batch, left, bot, done * width, height);

        Integer health = healthpoints;
        Integer maxhealth = maxHP;

        bmf.setColor(1f, 1f, 1f, parentAlpha);
        bmf.draw(batch, health.toString() + " / " + maxhealth.toString(), left + (width/2), bot+25);
    }

    public void act(float delta) {
        done = (float)healthpoints / (float)maxHP;
    }

    public void hit(int damage) {
        healthpoints -= damage;
        if( healthpoints <= 0 ) {
            healthpoints = 0;
        }
    }

    public int getHealthpoints() {
        return healthpoints;
    }
}
