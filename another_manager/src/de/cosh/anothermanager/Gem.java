package de.cosh.anothermanager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by cosh on 11.12.13.
 */
public class Gem extends Image {

    public enum GemType {
        TYPE_BLUE ("data/ball_blue.png"),
        TYPE_GREEN ("data/ball_green.png"),
        TYPE_PURPLE ("data/ball_purple.png"),
        TYPE_RED ("data/ball_red.png"),
        TYPE_WHITE ("data/ball_white.png"),
        TYPE_YELLOW ("data/ball_yellow.png");

        private String texturePath;

        GemType(String texturePath) {
            this.texturePath = texturePath;
        }

        public String getTexturePath() {
            return texturePath;
        }
    }
    private Texture gemTexture;
    private AnotherManager myGame;
    private Vector2 current_position;
    private Vector2 destination;
    private GemType gemType;

    public Gem(AnotherManager game, GemType g) {
        super(game.assets.get(g.getTexturePath(), Texture.class));
        gemType = g;
        myGame = game;
    }

    public GemType getGemType() {
        return gemType;
    }
}
