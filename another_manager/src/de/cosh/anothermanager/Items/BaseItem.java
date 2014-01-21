package de.cosh.anothermanager.Items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import de.cosh.anothermanager.AnotherManager;

/**
 * Created by cosh on 20.01.14.
 */
public abstract class BaseItem extends Image implements UseItem {
    private transient String itemName;
    private transient String itemText;
    private transient BitmapFont bmf;
    private transient Image itemBorder;
    private transient boolean selected;
    private transient boolean drawText;
    private transient boolean addedToActionBar;
    private transient int actionBarSlot;

    protected int itemNumber = -1;

    public ItemSlotType getItemSlotType() {
        return itemSlotType;
    }

    @Override
    public void onUse() {

    }

    @Override
    public void drawCooldown(SpriteBatch batch, float parentAlpha) {

    }

    @Override
    public void turn() {

    }

    public void removeFromActionBar() {
        addedToActionBar = false;
        AnotherManager.getInstance().player.getActionBar().removeFromBar(this);
    }

    public enum ItemSlotType {
        ARMOR,
        POTION,
        ACTIVE
    }

    private ItemSlotType itemSlotType;

    public BaseItem(Texture t) {
        super(t);
        itemBorder = new Image(AnotherManager.assets.get("data/textures/item_border.png", Texture.class));
        bmf = new BitmapFont();
        selected = false;
        drawText = true;
        addedToActionBar = false;
        actionBarSlot = -1;
    }

    public void setItemSlotType(ItemSlotType type) {
        itemSlotType = type;
    }

    public void setItemName(String name) {
        this.itemName = name;
    }

    public void setItemText(String text) {
        this.itemText = text;
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        if (selected)
            itemBorder.setColor(1f, 0f, 0f, parentAlpha);
        else itemBorder.setColor(1f, 1f, 1f, parentAlpha);
        itemBorder.setPosition(getX(), getY());
        itemBorder.draw(batch, parentAlpha);
        super.draw(batch, parentAlpha);
        float imgCenterX = getX() + (getWidth() / 2);
        if (drawText) {
            bmf.setColor(1f, 1f, 1f, parentAlpha);
            BitmapFont.TextBounds bounds = new BitmapFont.TextBounds();
            bmf.setColor(0f, 1f, 0f, parentAlpha);
            bounds = bmf.getBounds(itemName);
            bmf.draw(batch, itemName, imgCenterX - (bounds.width / 2), getY() - 5);
            bounds = bmf.getBounds(itemText);
            //bmf.draw(batch, itemText, imgCenterX-(bounds.width/2), getY()-25 );
            bmf.setColor(1f, 1f, 1f, parentAlpha);
            bmf.drawMultiLine(batch, itemText, imgCenterX - 50, getY() - 25, 100, BitmapFont.HAlignment.CENTER);
        }
    }

    public void selected() {
        selected = true;
    }

    public void setDrawText(boolean value ) {
        drawText = value;
    }

    public boolean isSelected() {
        return selected;
    }

    public void unselect() {
        selected = false;
    }

    public void addedToActionBar(boolean b) {
        addedToActionBar = b;
    }

    public void setActionBarSlot(int actionBarSlot) {
        this.actionBarSlot = actionBarSlot;
    }

    public int getActionBarSlot() {
        return actionBarSlot;
    }

    public boolean isAddedToActionBar() {
        return addedToActionBar;
    }
}
