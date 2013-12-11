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

    public enum GemColor {
        GEM_RED,
        GEM_WHITE,
        GEM_BLUE,
        GEM_YELLOW,
        GEM_PURPLE,
        GEM_GREEN
    }

    ;

    public Gem(AnotherManager game, GemColor gemColor) {
        myGame = game;
        // TODO: change this shit, use only white-gem + color
        switch (gemColor) {
            case GEM_RED:
                gemTexture = game.assets.get("data/ball_red.png", Texture.class);
                break;
        }
    }


}
