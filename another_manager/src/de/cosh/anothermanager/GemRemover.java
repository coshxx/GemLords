package de.cosh.anothermanager;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 * Created by cosh on 13.01.14.
 */
public class GemRemover {
    private Cell[][] cells;
    public GemRemover(Cell[][] cells) {
        this.cells = cells;
    }

    public void fadeMatchingGems() {
        for( int x = 0; x < Board.MAX_SIZE_X; x++ ) {
            for( int y = 0; y < Board.MAX_SIZE_Y; y++ ) {
                Gem rem = cells[x][y].getGem();
                if( rem.isMarkedForRemoval() ) {
                    rem.addAction(Actions.parallel(
                            Actions.scaleTo(0.0f, 0.0f, 1.0f),
                            Actions.moveBy(rem.getWidth()/2, rem.getHeight()/2, 1.0f)
                    )
                    );
                }
            }
        }
    }
}
