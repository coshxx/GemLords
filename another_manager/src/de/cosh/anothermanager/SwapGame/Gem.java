package de.cosh.anothermanager.SwapGame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import de.cosh.anothermanager.AnotherManager;

/**
 * Created by cosh on 13.01.14.
 */
public class Gem extends Image {
    public enum GemType {
        TYPE_BLUE("data/ball_blue.png"),
        TYPE_GREEN("data/ball_green.png"),
        TYPE_PURPLE("data/ball_purple.png"),
        TYPE_RED("data/ball_red.png"),
        TYPE_WHITE("data/ball_white.png"),
        TYPE_YELLOW("data/ball_yellow.png"),
        TYPE_NONE("");

        private String texturePath;

        GemType(final String texturePath) {
            this.texturePath = texturePath;
        }

        public String getTexturePath() {
            return texturePath;
        }
    }

    public enum SpecialTypeHorizontal {
        TYPE_BLUE("data/special_blueh.png"),
        TYPE_GREEN("data/special_greenh.png"),
        TYPE_PURPLE("data/special_purpleh.png"),
        TYPE_RED("data/special_redh.png"),
        TYPE_WHITE("data/special_whiteh.png"),
        TYPE_YELLOW("data/special_yellowh.png"),
        TYPE_NONE("");
        private String texturePath;

        SpecialTypeHorizontal(final String texturePath) {
            this.texturePath = texturePath;
        }

        public String getTexturePath() {
            return texturePath;
        }
    }

    public enum SpecialTypeVertical {
        TYPE_BLUE("data/special_bluev.png"),
        TYPE_GREEN("data/special_greenv.png"),
        TYPE_PURPLE("data/special_purplev.png"),
        TYPE_RED("data/special_redv.png"),
        TYPE_WHITE("data/special_whitev.png"),
        TYPE_YELLOW("data/special_yellowv.png"),
        TYPE_NONE("");
        private String texturePath;

        SpecialTypeVertical(final String texturePath) {
            this.texturePath = texturePath;
        }

        public String getTexturePath() {
            return texturePath;
        }
    }

    public enum MoveDirection {
        DIRECTION_HORIZONTAL,
        DIRECTION_VERTICAL,
        DIRECTION_NONE
    }

    ;

    private final float GEM_SPEED = 150f;
    private GemType gemType;

    private boolean isMarkedForSpecialConversion;
    private boolean convertHorizontal;
    private boolean isSpecialHorizontalGem;
    private boolean isSpecialVerticalGem;

    private MoveDirection moveDirection;

    private boolean markedForRemoval;

    private final AnotherManager myGame;

    private final float SWAP_SPEED = 0.20f;

    public Gem(final AnotherManager myGame, final GemType g) {
        super(myGame.assets.get(g.getTexturePath(), Texture.class));
        this.gemType = g;
        this.myGame = myGame;
        this.isMarkedForSpecialConversion = false;
        this.markedForRemoval = false;
        this.moveDirection = MoveDirection.DIRECTION_NONE;

        this.isSpecialHorizontalGem = false;
    }

    public void convertToSpecialGem() {
        if (convertHorizontal) {
            final SpecialTypeHorizontal t = SpecialTypeHorizontal.values()[gemType.ordinal()];
            setDrawable(new TextureRegionDrawable(new TextureRegion(myGame.assets.get(t.getTexturePath(), Texture.class))));
            isMarkedForSpecialConversion = false;
            isSpecialHorizontalGem = true;
        } else {
            final SpecialTypeVertical t = SpecialTypeVertical.values()[gemType.ordinal()];
            setDrawable(new TextureRegionDrawable(new TextureRegion(myGame.assets.get(t.getTexturePath(), Texture.class))));
            isMarkedForSpecialConversion = false;
            isSpecialVerticalGem = true;
        }
    }

    @Override
    public void draw(final SpriteBatch batch, final float parentAlpha) {
        final Stage stage = this.getStage();
        final Vector2 begin = stage.stageToScreenCoordinates(new Vector2(45, myGame.VIRTUAL_HEIGHT - 145));
        final Vector2 end = stage.stageToScreenCoordinates(new Vector2(myGame.VIRTUAL_WIDTH - 90,
                myGame.VIRTUAL_HEIGHT - 630));
        final Rectangle scissor = new Rectangle();
        final Rectangle clipBounds = new Rectangle(begin.x, begin.y, end.x, end.y);
        ScissorStack.calculateScissors(myGame.camera, 0, 0, myGame.VIRTUAL_WIDTH, myGame.VIRTUAL_HEIGHT,
                batch.getTransformMatrix(), clipBounds, scissor);
        batch.flush();
        ScissorStack.pushScissors(scissor);

        super.draw(batch, parentAlpha);
        batch.flush();
        ScissorStack.popScissors();
    }

    public boolean equals(final Gem b) {
        return gemType == b.gemType;
    }

    public void fallBy(final int x, final int y) {
        final float fallTo = getY() + y * Board.CELL_SIZE;
        addAction(new AccelAction(fallTo, GEM_SPEED));
    }

    public boolean isMarkedForRemoval() {
        return markedForRemoval;
    }

    public boolean isMarkedForSpecialConversion() {
        return isMarkedForSpecialConversion;
    }

    public boolean isMoving() {
        return moveDirection != MoveDirection.DIRECTION_NONE;
    }

    public boolean isSpecialHorizontalGem() {
        return isSpecialHorizontalGem;
    }

    public boolean isSpecialVerticalGem() {
        return isSpecialVerticalGem;
    }

    public boolean isTypeNone() {
        return gemType == GemType.TYPE_NONE;
    }

    public void markForSpecialConversion() {
        isMarkedForSpecialConversion = true;
        if( moveDirection == MoveDirection.DIRECTION_HORIZONTAL )
            convertHorizontal = true;
        else convertHorizontal = false;
    }

    public void markGemForRemoval() {
        markedForRemoval = true;
    }

    public void moveBy(final int x, final int y) {
        addAction(Actions.moveBy(x * getWidth(), y * getHeight(), SWAP_SPEED));
    }

    public void setMoving(MoveDirection dir) {
        moveDirection = dir;
    }

    public void setToNone() {
        gemType = GemType.TYPE_NONE;

    }

    public void unmarkRemoval() {
        markedForRemoval = false;
    }
}
