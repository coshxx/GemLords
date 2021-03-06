package de.cosh.gemlords.SwapGame;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import de.cosh.gemlords.GemLord;

/**
 * Created by cosh on 13.01.14.
 */
public class Gem extends Image {

	public enum MoveDirection {
		DIRECTION_HORIZONTAL, DIRECTION_VERTICAL, DIRECTION_NONE
	};

	private final float SWAP_SPEED = 0.20f;
	private final float ACCEL_FACTOR = 2000f;

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

	private int cellX, cellY;
	private Cell[][] cells;
	private boolean isFalling;
	private float speed, totalTranslated;

	public Gem(GemType g) {
        TextureAtlas atlas = GemLord.assets.get("data/textures/pack.atlas", TextureAtlas.class);
        this.setDrawable(new TextureRegionDrawable(new TextureRegion(atlas.findRegion(g.getTexturePath()))));
		super.setWidth(80);
		super.setHeight(80);
		this.gemType = g;
		this.isMarkedForSpecialConversion = false;
		this.isMarkedForSuperSpecialConversion = false;
		this.markedForRemoval = false;
		this.moveDirection = MoveDirection.DIRECTION_NONE;
		this.isSuperSpecialGem = false;
		this.isSpecialHorizontalGem = false;
		this.checked = false;
		this.isDisabled = false;
		this.isFalling = false;
		this.speed = 0f;
		this.totalTranslated = 0f;
		this.cells = GemLord.getInstance().gameScreen.getBoard().getCells();
		specialTypeHorizontal = GemTypeSpecialHorizontal.TYPE_NONE;
		specialTypeVertical = GemTypeSpecialVertical.TYPE_NONE;
		specialSuperSpecial = GemTypeSuperSpecial.TYPE_NONE;
	}

	public void update(float delta) {
		if (cellY >= Board.MAX_SIZE_Y) {
			fall(delta);
			return;
		}

		if (cellY - 1 < 0)
			return;

		if (isFalling) {
			fall(delta);
		} else if (cells[cellX][cellY - 1].isEmpty()) {
			cells[cellX][cellY].setEmpty(true);
			setFalling(true);
			setMoving(MoveDirection.DIRECTION_VERTICAL);
			GemLord.getInstance().gameScreen.getBoard().addToUncelledGems(this);
			fall(delta);
		} else {
			setFalling(false);
			cells[cellX][cellY].setGem(this);
			cells[cellX][cellY].setEmpty(false);
			speed = 0;
		}
	}

	boolean fall(float delta) {
		speed += ACCEL_FACTOR * delta;
        float toTranslate = speed * delta;
        translate(0, -toTranslate);
		totalTranslated += toTranslate;
		if (totalTranslated >= Board.CELL_SIZE) {
			translate(0, totalTranslated - Board.CELL_SIZE);
			totalTranslated = 0;
			cellY--;
			if (cellY < Board.MAX_SIZE_Y) {
				cells[cellX][cellY].setEmpty(false);
				cells[cellX][cellY].setGem(this);
				cells[cellX][cellY].setPosition(cells[cellX][cellY].getX(), cells[cellX][cellY].getY());
			}
			isFalling = false;
			return true;
		}
		return false;
	}

	public boolean isFalling() {
		return isFalling;
	}

	void setFalling(boolean b) {
		isFalling = b;
	}

	public void convertToSpecialGem() {
        TextureAtlas atlas = GemLord.assets.get("data/textures/pack.atlas", TextureAtlas.class);
		if (isSuperSpecialGem)
			return;
		if (convertHorizontal) {
			specialTypeHorizontal = GemTypeSpecialHorizontal.values()[gemType.ordinal()];
			setDrawable(new TextureRegionDrawable(new TextureRegion(atlas.findRegion(specialTypeHorizontal.getTexturePath()))));
			isMarkedForSpecialConversion = false;
			isSpecialHorizontalGem = true;
		} else {
			specialTypeVertical = GemTypeSpecialVertical.values()[gemType.ordinal()];
			setDrawable(new TextureRegionDrawable(new TextureRegion(atlas.findRegion(specialTypeVertical.getTexturePath()))));
			isMarkedForSpecialConversion = false;
			isSpecialVerticalGem = true;
		}
	}

	public boolean equals(final Gem b) {
		if (isTypeNone() || b.isTypeNone())
			return false;
		if (isSuperSpecialGem())
			return true;
		if (b.isSuperSpecialGem())
			return true;
		if (gemType == b.gemType)
			return true;
		return false;
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
		return (gemType == GemType.TYPE_NONE && specialSuperSpecial == GemTypeSuperSpecial.TYPE_NONE
				&& specialTypeHorizontal == GemTypeSpecialHorizontal.TYPE_NONE && specialTypeVertical == GemTypeSpecialVertical.TYPE_NONE);
	}

	public void markForSpecialConversion() {
		if (isMarkedForSuperSpecialConversion || isMarkedForSpecialConversion || isSuperSpecialGem || isSpecialHorizontalGem()
				|| isSpecialVerticalGem)
			return;
		isMarkedForSpecialConversion = true;
		if (moveDirection == MoveDirection.DIRECTION_HORIZONTAL)
			convertHorizontal = true;
		else
			convertHorizontal = false;
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
        TextureAtlas atlas = GemLord.assets.get("data/textures/pack.atlas", TextureAtlas.class);
		setDrawable(new TextureRegionDrawable(new TextureRegion(atlas.findRegion(specialSuperSpecial.getTexturePath()))));
		isMarkedForSuperSpecialConversion = false;
		isSuperSpecialGem = true;
	}

	public boolean isSuperSpecialGem() {
		return isSuperSpecialGem;
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

	public boolean foundACell() {
		if (cellY < Board.MAX_SIZE_Y && !isFalling)
			return true;
		return false;
	}

    @Override
    public String toString() {
        return super.toString() + gemType.toString();
    }
}
