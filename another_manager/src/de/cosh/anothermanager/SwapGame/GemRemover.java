package de.cosh.anothermanager.SwapGame;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import de.cosh.anothermanager.AnotherManager;

/**
 * Created by cosh on 13.01.14.
 */
public class GemRemover {
    private Cell[][] cells;
    private final float FADE_TIME = 0.25f;

    public GemRemover(Cell[][] cells) {
        this.cells = cells;
    }

    public void fadeMarkedGems() {
        for( int x = 0; x < Board.MAX_SIZE_X; x++ ) {
            for( int y = 0; y < Board.MAX_SIZE_Y; y++ ) {
                Gem rem = cells[x][y].getGem();
                if( rem.isMarkedForRemoval() ) {
                    rem.addAction(Actions.parallel(
                            Actions.scaleTo(0.0f, 0.0f, FADE_TIME),
                            Actions.moveBy(rem.getWidth()/2, rem.getHeight()/2, FADE_TIME)
                    )
                    );
                }
            }
        }
    }

    public void removeFadedGems(AnotherManager myGame, Group foreGround) {
        for( int x = 0; x < Board.MAX_SIZE_X; x++ ) {
            for( int y = 0; y < Board.MAX_SIZE_Y; y++ ) {
                Gem rem = cells[x][y].getGem();
                if( rem.isMarkedForRemoval() ) {
                    StarEffect effect = new StarEffect(myGame);
                    effect.spawnStars(rem.getX(), rem.getY(), foreGround);
                    rem.remove();
                    rem.unmarkRemoval();
                    rem.setToNone();
                }
            }
        }
    }
}
