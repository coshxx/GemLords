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
        int counter = 0;
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 6; y++) {
                if (counter >= itemsInInventory.size)
                    break;
                final BaseItem i = itemsInInventory.get(counter);
                if (i.isAddedToActionBar()) {
                    counter++;
                    continue;
                }
                i.setPosition(70 + ((x * i.getWidth()) + (x * 100)), (AnotherManager.VIRTUAL_HEIGHT - 80) - (y * 140));
                i.clearListeners();
                i.addListener(new ClickListener() {
                    public void clicked(InputEvent e, float x, float y) {
                        if (i.isSelected()) {
                            i.unselect();
                            return;
                        }
                        i.selected();
                        for (BaseItem otherItem : itemsInInventory)
                            if (otherItem != i)
                                otherItem.unselect();
                    }
                });
                stage.addActor(i);
                counter++;
            }
        }
    }
}
