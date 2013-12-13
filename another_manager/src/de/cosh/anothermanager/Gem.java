package de.cosh.anothermanager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by cosh on 11.12.13.
 */
public class Gem extends Image {
    private Texture gemTexture;
    private AnotherManager myGame;
    private Vector2 current_position;
    private Vector2 destination;

    public Gem(AnotherManager game, Texture t) {
        super(t);
        myGame = game;
    }
}
