package de.cosh.anothermanager.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import de.cosh.anothermanager.AnotherManager;
import de.cosh.anothermanager.Items.BaseItem;

/**
 * Created by cosh on 20.01.14.
 */
public class ActionBar extends Actor {

    private final float ACTION_PAD_X = 40;
    private final float ACTION_SPACEING_X = 5;
    private BaseItem[] defaultSlots;
    private Image[] defaultBorders;
    private BaseItem[] actionSlots;
    private Image[] actionBorders;
    private ActionBarMode actionBarMode;


    public ActionBar(ActionBarMode barMode) {
        defaultSlots = new BaseItem[2];
        defaultBorders = new Image[2];
        actionSlots = new BaseItem[4];
        actionBorders = new Image[4];

        if (barMode == ActionBarMode.LOADOUT)
            addLoadOutListeners();
        else addActionListeners();
    }

    public void addToBoard(Group foreGround) {
        for (int i = 0; i < 2; i++)
            foreGround.addActor(defaultBorders[i]);
        for (int i = 0; i < 4; i++)
            foreGround.addActor(actionBorders[i]);

        Array<BaseItem> items = AnotherManager.getInstance().player.getInventoryItems();
        for (int i = 0; i < items.size; i++) {
            final BaseItem item = items.get(i);
            if (item.isAddedToActionBar()) {
                item.setPosition(defaultBorders[item.getActionBarSlot()].getX(), defaultBorders[item.getActionBarSlot()].getY());
                if( item.getItemSlotType() == BaseItem.ItemSlotType.POTION ) {
                    item.clearListeners();
                    item.addListener(new ClickListener() {
                        public void clicked(InputEvent event, float x, float y) {
                            item.onUse();
                        }
                    });
                }
                foreGround.getParent().addActor(item);
            }

        }
    }

    private void addActionListeners() {
        for (int i = 0; i < 2; i++) {
            final int index = i;
            defaultBorders[i] = new Image(AnotherManager.assets.get("data/textures/item_border.png", Texture.class));
            defaultBorders[i].setPosition(ACTION_PAD_X + (i * defaultBorders[i].getWidth()) + (i * ACTION_SPACEING_X), 100);
        }
        for (int i = 0; i < 4; i++) {
            actionBorders[i] = new Image(AnotherManager.assets.get("data/textures/item_border.png", Texture.class));
            actionBorders[i].setPosition((ACTION_PAD_X + ((i + 4) * actionBorders[i].getWidth())) + ((i + 4) * ACTION_SPACEING_X), 100);
        }

        Array<BaseItem> items = AnotherManager.getInstance().player.getInventoryItems();
        for (int i = 0; i < items.size; i++) {
            BaseItem item = items.get(i);
            if (item.isAddedToActionBar()) {
                defaultSlots[item.getActionBarSlot()] = item;
                item.setPosition(defaultBorders[item.getActionBarSlot()].getX(), defaultBorders[item.getActionBarSlot()].getY());
            }
        }
    }

    private void addLoadOutListeners() {
        for (int i = 0; i < 2; i++) {
            final int index = i;
            defaultBorders[i] = new Image(AnotherManager.assets.get("data/textures/item_border.png", Texture.class));
            defaultBorders[i].setPosition(ACTION_PAD_X + (i * defaultBorders[i].getWidth()) + (i * ACTION_SPACEING_X), 100);
            defaultBorders[i].addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    Array<BaseItem> items = AnotherManager.getInstance().player.getInventoryItems();
                    for (BaseItem i : items) {
                        if (i.isSelected()) {
                            if( i.isAddedToActionBar() ) {
                                defaultSlots[i.getActionBarSlot()] = null;
                            }
                            i.unselect();
                            i.setDrawText(false);
                            Actor test = event.getListenerActor();

                            if (defaultSlots[index] == null) {
                                defaultSlots[index] = i;
                                defaultSlots[index].setPosition(test.getX(), test.getY());
                                test.getStage().addActor(defaultSlots[index]);
                                i.addedToActionBar(true);
                                i.setActionBarSlot(index);
                            }
                        }
                    }
                }
            });
        }

        for (int i = 0; i < 4; i++) {
            actionBorders[i] = new Image(AnotherManager.assets.get("data/textures/item_border.png", Texture.class));
            actionBorders[i].setPosition((ACTION_PAD_X + ((i + 4) * actionBorders[i].getWidth())) + ((i + 4) * ACTION_SPACEING_X), 100);
        }
    }

    public void addToStage(Stage stage) {
        for (int i = 0; i < 2; i++)
            stage.addActor(defaultBorders[i]);
        for (int i = 0; i < 4; i++)
            stage.addActor(actionBorders[i]);

        Array<BaseItem> items = AnotherManager.getInstance().player.getInventoryItems();
        for (int i = 0; i < items.size; i++) {
            BaseItem item = items.get(i);
            if (item.isAddedToActionBar()) {
                item.setPosition(defaultBorders[item.getActionBarSlot()].getX(), defaultBorders[item.getActionBarSlot()].getY());
                stage.addActor(item);
            }
        }
    }

    public enum ActionBarMode {
        LOADOUT,
        ACTION
    }
}
