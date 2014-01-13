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
    private boolean markedForRemoval;
    private boolean replaceWithSpecial;

    public Cell(Texture t) {
        super(t);
        markedForRemoval = false;
        replaceWithSpecial = false;
    }

    public void setOccupant( Gem g ) {
        currently_occupied_by = g;
    }

    public Gem getOccupant() {
        return currently_occupied_by;
    }

    public void markForRemoval() {
        markedForRemoval = true;
    }

    public void markForSepcial() {
        replaceWithSpecial = true;
    }

    public boolean isMarkedForRemoval() {
        return markedForRemoval;
    }
    public boolean isMarkedForSpecial() { return replaceWithSpecial; }

    public void removeOccupant() {
        currently_occupied_by = null;
    }

    public void unmarkForRemoval() {
        markedForRemoval = false;
    }

    public void unmarkForSpecial() {
        replaceWithSpecial = false;
    }
}
