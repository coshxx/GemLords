package de.cosh.anothermanager.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import de.cosh.anothermanager.AnotherManager;

/**
 * Created by cosh on 20.01.14.
 */
public class ActionBar extends Actor {

    private final float ACTION_PAD_X = 40;
    private final float ACTION_SPACEING_X = 5;

    private Image[] defaultSlots;
    private Image[] defaultBorders;
    private Image[] actionSlots;
    private Image[] actionBorders;

    public ActionBar() {
        defaultSlots = new Image[2];
        defaultBorders = new Image[2];
        actionSlots = new Image[4];
        actionBorders = new Image[4];

        for( int i = 0; i < 2; i++ ) {
            defaultBorders[i] = new Image(AnotherManager.assets.get("data/textures/item_border.png", Texture.class));
            defaultBorders[i].setPosition(ACTION_PAD_X + ( i * defaultBorders[i].getWidth()) + (i * ACTION_SPACEING_X), 100);
        }

        for( int i = 0; i < 4; i++ ) {
            actionBorders[i] = new Image(AnotherManager.assets.get("data/textures/item_border.png", Texture.class));
            actionBorders[i].setPosition((ACTION_PAD_X + ( (i+4) * actionBorders[i].getWidth()))+ ((i+4) * ACTION_SPACEING_X), 100);
        }
    }

    public void draw(SpriteBatch batch, float parentAlpha) {
        for( Image i : actionBorders )
            i.draw(batch, parentAlpha);

        for( Image i : defaultBorders )
            i.draw(batch, parentAlpha);
    }
}
