package de.cosh.gemlords.Characters;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import de.cosh.gemlords.CustomStyle;
import de.cosh.gemlords.GemLord;
import de.cosh.gemlords.Items.BaseItem;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cosh on 21.01.14.
 */
public class PlayerInventory {
	private ArrayList<BaseItem> itemsInInventory;

	public PlayerInventory() {
		itemsInInventory = new ArrayList<BaseItem>();
	}

	public void addItem(BaseItem item) {
		itemsInInventory.add(item);
	}

	public ArrayList<BaseItem> getAllItems() {
		return itemsInInventory;
	}

	public void addToLoadoutScreen(final Stage stage) {

        final HashMap<String, Integer> items = new HashMap<String, Integer>();

        for( int i = 0; i < itemsInInventory.size(); i++ ) {
            final BaseItem item = itemsInInventory.get(i);
            items.put(item.getName(), item.getID());

            item.clearListeners();
            item.addListener(new ClickListener() {
                @Override
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

        }

        ArrayList<String> strings = new ArrayList<String>();
        for( String key : items.keySet() ) {
            strings.add(key);
        }
        Skin s = GemLord.assets.get("data/ui/uiskin.json", Skin.class);

        final SelectBox box = new SelectBox(strings.toArray(), s);
        box.getStyle().font = s.getFont("credit-font");
        box.setBounds(40, GemLord.VIRTUAL_HEIGHT-150, 640, 75);

        box.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                BaseItem itemCenter = BaseItem.getNewItemByID(items.get(box.getSelection()));
                itemCenter.setPosition(330, 500);
                itemCenter.setDrawText(true);
                stage.addActor(itemCenter);
            }
        });
        stage.addActor(box);

	}

	public void resortItems() {
		float x = 0;
		float y = 0;
		for( int i = 0; i < itemsInInventory.size(); i++ ) {
			final BaseItem item = itemsInInventory.get(i);
			if( item.isAddedToActionBar() )
				continue;
			//item.setPosition(70 + ((x * item.getWidth()) + (x * 100)), (GemLord.VIRTUAL_HEIGHT - 80) - (y * 140));
            item.setPosition(70 + ((x * item.getWidth()) + (x * 140)), (GemLord.VIRTUAL_HEIGHT - 100) - (y * 220));
			x++;
			if( x >= 3 ) {
				x = 0;
				y++;
			}
		}
	}

    public void removeItem(BaseItem oldInventoryItem) {
        for( int i = 0; i < itemsInInventory.size(); i++ ) {
            BaseItem invItem = itemsInInventory.get(i);
            if( invItem == oldInventoryItem ) {
                itemsInInventory.remove(i);
                i--;
            }
        }
    }

    public ArrayList<BaseItem> getAllNotAddedItems() {
        ArrayList<BaseItem> returnItems = new ArrayList<BaseItem>();

        for( BaseItem item : itemsInInventory ) {
            if( item.isAddedToActionBar() )
                continue;
            returnItems.add(item);
        }
        return returnItems;
    }

    public void clearInventory() {
        itemsInInventory.clear();
    }
}
