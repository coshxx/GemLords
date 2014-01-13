package de.cosh.anothermanager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by cosh on 13.01.14.
 */
public class Cell extends Image{
    private AnotherManager myGame;
    private Gem occupant;

    public Cell(AnotherManager myGame) {
        super(myGame.assets.get("data/cell_back.png", Texture.class));
        this.myGame = myGame;
    }
    public void putGem(Gem gem) {
        occupant = gem;
        occupant.setPosition(getX(), getY());
    }

    public Gem getGem() {
        return occupant;
    }

    public void setGem(Gem gem) {
        occupant = gem;
    }
}
