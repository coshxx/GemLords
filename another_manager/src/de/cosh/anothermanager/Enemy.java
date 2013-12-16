package de.cosh.anothermanager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by cosh on 16.12.13.
 */
public class Enemy extends Image {
    private int health_points;

    public Enemy(Texture t) {
        super(t);
    }
}
