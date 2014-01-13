package de.cosh.anothermanager.SwapGame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import de.cosh.anothermanager.AnotherManager;

/**
 * Created by cosh on 13.01.14.
 */
public class Gem extends Image {
    private GemType gemType;
    private boolean isSpecialGem;
    private boolean markedForRemoval;
    private AnotherManager myGame;
    private final float GEM_SPEED = 150f;
    private final float SWAP_SPEED = 0.25f;

    public Gem(AnotherManager myGame, GemType g) {
        super(myGame.assets.get(g.getTexturePath(), Texture.class));
        this.gemType = g;
        this.myGame = myGame;
        this.isSpecialGem = false;
        this.markedForRemoval = false;
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

    public void moveBy(int x, int y) {
        addAction(Actions.moveBy(x * getWidth(), y * getHeight(), SWAP_SPEED));
    }

    public boolean isMarkedForRemoval() {
        return markedForRemoval;
    }

    public boolean isTypeNone() {
        return gemType == GemType.TYPE_NONE;
    }

    public void unmarkRemoval() {
        markedForRemoval = false;
    }

    public void fallBy(int x, int y) {
        float fallTo = getY() + (y * Board.CELL_SIZE);
        addAction(new AccelAction(fallTo, GEM_SPEED));
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
