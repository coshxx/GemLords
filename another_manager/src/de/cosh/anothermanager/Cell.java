package de.cosh.anothermanager;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by cosh on 11.12.13.
 */
public class Cell  {
    private Gem currently_occupied_by;
    private Vector2 position;

    public void setOccupant( Gem g ) {
        currently_occupied_by = g;
    }

}
