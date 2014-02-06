package de.cosh.anothermanager.SwapGame;

import com.badlogic.gdx.math.GridPoint2;

/**
 * Created by cosh on 06.02.14.
 */
public class SwapCommand implements Comparable<SwapCommand> {
    public GridPoint2 swapStart;
    public int x, y;
    public boolean commandFound;
    public int score;

    @Override
    public int compareTo(SwapCommand o) {
        if( score > o.score )
            return 1;
        if( score == o.score )
            return 0;

        return -1;
    }
}
