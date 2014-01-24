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
        TYPE_BLUE("data/textures/ball_blue.png"),
        TYPE_GREEN("data/textures/ball_green.png"),
        TYPE_PURPLE("data/textures/ball_purple.png"),
        TYPE_RED("data/textures/ball_red.png"),
        TYPE_WHITE("data/textures/ball_white.png"),
        TYPE_YELLOW("data/textures/ball_yellow.png"),
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
        TYPE_BLUE("data/textures/special_blueh.png"),
        TYPE_GREEN("data/textures/special_greenh.png"),
        TYPE_PURPLE("data/textures/special_purpleh.png"),
        TYPE_RED("data/textures/special_redh.png"),
        TYPE_WHITE("data/textures/special_whiteh.png"),
        TYPE_YELLOW("data/textures/special_yellowh.png"),
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
        TYPE_BLUE("data/textures/special_bluev.png"),
        TYPE_GREEN("data/textures/special_greenv.png"),
        TYPE_PURPLE("data/textures/special_purplev.png"),
        TYPE_RED("data/textures/special_redv.png"),
        TYPE_WHITE("data/textures/special_whitev.png"),
        TYPE_YELLOW("data/textures/special_yellowv.png"),
        TYPE_NONE("");
        private String texturePath;

        SpecialTypeVertical(final String texturePath) {
            this.texturePath = texturePath;
        }

        public String getTexturePath() {
            return texturePath;
        }
    }
    
    public enum SpecialSuperSpecial {
    	TYPE_SPECIAL_5("data/textures/special_5.png"),
    	TYPE_NONE("");
    	
    	private String texturePath;
    	
    	SpecialSuperSpecial(final String texturePath) {
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
    private SpecialTypeHorizontal specialTypeHorizontal;
    private SpecialTypeVertical specialTypeVertical;
    private SpecialSuperSpecial specialSuperSpecial;

    private boolean isMarkedForSpecialConversion;
    private boolean isMarkedForSuperSpecialConversion;
    private boolean convertHorizontal;
    private boolean isSpecialHorizontalGem;
    private boolean isSpecialVerticalGem;
    private boolean isSuperSpecialGem;
    private boolean checked;
    private MoveDirection moveDirection;

    private boolean markedForRemoval;

    private final AnotherManager myGame;

    private final float SWAP_SPEED = 0.20f;
	

    public Gem(final AnotherManager myGame, final GemType g) {
        super(AnotherManager.assets.get(g.getTexturePath(), Texture.class));
        this.gemType = g;
        this.myGame = myGame;
        this.isMarkedForSpecialConversion = false;
        this.isMarkedForSuperSpecialConversion = false;
        this.markedForRemoval = false;
        this.moveDirection = MoveDirection.DIRECTION_NONE;
        this.isSuperSpecialGem = false;
        this.isSpecialHorizontalGem = false;
        this.checked = false;
        specialTypeHorizontal = SpecialTypeHorizontal.TYPE_NONE;
        specialTypeVertical = SpecialTypeVertical.TYPE_NONE;
        specialSuperSpecial = SpecialSuperSpecial.TYPE_NONE;
    }

    public void convertToSpecialGem() {
    	if( isSuperSpecialGem )
    		return;
        if (convertHorizontal) {
            specialTypeHorizontal = SpecialTypeHorizontal.values()[gemType.ordinal()];
            setDrawable(new TextureRegionDrawable(new TextureRegion(AnotherManager.assets.get(specialTypeHorizontal.getTexturePath(), Texture.class))));
            isMarkedForSpecialConversion = false;
            isSpecialHorizontalGem = true;
        } else {
            specialTypeVertical = SpecialTypeVertical.values()[gemType.ordinal()];
            setDrawable(new TextureRegionDrawable(new TextureRegion(AnotherManager.assets.get(specialTypeVertical.getTexturePath(), Texture.class))));
            isMarkedForSpecialConversion = false;
            isSpecialVerticalGem = true;
        }
    }

    @Override
    public void draw(final SpriteBatch batch, final float parentAlpha) {
        final Stage stage = this.getStage();
        final Vector2 begin = stage.stageToScreenCoordinates(new Vector2(0, AnotherManager.VIRTUAL_HEIGHT - 250));
        final Vector2 end = stage.stageToScreenCoordinates(new Vector2(AnotherManager.VIRTUAL_WIDTH ,
                AnotherManager.VIRTUAL_HEIGHT - 630));
        final Rectangle scissor = new Rectangle();
        final Rectangle clipBounds = new Rectangle(begin.x, begin.y, end.x, end.y);
        ScissorStack.calculateScissors(myGame.camera, 0, 0, AnotherManager.VIRTUAL_WIDTH, AnotherManager.VIRTUAL_HEIGHT,
                batch.getTransformMatrix(), clipBounds, scissor);
        batch.flush();
        ScissorStack.pushScissors(scissor);

        super.draw(batch, parentAlpha);
        batch.flush();
        ScissorStack.popScissors();
    }

    public boolean equals(final Gem b) {
    	if( isSuperSpecialGem() )
    		return true;
    	if( b.isSuperSpecialGem() )
    		return true;
    	if( gemType == b.gemType )
    		return true;
    	return false;
    }

    public void fallBy(final int x, final int y) {
        final float fallTo = getY() + y * Board.CELL_SIZE;
        addAction(Actions.sequence(
        		new AccelAction(fallTo, GEM_SPEED),
        		Actions.moveBy(0, 4f, 0.04f),
        		Actions.moveBy(0, -4f, 0.04f)));
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
        return (gemType == GemType.TYPE_NONE &&
        		specialSuperSpecial == SpecialSuperSpecial.TYPE_NONE &&
        		specialTypeHorizontal == SpecialTypeHorizontal.TYPE_NONE &&
        		specialTypeVertical == SpecialTypeVertical.TYPE_NONE);
    }

    public void markForSpecialConversion() {
    	if( isMarkedForSuperSpecialConversion || isMarkedForSpecialConversion || isSuperSpecialGem || isSpecialHorizontalGem() || isSpecialVerticalGem )
    		return;
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
        specialSuperSpecial = SpecialSuperSpecial.TYPE_NONE;
        specialTypeHorizontal = SpecialTypeHorizontal.TYPE_NONE;
        specialTypeVertical = SpecialTypeVertical.TYPE_NONE;

    }

    public void unmarkRemoval() {
        markedForRemoval = false;
    }

	public void markForSuperSpecial() {
		isMarkedForSuperSpecialConversion = true;
	}

	public boolean isMarkedForSuperSpecialConversion() {
		return isMarkedForSuperSpecialConversion;
	}

	public void convertToSuperSpecial() {
		specialSuperSpecial = SpecialSuperSpecial.TYPE_SPECIAL_5;
		setDrawable(new TextureRegionDrawable(new TextureRegion(AnotherManager.assets.get(specialSuperSpecial.getTexturePath(), Texture.class))));
		isMarkedForSuperSpecialConversion = false;
		isSuperSpecialGem = true;
	}

	public boolean isSuperSpecialGem() {
		return isSuperSpecialGem;
	}

	public void checked() {
		checked = true;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean b) {
		checked = b;
	}

	public GemType getGemType() {
		return gemType;
	}
}
