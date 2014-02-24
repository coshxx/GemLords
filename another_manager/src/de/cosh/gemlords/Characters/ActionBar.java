package de.cosh.gemlords.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.cosh.gemlords.GemLord;
import de.cosh.gemlords.Items.BaseItem;
import de.cosh.gemlords.SwapGame.ParticleActor;

import java.util.ArrayList;

/**
 * Created by cosh on 20.01.14.
 */
public class ActionBar {
    public static final int BARLENGTH = 6;
    private final float ACTION_PAD_X = 20;
    private final float ACTION_SPACEING_X = 31;
    private BaseItem[] itemsInBar;
    private Image[] itemBorders;

    private Image actionBarImage;


    public ActionBar() {
        itemBorders = new Image[BARLENGTH];
        itemsInBar = new BaseItem[BARLENGTH];
    }

    public void addToLoadoutScreen(final Stage stage) {
        TextureAtlas atlas = GemLord.assets.get("data/textures/pack.atlas", TextureAtlas.class);
        actionBarImage = new Image(atlas.findRegion("actionbar"));
        actionBarImage.setPosition(0, 150);
        stage.addActor(actionBarImage);

        for (int i = 0; i < BARLENGTH; i++) {
            final int index = i;
            itemBorders[i] = new Image(atlas.findRegion("item_border"));
            itemBorders[i].setPosition((ACTION_PAD_X + ((i) * itemBorders[i].getWidth())) + ((i) * ACTION_SPACEING_X), 170);
            itemBorders[i].clearListeners();
            itemBorders[i].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    ArrayList<BaseItem> baseItems = GemLord.getInstance().player.getInventory().getAllItems();
                    for (BaseItem item : baseItems) {
                        if (item.isSelected()) {
                            if (itemsInBar[index] == null) {
                                if (item.getActionBarSlot() != -1) {
                                    itemsInBar[item.getActionBarSlot()] = null;
                                }
                                item.setActionBarSlot(index);
                                itemsInBar[index] = item;
                                itemsInBar[index].setPosition(itemBorders[index].getX(), itemBorders[index].getY());
                                ParticleActor p = new ParticleActor(itemBorders[index].getX() + (itemBorders[index].getWidth() / 2), itemBorders[index].getY() + itemBorders[index].getHeight() / 2);
                                GemLord.soundPlayer.playWoosh();
                                stage.addActor(p);
                                item.addedToActionBar(true);
                                item.unselect();
                                item.setDrawText(false);
                                GemLord.getInstance().loadoutScreen.refresh();
                            }
                        }
                    }
                }
            });
            stage.addActor(itemBorders[i]);
            if (itemsInBar[i] != null) {
                itemsInBar[i].setPosition(itemBorders[i].getX(), itemBorders[i].getY());
                itemsInBar[i].setDrawText(false);
                stage.addActor(itemsInBar[i]);
            }
        }
    }

    public void removeFromBar(BaseItem baseItem) {
        for (int i = 0; i < BARLENGTH; i++) {
            if (itemsInBar[i] == null)
                continue;
            if (itemsInBar[i].equals(baseItem)) {
                itemsInBar[i].addedToActionBar(false);
                itemsInBar[i] = null;
            }
        }
    }

    public void addToBoard(Group foreGround) {
        TextureAtlas atlas = GemLord.assets.get("data/textures/pack.atlas", TextureAtlas.class);
        actionBarImage = new Image(atlas.findRegion("actionbar"));
        actionBarImage.setPosition(0, 80);
        foreGround.addActor(actionBarImage);
        for (int i = 0; i < BARLENGTH; i++) {
            final int index = i;

            itemBorders[i] = new Image(atlas.findRegion("item_border"));
            itemBorders[i].setPosition((ACTION_PAD_X + ((i) * itemBorders[i].getWidth())) + ((i) * ACTION_SPACEING_X), 95);
            itemBorders[i].clearListeners();
            foreGround.addActor(itemBorders[i]);
            if (itemsInBar[i] != null) {
                itemsInBar[i].clearListeners();
                itemsInBar[i].resetCooldown();
                itemsInBar[i].setPosition(itemBorders[i].getX(), itemBorders[i].getY());
                itemsInBar[i].addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        itemsInBar[index].onUse();
                    }
                });
                foreGround.addActor(itemsInBar[i]);
            }
        }
    }

    public boolean hasFreeSlot() {
        boolean slotFree = false;
        for (int i = 0; i < BARLENGTH; i++) {
            if (itemsInBar[i] == null) {
                slotFree = true;
            }
        }
        return slotFree;
    }

    public void addToActionBar(BaseItem item) {
        for (int i = 0; i < BARLENGTH; i++) {
            if (itemsInBar[i] == null) {
                itemsInBar[i] = item;
                item.setActionBarSlot(i);
                item.addedToActionBar(true);
                item.setDrawText(false);
                if (itemBorders[i] == null) {
                    itemBorders[i] = new Image(GemLord.assets.get("data/textures/item_border.png", Texture.class));
                    itemBorders[i].setPosition((ACTION_PAD_X + ((i) * itemBorders[i].getWidth())) + ((i) * ACTION_SPACEING_X), 95);
                    itemBorders[i].clearListeners();
                }
                item.setPosition(itemBorders[i].getX(), itemBorders[i].getY());
                break;
            }
        }
    }

    public BaseItem getItemInSlot(int i) {
        if (itemsInBar[i] == null)
            return null;
        else return itemsInBar[i];
    }

    public void addToActionBarAt(BaseItem actionBarItem, int slot) {
        itemsInBar[slot] = actionBarItem;
        itemsInBar[slot].setDrawText(false);
        itemsInBar[slot].addedToActionBar(true);
        itemsInBar[slot].setActionBarSlot(slot);
        if (itemBorders[slot] == null) {
            TextureAtlas atlas = GemLord.assets.get("data/textures/pack.atlas", TextureAtlas.class);
            itemBorders[slot] = new Image(atlas.findRegion("item_border"));
            itemBorders[slot].setPosition((ACTION_PAD_X + ((slot) * itemBorders[slot].getWidth())) + ((slot) * ACTION_SPACEING_X), 95);
            itemBorders[slot].clearListeners();
        }
        itemsInBar[slot].setPosition(itemBorders[slot].getX(), itemBorders[slot].getY());
    }

    public void clear() {
        for (int i = 0; i < BARLENGTH; i++)
            itemsInBar[i] = null;
    }

    public void remove() {
        actionBarImage.remove();
        for (int i = 0; i < itemsInBar.length; i++)
            if (itemsInBar[i] != null) {
                itemsInBar[i].remove();
            }
        for (int i = 0; i < itemBorders.length; i++)
            if (itemBorders[i] != null) {
                itemBorders[i].remove();
            }
    }
}
