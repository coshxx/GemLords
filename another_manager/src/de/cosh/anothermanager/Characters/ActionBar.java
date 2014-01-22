package de.cosh.anothermanager.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import de.cosh.anothermanager.AnotherManager;
import de.cosh.anothermanager.Items.BaseItem;
import de.cosh.anothermanager.SwapGame.ParticleActor;

/**
 * Created by cosh on 20.01.14.
 */
public class ActionBar {
    private final float ACTION_PAD_X = 42;
    private final float ACTION_SPACEING_X = 5;
    private BaseItem[] itemsInBar;
    private Image[] itemBorders;
    
    private final int BARLENGTH = 8;

    public ActionBar() {
        itemBorders = new Image[BARLENGTH];
        itemsInBar = new BaseItem[BARLENGTH];
    }

    public void addToLoadoutScreen(final Stage stage) {
        for (int i = 0; i < BARLENGTH; i++) {
            final int index = i;
            itemBorders[i] = new Image(AnotherManager.assets.get("data/textures/item_border.png", Texture.class));
            itemBorders[i].setPosition((ACTION_PAD_X + ((i) * itemBorders[i].getWidth())) + ((i) * ACTION_SPACEING_X), 170);
            itemBorders[i].clearListeners();
            itemBorders[i].addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    Array<BaseItem> baseItems = AnotherManager.getInstance().player.getInventory().getAllItems();
                    for (BaseItem item : baseItems) {
                        if (item.isSelected()) {
                            if (itemsInBar[index] == null) {
                                itemsInBar[index] = item;
                                itemsInBar[index].setPosition(itemBorders[index].getX(), itemBorders[index].getY());
                                ParticleActor p = new ParticleActor(itemBorders[index].getX() + (itemBorders[index].getWidth()/2), itemBorders[index].getY() + itemBorders[index].getHeight()/2);
                                AnotherManager.getInstance().soundPlayer.playWoosh();
                                stage.addActor(p);
                                item.addedToActionBar(true);
                                item.unselect();
                                item.setDrawText(false);
                            }
                        }
                    }
                }
            });
            stage.addActor(itemBorders[i]);
            if (itemsInBar[i] != null)
                stage.addActor(itemsInBar[i]);
        }
    }

    public void removeFromBar(BaseItem baseItem) {
        for (int i = 0; i < BARLENGTH; i++) {
            if (itemsInBar[i] == null)
                continue;
            if (itemsInBar[i].equals(baseItem)) {
                itemsInBar[i] = null;
            }
        }
    }

    public void addToBoard(Group foreGround) {
        for (int i = 0; i < BARLENGTH; i++) {
            final int index = i;
            itemBorders[i] = new Image(AnotherManager.assets.get("data/textures/item_border.png", Texture.class));
            itemBorders[i].setPosition((ACTION_PAD_X + ((i) * itemBorders[i].getWidth())) + ((i) * ACTION_SPACEING_X), 170);
            itemBorders[i].clearListeners();
            foreGround.addActor(itemBorders[i]);
            if (itemsInBar[i] != null) {
                itemsInBar[i].clearListeners();
                itemsInBar[i].resetCooldown();
                itemsInBar[i].addListener(new ClickListener() {
                    public void clicked(InputEvent event, float x, float y) {
                        itemsInBar[index].onUse();
                    }
                });
                foreGround.addActor(itemsInBar[i]);
            }
        }
    }
}
