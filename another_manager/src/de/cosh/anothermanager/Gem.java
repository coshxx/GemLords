package de.cosh.anothermanager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;

/**
 * Created by cosh on 11.12.13.
 */
public class Gem extends Image {

    public void setToNoGem() {
        gemType = GemType.TYPE_NONE;
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

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        /*
        if( this.getY() < 650 )
            super.draw(batch, parentAlpha);
            */
        Stage stage = this.getStage();
        Vector2 begin = stage.stageToScreenCoordinates(new Vector2(45, myGame.VIRTUAL_HEIGHT-45));
        Vector2 end = stage.stageToScreenCoordinates(new Vector2(675, myGame.VIRTUAL_HEIGHT-630));
        Rectangle scissor = new Rectangle();
        Rectangle clipBounds = new Rectangle(begin.x, begin.y, end.x, end.y);
        ScissorStack.calculateScissors(myGame.camera, 0, 0, myGame.VIRTUAL_WIDTH, myGame.VIRTUAL_HEIGHT, batch.getTransformMatrix(), clipBounds, scissor);
        ScissorStack.pushScissors(scissor);

        super.draw(batch, parentAlpha);
        batch.flush();

        ScissorStack.popScissors();
    }

}
