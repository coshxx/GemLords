package de.cosh.anothermanager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by cosh on 13.01.14.
 */
public class Gem extends Image {
    private GemType gemType;
    private boolean isSpecialGem;
    private boolean markedForRemoval;

    public Gem(AnotherManager myGame, GemType g) {
        super(myGame.assets.get(g.getTexturePath(), Texture.class));
        this.gemType = g;
        this.isSpecialGem = false;
        this.markedForRemoval = false;
    }

    public void setToNone() {
        gemType = GemType.TYPE_NONE;
    }

    public void markGemForRemoval() {
        markedForRemoval = true;
    }

    public void convertToSpecialGem() {
        isSpecialGem = true;
    }

    public boolean equals(Gem b) {
        return gemType == b.gemType;
    }

    public void moveTo(int x, int y) {
        addAction(Actions.moveBy(x * getWidth(), y * getWidth(), 1.0f));
    }

    public enum GemType {
        TYPE_BLUE("data/ball_blue.png"),
        TYPE_GREEN("data/ball_green.png"),
        TYPE_PURPLE("data/ball_purple.png"),
        TYPE_RED("data/ball_red.png"),
        TYPE_WHITE("data/ball_white.png"),
        TYPE_YELLOW("data/ball_yellow.png"),
        TYPE_NONE("");
        private String texturePath;

        GemType(String texturePath) {
            this.texturePath = texturePath;
        }

        public String getTexturePath() {
            return texturePath;
        }
    }
}
