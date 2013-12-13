package de.cosh.anothermanager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.awt.*;

/**
 * Created by cosh on 11.12.13.
 */
public class Cell extends Image {
    private Gem currently_occupied_by;
    private Point position;

    public Cell(Texture t) {
        super(t);
    }

    public void setOccupant( Gem g ) {
        currently_occupied_by = g;
    }

    public Gem getOccupant() {
        return currently_occupied_by;
    }
}
