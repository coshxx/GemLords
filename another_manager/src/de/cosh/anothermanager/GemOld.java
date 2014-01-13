package de.cosh.anothermanager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by cosh on 11.12.13.
 */
public class GemOld extends Image {

    private AnotherManager myGame;
    private GemType gemType;
    private boolean justMoved;
    private boolean isSpecial;

    public GemOld(AnotherManager game, GemType g) {
        super(game.assets.get(g.getTexturePath(), Texture.class));
        gemType = g;
        myGame = game;
        justMoved = false;
        isSpecial = false;
    }

    public void setToNoGem() {
        gemType = GemType.TYPE_NONE;
        isSpecial = false;
    }

    public GemType getGemType() {
        return gemType;
    }

    public boolean isSpecial() {
        return isSpecial;
    }

    public void setMoved(boolean moved) {
        justMoved = moved;
    }

    public boolean didMoveLast() {
        return justMoved;
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        Stage stage = this.getStage();
        Vector2 begin = stage.stageToScreenCoordinates(new Vector2(45, myGame.VIRTUAL_HEIGHT - 45));
        Vector2 end = stage.stageToScreenCoordinates(new Vector2(myGame.VIRTUAL_WIDTH - 90, myGame.VIRTUAL_HEIGHT - 630));
        Rectangle scissor = new Rectangle();
        Rectangle clipBounds = new Rectangle(begin.x, begin.y, end.x, end.y);
        ScissorStack.calculateScissors(myGame.camera, 0, 0, myGame.VIRTUAL_WIDTH, myGame.VIRTUAL_HEIGHT, batch.getTransformMatrix(), clipBounds, scissor);
        batch.flush();
        ScissorStack.pushScissors(scissor);

        super.draw(batch, parentAlpha);
        batch.flush();
        ScissorStack.popScissors();
    }

    public void convertToSpecial() {
        int gemNumber = gemType.ordinal();
        SpecialType t = SpecialType.values()[gemNumber];
        TextureRegionDrawable draw = new TextureRegionDrawable(new TextureRegion(myGame.assets.get(t.getTexturePath(), Texture.class)));
        this.setDrawable(draw);
        isSpecial = true;
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

    public enum SpecialType {
        TYPE_BLUE("data/special_blue.png"),
        TYPE_GREEN("data/special_green.png"),
        TYPE_PURPLE("data/special_purple.png"),
        TYPE_RED("data/special_red.png"),
        TYPE_WHITE("data/special_white.png"),
        TYPE_YELLOW("data/special_yellow.png"),
        TYPE_NONE("");
        private String texturePath;

        SpecialType(String texturePath) {
            this.texturePath = texturePath;
        }

        public String getTexturePath() {
            return texturePath;
        }
    }
}
