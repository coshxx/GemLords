package de.cosh.anothermanager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import de.cosh.anothermanager.GemOld.GemType;

import java.util.Random;

/**
 * Created by cosh on 27.12.13.
 */
public class BoardControllerOld {

    private CellOld[][] currentBoard;
    private int PADDING_BOT, PADDING_LEFT, CELL_SIZE, MAX_DIFFERENT_GEMS, MAX_SIZE_X, MAX_SIZE_Y;
    private AnotherManager myGame;
    private float GEM_SPEED = 0.15f;
    private Random r;

    public void init(int PADDING_BOT, int PADDING_LEFT, int CELL_SIZE, int MAX_DIFFERENT_GEMS, int MAX_SIZE_X, int MAX_SIZE_Y, AnotherManager myGame) {
        this.PADDING_BOT = PADDING_BOT;
        this.PADDING_LEFT = PADDING_LEFT;
        this.CELL_SIZE = CELL_SIZE;
        this.MAX_DIFFERENT_GEMS = MAX_DIFFERENT_GEMS;
        this.MAX_SIZE_X = MAX_SIZE_X;
        this.MAX_SIZE_Y = MAX_SIZE_Y;
        this.myGame = myGame;
        r = new Random();
    }

    public CellOld[][] fillBoard(Group backGround, Group foreGround) {
        currentBoard = new CellOld[MAX_SIZE_X][MAX_SIZE_Y];

        for (int x = 0; x < MAX_SIZE_X; x++) {
            for (int y = 0; y < MAX_SIZE_Y; y++) {
                currentBoard[x][y] = new CellOld(myGame.assets.get("data/cell_back.png", Texture.class));
                currentBoard[x][y].setPosition(PADDING_LEFT + (x * CELL_SIZE), PADDING_BOT + (y * CELL_SIZE));
                currentBoard[x][y].setColor(1f, 1f, 1f, 0.33f);
                backGround.addActor(currentBoard[x][y]);

                currentBoard[x][y].setOccupant(new GemOld(myGame, GemType.values()[r.nextInt(MAX_DIFFERENT_GEMS)]));
                currentBoard[x][y].getOccupant().setPosition(PADDING_LEFT + (x * CELL_SIZE), PADDING_BOT + (y * CELL_SIZE));
                foreGround.addActor(currentBoard[x][y].getOccupant());
            }
        }
        return currentBoard;
    }

    public void swapCellTo(GridPoint2 cell, int x, int y) {

        GridPoint2 start = cell;
        GridPoint2 end = new GridPoint2(start.x + x, start.y + y);

        GemOld startOccupant = currentBoard[start.x][start.y].getOccupant(), endOccupant = currentBoard[end.x][end.y].getOccupant();

        if (startOccupant.getGemType() == GemType.TYPE_NONE || endOccupant.getGemType() == GemType.TYPE_NONE)
            return;

        currentBoard[start.x][start.y].getOccupant().addAction(Actions.moveBy(CELL_SIZE * x, CELL_SIZE * y, GEM_SPEED));
        currentBoard[start.x][start.y].getOccupant().setMoved(true);
        currentBoard[end.x][end.y].getOccupant().addAction(Actions.moveBy(-(CELL_SIZE * x), -(CELL_SIZE * y), GEM_SPEED));
        currentBoard[end.x][end.y].getOccupant().setMoved(true);
        GemOld oldOccupant = startOccupant;
        currentBoard[start.x][start.y].setOccupant(currentBoard[end.x][end.y].getOccupant());
        currentBoard[end.x][end.y].setOccupant(oldOccupant);
    }

    public MatchResult playFadingAnimationOnMarkedGems(Group foreGround) {
        MatchResult result = new MatchResult();
        for (int x = 0; x < MAX_SIZE_X; x++) {
            for (int y = 0; y < MAX_SIZE_Y; y++) {
                if (currentBoard[x][y].isMarkedForRemoval() && !currentBoard[x][y].isMarkedForSpecial()) {
                    result.howMany++;
                    if( currentBoard[x][y].getOccupant().isSpecial() )
                        result.specialExplo = true;
                    currentBoard[x][y].getOccupant().addAction(Actions.parallel(Actions.scaleBy(-1f, -1f, 0.15f), Actions.moveBy(35f, 35f, 0.15f)));
                }
            }
        }
        return result;
    }

    public boolean applyFallingActionToGems() {
        boolean neededToMoveOne = false;
        for (int x = 0; x < MAX_SIZE_X; x++) {
            for (int y = 0; y < MAX_SIZE_Y; y++) {
                GemOld currentGemOld = currentBoard[x][y].getOccupant();

                if (currentGemOld.getGemType() == GemType.TYPE_NONE) {
                    for (int d = y + 1; d < MAX_SIZE_Y; d++) {
                        if (currentBoard[x][d].getOccupant().getGemType() != GemType.TYPE_NONE) {
                            neededToMoveOne = true;
                            GemOld moveGemOld = currentBoard[x][d].getOccupant();
                            moveGemOld.addAction(Actions.moveTo(PADDING_LEFT + (x * CELL_SIZE), PADDING_BOT + (y * CELL_SIZE), (GEM_SPEED * (d - y)), new Interpolation.ExpOut(0.2f, 1f)));
                            moveGemOld.setMoved(true);
                            currentBoard[x][y].setOccupant(currentBoard[x][d].getOccupant());
                            currentBoard[x][d].setOccupant(currentGemOld);
                            break;
                        }
                    }
                }
            }
        }
        return neededToMoveOne;
    }

    public void respawnMissingGems(Group foreGround) {
        for (int x = 0; x < MAX_SIZE_X; x++) {
            int counter = 0;
            for (int y = 0; y < MAX_SIZE_Y; y++) {
                GemOld currentGemOld = currentBoard[x][y].getOccupant();
                if (currentGemOld.getGemType() == GemType.TYPE_NONE) {
                    GemOld newGemOld = new GemOld(myGame, GemType.values()[r.nextInt(MAX_DIFFERENT_GEMS)]);
                    newGemOld.setPosition(PADDING_LEFT + (x * CELL_SIZE), PADDING_BOT + (((MAX_SIZE_Y) + counter) * CELL_SIZE));
                    counter++;
                    newGemOld.addAction(Actions.moveTo(PADDING_LEFT + (x * CELL_SIZE), PADDING_BOT + (y * CELL_SIZE), (GEM_SPEED * (((MAX_SIZE_Y - 1) + counter) - (y))), new Interpolation.ExpOut(0.2f, 1f)));
                    newGemOld.setMoved(true);
                    foreGround.addActor(newGemOld);
                    currentBoard[x][y].setOccupant(newGemOld);
                }
            }
        }
    }

    public void removeMarkedGems(Group foreGround) {
        for (int x = 0; x < MAX_SIZE_X; x++) {
            for (int y = 0; y < MAX_SIZE_Y; y++) {
                if( currentBoard[x][y].isMarkedForSpecial()) {
                    GemOld currentGemOld = currentBoard[x][y].getOccupant();
                    currentGemOld.convertToSpecial();
                    currentBoard[x][y].unmarkForRemoval();
                    currentBoard[x][y].unmarkForSpecial();

                }
                else if (currentBoard[x][y].isMarkedForRemoval()) {
                    foreGround.removeActor(currentBoard[x][y].getOccupant());
                    StarEffect e = new StarEffect(myGame);
                    e.spawnStars(currentBoard[x][y].getOccupant().getX(), currentBoard[x][y].getOccupant().getY(), foreGround);
                    currentBoard[x][y].getOccupant().setToNoGem();
                    currentBoard[x][y].unmarkForRemoval();
                }
            }
        }
        return;
    }
}
