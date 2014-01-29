package de.cosh.anothermanager.SwapGame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import de.cosh.anothermanager.AnotherManager;
import de.cosh.anothermanager.SwapGame.Board.BoardState;

/**
 * Created by cosh on 13.01.14.
 */
public class Gem extends Image {
	
	public enum MoveDirection {
		DIRECTION_HORIZONTAL,
		DIRECTION_VERTICAL,
		DIRECTION_NONE
	};

	private final float GEM_SPEED = 150f;
	private final float SWAP_SPEED = 0.20f;
	private final float ACCEL_FACTOR = 20f;
	
	private GemType gemType;
	private GemTypeSpecialHorizontal specialTypeHorizontal;
	private GemTypeSpecialVertical specialTypeVertical;
	private GemTypeSuperSpecial specialSuperSpecial;

	private boolean isMarkedForSpecialConversion;
	private boolean isMarkedForSuperSpecialConversion;
	private boolean convertHorizontal;
	private boolean isSpecialHorizontalGem;
	private boolean isSpecialVerticalGem;
	private boolean isSuperSpecialGem;
	private boolean checked;
    private boolean isDisabled;
	private boolean markedForRemoval;
	private MoveDirection moveDirection;

	private int cellX, cellY, destCellX, destCellY;
	private Cell[][] cells;
	private boolean fallOne;
	private float totalTranslated;
	private float speed;
	
	private BitmapFont bmf;

	public Gem(GemType g) {
		super(AnotherManager.assets.get(g.getTexturePath(), Texture.class));
		super.setWidth(80);
		super.setHeight(80);
		this.gemType = g;
		this.speed = 0;
		this.isMarkedForSpecialConversion = false;
		this.isMarkedForSuperSpecialConversion = false;
		this.markedForRemoval = false;
		this.moveDirection = MoveDirection.DIRECTION_NONE;
		this.isSuperSpecialGem = false;
		this.isSpecialHorizontalGem = false;
		this.checked = false;
        this.isDisabled = false;
        this.fallOne = false;
        this.totalTranslated = 0f;
		specialTypeHorizontal = GemTypeSpecialHorizontal.TYPE_NONE;
		specialTypeVertical = GemTypeSpecialVertical.TYPE_NONE;
		specialSuperSpecial = GemTypeSuperSpecial.TYPE_NONE;
		cells = AnotherManager.getInstance().gameScreen.getBoard().getCells();
		//bmf = new BitmapFont();
	}
	
	public boolean isFalling() {
		return fallOne;
	}
	
	public void setFalling(boolean b) {
		fallOne = b;
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		
		/*
		if( !(AnotherManager.getInstance().gameScreen.getBoard().getBoardState() == BoardState.STATE_CHECK) )
			return;
		*/
		
		// where am I
		// what is below me
		// nothig below me -> fall
		// easy, right?
		// no
		
		
		
		
		if( fallOne ) {
			speed += ACCEL_FACTOR * delta;
			translate(0, -speed);
			totalTranslated += speed;
			if( totalTranslated >= Board.CELL_SIZE ) {
				translate(0, totalTranslated-Board.CELL_SIZE);
				fallOne = false;
				totalTranslated = 0f;
				/*
				int cellPosX = Math.round((getX() - Board.CELL_PAD_X )/Board.CELL_SIZE);
				int cellPosY = Math.round((getY() - Board.CELL_PAD_Y )/Board.CELL_SIZE);
				*/
				
			}
			return;
		}
		if( cellY-1 < 0 ) // bottom gem never falls
			return;

		Gem gemBelow = cells[cellX][cellY-1].getGem();
		
		if( gemBelow.isTypeNone() ) {
			fallOne = true;
			destCellX = cellX;
			destCellY = cellY-1;

			Gem tempGem = cells[destCellX][destCellY].getGem();
			cells[cellX][cellY].setGem(tempGem);
			cells[destCellX][destCellY].setGem(this);
		} else speed = 0f;
		
		/*
		if( cellY-1 < 0 )
			return;
		
		int cellPosX = Math.round((getX() - Board.CELL_PAD_X )/Board.CELL_SIZE);
		int cellPosY = Math.round((getY() - Board.CELL_PAD_Y )/Board.CELL_SIZE);

		if( fallOne ) {
			speed += ACCEL_FACTOR * delta;
			translate(0, -speed);
			totalTranslated += speed;
			if( totalTranslated >= 80f ) {
				translate(0, totalTranslated-80f);
				fallOne = false;
				totalTranslated = 0f;
			}
			return;
		}
		Gem gemBelow = cells[cellX][cellY-1].getGem();
		
		if( gemBelow.isTypeNone() ) {
			fallOne = true;
			cells[cellX][cellY-1].setGem(this);
			cells[cellX][cellY+1].setGem(gemBelow);
		} else speed = 0;
		
		/*
		if( fallOne ) {
			speed += ACCEL_FACTOR * delta; 
			totalTranslated += speed;
			if( totalTranslated >= 80f ) {
				translate(0, totalTranslated-80f);
				fallOne = false;
				totalTranslated = 0f;
			}
			translate(0, -speed);
			return;
		}

		int cellPosX = Math.round((getX() - Board.CELL_PAD_X )/Board.CELL_SIZE);
		int cellPosY = Math.round((getY() - Board.CELL_PAD_Y )/Board.CELL_SIZE);
		
		if( cellPosX >= 0 && cellPosX < Board.MAX_SIZE_X && cellPosY >= 0 && cellPosY < Board.MAX_SIZE_Y )
			setCell(cellPosX, cellPosY);
		
		if( cellPosY > Board.MAX_SIZE_Y ) {
			fallOne = true;
			return;
		}
			
		
		if( cellY-1 < 0 )
			return;
		
		if( cells[cellX][cellY-1].getGem().isTypeNone() ) {
			fallOne = true;
			Gem temp = cells[cellX][cellY-1].getGem();
			cells[cellX][cellY-1].setGem(this);
			cells[cellX][cellY+1].setGem(temp);
		} else {
			speed = 0;
		}
		
		*/
		
	}

    public void disable() {
        isDisabled = true;
    }

    public boolean isDisabled() {
        return isDisabled;
    }

	public void convertToSpecialGem() {
		if( isSuperSpecialGem )
			return;
		if (convertHorizontal) {
			specialTypeHorizontal = GemTypeSpecialHorizontal.values()[gemType.ordinal()];
			setDrawable(new TextureRegionDrawable(new TextureRegion(AnotherManager.assets.get(specialTypeHorizontal.getTexturePath(), Texture.class))));
			isMarkedForSpecialConversion = false;
			isSpecialHorizontalGem = true;
		} else {
			specialTypeVertical = GemTypeSpecialVertical.values()[gemType.ordinal()];
			setDrawable(new TextureRegionDrawable(new TextureRegion(AnotherManager.assets.get(specialTypeVertical.getTexturePath(), Texture.class))));
			isMarkedForSpecialConversion = false;
			isSpecialVerticalGem = true;
		}
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
//		Stage stage = getStage();
//		AnotherManager myGame = AnotherManager.getInstance();
//		
//        final Vector2 begin = stage.stageToScreenCoordinates(new Vector2(0, AnotherManager.VIRTUAL_HEIGHT - 247));
//        final Vector2 end = stage.stageToScreenCoordinates(new Vector2(AnotherManager.VIRTUAL_WIDTH ,
//                AnotherManager.VIRTUAL_HEIGHT - 720));
//        final Rectangle scissor = new Rectangle();
//        final Rectangle clipBounds = new Rectangle(begin.x, begin.y, end.x, end.y);
//        ScissorStack.calculateScissors(myGame.camera, 0, 0, AnotherManager.VIRTUAL_WIDTH, AnotherManager.VIRTUAL_HEIGHT,
//                batch.getTransformMatrix(), clipBounds, scissor);
//        batch.flush();
//        ScissorStack.pushScissors(scissor);
//        super.draw(batch, parentAlpha);
//        batch.flush();
//        ScissorStack.popScissors();
	}

	public boolean equals(final Gem b) {
        if( isDisabled || b.isDisabled() )
            return false;
        if( isTypeNone() || b.isTypeNone() )
            return false;
		if( isSuperSpecialGem() )
			return true;
		if( b.isSuperSpecialGem() )
			return true;
		if( gemType == b.gemType )
			return true;
		return false;
	}

	public void fallBy(final int x, final int y) {
		final float fallGoal = y * Board.CELL_SIZE;
		addAction(Actions.after(
				Actions.sequence(
				new AccelAction(fallGoal, GEM_SPEED),
				Actions.moveBy(0, 4f, 0.04f),
				Actions.moveBy(0, -4f, 0.04f))));
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
				specialSuperSpecial == GemTypeSuperSpecial.TYPE_NONE &&
				specialTypeHorizontal == GemTypeSpecialHorizontal.TYPE_NONE &&
				specialTypeVertical == GemTypeSpecialVertical.TYPE_NONE);
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
		specialSuperSpecial = GemTypeSuperSpecial.TYPE_NONE;
		specialTypeHorizontal = GemTypeSpecialHorizontal.TYPE_NONE;
		specialTypeVertical = GemTypeSpecialVertical.TYPE_NONE;

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
		specialSuperSpecial = GemTypeSuperSpecial.TYPE_SPECIAL_5;
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

	public void setCell(int cellX, int cellY) {
		this.cellX = cellX;
		this.cellY = cellY;
	}
}
