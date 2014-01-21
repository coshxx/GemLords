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
public class ActionBar {
    private final float ACTION_PAD_X = 40;
    private final float ACTION_SPACEING_X = 5;

    private BaseItem[] itemsInBar;
    private Image[] itemBorders;

    public ActionBar() {
        itemBorders = new Image[4];
        itemsInBar = new BaseItem[4];
    }

    public void addToLoadoutScreen(Stage stage) {
        for (int i = 0; i < 4; i++) {
            final int index = i;

            itemBorders[i] = new Image(AnotherManager.assets.get("data/textures/item_border.png", Texture.class));
            itemBorders[i].setPosition((ACTION_PAD_X + ((i) * itemBorders[i].getWidth())) + ((i) * ACTION_SPACEING_X), 100);
            itemBorders[i].clearListeners();
            itemBorders[i].addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    Array<BaseItem> baseItems = AnotherManager.getInstance().player.getInventory().getAllItems();
                    for (BaseItem item : baseItems ) {
                        if( item.isSelected() ) {
                            if( itemsInBar[index] == null ) {
                                itemsInBar[index] = item;
                                itemsInBar[index].setPosition(itemBorders[index].getX(), itemBorders[index].getY());
                                item.addedToActionBar(true);
                                item.unselect();
                                item.setDrawText(false);
                            }
                        }
                    }
                }
            });
            stage.addActor(itemBorders[i]);
            if( itemsInBar[i] != null )
                stage.addActor(itemsInBar[i]);
        }
    }

    public void removeFromBar(BaseItem baseItem) {
        for( int i = 0; i < 4; i++ ) {
            if( itemsInBar[i] == null )
                continue;
            if( itemsInBar[i].equals(baseItem)) {
                itemsInBar[i] = null;
            }
        }
    }

    public void addToBoard(Group foreGround) {
        for (int i = 0; i < 4; i++) {
            final int index = i;
            itemBorders[i].clearListeners();
            foreGround.addActor(itemBorders[i]);
            if( itemsInBar[i] != null ) {
                itemsInBar[i].clearListeners();
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
