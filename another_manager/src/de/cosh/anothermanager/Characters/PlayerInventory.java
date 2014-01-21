package de.cosh.anothermanager.Characters;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import de.cosh.anothermanager.AnotherManager;
import de.cosh.anothermanager.Items.BaseItem;

/**
 * Created by cosh on 21.01.14.
 */
public class PlayerInventory {
    private Array<BaseItem> itemsInInventory;

    public PlayerInventory() {
        itemsInInventory = new Array<BaseItem>();
    }

    public void addItem(BaseItem item) {
        itemsInInventory.add(item);
    }

    public Array<BaseItem> getAllItems() {
        return itemsInInventory;
    }

    public void addToLoadoutScreen(Stage stage) {
        float x = 0;
        float y = 0;
        for( int i = 0; i < itemsInInventory.size; i++ ) {
            final BaseItem item = itemsInInventory.get(i);
            if( item.isAddedToActionBar() )
                continue;
            item.setPosition(70 + ((x * item.getWidth()) + (x * 100)), (AnotherManager.VIRTUAL_HEIGHT - 80) - (y * 140));
            x++;
            if( x >= 4 ) {
                x = 0;
                y++;
            }
            item.clearListeners();
            item.addListener(new ClickListener() {
                public void clicked(InputEvent e, float x, float y) {
                    if (item.isSelected()) {
                        item.unselect();
                        return;
                    }
                    item.selected();
                    for (BaseItem otherItem : itemsInInventory)
                        if (otherItem != item)
                            otherItem.unselect();
                }
            });
            stage.addActor(item);
        }
    }

    public void resortItems() {
        float x = 0;
        float y = 0;
        for( int i = 0; i < itemsInInventory.size; i++ ) {
            final BaseItem item = itemsInInventory.get(i);
            if( item.isAddedToActionBar() )
                continue;
            item.setPosition(70 + ((x * item.getWidth()) + (x * 100)), (AnotherManager.VIRTUAL_HEIGHT - 80) - (y * 140));
            x++;
            if( x >= 4 ) {
                x = 0;
                y++;
            }
        }
    }
}
