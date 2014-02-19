package de.cosh.anothermanager.Items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import de.cosh.anothermanager.Characters.Damage;
import de.cosh.anothermanager.GemLord;

/**
 * Created by cosh on 20.01.14.
 */
public abstract class BaseItem extends Image implements UseItem, Comparable<BaseItem> {
    public static int MAXIDS = 1337;
    protected BitmapFont bmf;
    protected int itemNumber = -1;

    private transient String itemName;
    private transient String itemText;
    private transient Image itemBorder;
    private transient boolean selected;
    private transient boolean drawText;
    private transient boolean addedToActionBar;
    private transient int actionBarSlot;
    private ItemSlotType itemSlotType;

    BaseItem(Texture t) {
        super(t);
        itemBorder = new Image(GemLord.assets.get("data/textures/item_border.png", Texture.class));
        selected = false;
        drawText = true;
        addedToActionBar = false;
        actionBarSlot = -1;
        Skin s = GemLord.assets.get("data/ui/uiskin.json", Skin.class);
        bmf = s.getFont("credit-font");
    }

    public static BaseItem getNewItemByID(int id) {
        switch (id) {
            case -1:
                return null;
            case 0:
                return new ItemApprenticeRobe();
            case 1:
                return new ItemMinorHealthPotion();
            case 2:
                return new ItemScholarRobe();
            case 3:
                return new ItemTotem();
            case 4:
                return new ItemPocketWatch();
            case 5:
                return new ItemTerribleShield();
            case 6:
                return new ItemDagger();
            case 7:
                return new ItemBow();
            case 8:
                return new ItemAmulet();
            case 9:
                return new ItemRing();
            case 10:
                return new ItemMageRobe();
            case 11:
                return new ItemBetterShield();
            case 12:
                return new ItemHuntingBow();
            default:
                return null;
        }
    }


    public ItemSlotType getItemSlotType() {
        return itemSlotType;
    }

    void setItemSlotType(ItemSlotType type) {
        itemSlotType = type;
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

    public int getCritChanceIncrease() {
        return 0;
    }

    public void removeFromActionBar() {
        addedToActionBar = false;
        GemLord.getInstance().player.getActionBar().removeFromBar(this);
    }

    public int getAdditionalDamage(Damage damage) {
        return 0;
    }

    void setItemName(String name) {
        this.itemName = name;
    }

    void setItemText(String text) {
        this.itemText = text;
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        if (selected)
            itemBorder.setColor(1f, 0f, 0f, parentAlpha * getColor().a);
        else itemBorder.setColor(1f, 1f, 1f, parentAlpha * getColor().a);
        GemLord.getInstance();
        BitmapFont.TextBounds bounds;
        itemBorder.setPosition(getX(), getY());
        itemBorder.draw(batch, parentAlpha);
        super.draw(batch, parentAlpha);
        float imgCenterX = getX() + (getWidth() / 2);
        if (drawText) {
            bmf.setScale(0.75f);
            if (itemSlotType == ItemSlotType.BOW_ACTIVE)
                bmf.setColor(0.4f, 0.4f, 1f, parentAlpha * getColor().a);
            else if (itemSlotType == ItemSlotType.POTION)
                bmf.setColor(1f, 1f, 0f, parentAlpha * getColor().a);
            else bmf.setColor(0f, 1f, 0f, parentAlpha * getColor().a);
            bounds = bmf.getBounds(itemName);
            bmf.draw(batch, itemName, imgCenterX - (bounds.width / 2), getY() - 5);
            bounds = bmf.getBounds(itemText);
            //bmf.draw(batch, itemText, imgCenterX-(bounds.width/2), getY()-25 );
            bmf.setColor(1f, 1f, 1f, parentAlpha * getColor().a);
            bmf.drawMultiLine(batch, itemText, imgCenterX - 50, getY() - 35, 100, BitmapFont.HAlignment.CENTER);
            //bmf.drawWrapped(batch, itemText, imgCenterX - 50, getY() - 25, 200, BitmapFont.HAlignment.CENTER);
        }
    }

    public void selected() {
        selected = true;
    }

    public void setDrawText(boolean value) {
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

    public int getActionBarSlot() {
        return actionBarSlot;
    }

    public void setActionBarSlot(int actionBarSlot) {
        this.actionBarSlot = actionBarSlot;
    }

    public boolean isAddedToActionBar() {
        return addedToActionBar;
    }

    public int tryToReduceDamage(int incomingDamage) {
        return incomingDamage;
    }

    @Override
    public int compareTo(BaseItem o) {
        if (itemNumber < o.itemNumber)
            return -1;
        if (itemNumber > o.itemNumber)
            return 1;
        return 0;
    }

    public int getID() {
        return itemNumber;
    }

    public enum ItemSlotType {
        ROBE_ARMOR,
        POTION,
        BOW_ACTIVE,
        WATCH_ACTIVE,
        TOTEM_ACTIVE,
        SHIELD,
        WEAPON_PASSIVE,
        RING_PASSIVE,
        AMULET_PASSIVE
    }
}
