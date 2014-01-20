package de.cosh.anothermanager.Items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import de.cosh.anothermanager.AnotherManager;

/**
 * Created by cosh on 20.01.14.
 */
public class BaseItem extends Image {
    private String itemName;
    private String itemText;
    private transient BitmapFont bmf;
    private transient Image itemBorder;

    public BaseItem(Texture t) {
        super(t);
        itemBorder = new Image(AnotherManager.assets.get("data/textures/item_border.png", Texture.class));
        bmf = new BitmapFont();
    }

    public void setItemName(String name) {
        this.itemName = name;
    }

    public void setItemText(String text) {
        this.itemText = text;
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        itemBorder.setPosition(getX(), getY());
        itemBorder.draw(batch, parentAlpha);
        super.draw(batch, parentAlpha);
        float imgCenterX = getX() + (getWidth()/2);
        bmf.setColor(1f, 1f, 1f, parentAlpha);
        BitmapFont.TextBounds bounds = new BitmapFont.TextBounds();
        bmf.setColor(0f, 1f, 0f, parentAlpha);
        bounds = bmf.getBounds(itemName);
        bmf.draw(batch, itemName, imgCenterX-(bounds.width/2), getY()-5 );
        bounds = bmf.getBounds(itemText);
        //bmf.draw(batch, itemText, imgCenterX-(bounds.width/2), getY()-25 );
        bmf.setColor(1f, 1f, 1f, parentAlpha);
        bmf.drawMultiLine(batch, itemText, imgCenterX-50, getY()-25, 100, BitmapFont.HAlignment.CENTER);
    }
}
