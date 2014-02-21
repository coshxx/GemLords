package de.cosh.gemlords.Characters;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import de.cosh.gemlords.GemLord;
import de.cosh.gemlords.Items.BaseItem;

import java.util.ArrayList;

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

	public void addToLoadoutScreen(Stage stage) {
		float x = 0;
		float y = 0;
		for( int i = 0; i < itemsInInventory.size(); i++ ) {
			final BaseItem item = itemsInInventory.get(i);
			if( !item.isAddedToActionBar() )
				item.setPosition(50 + ((x * item.getWidth()) + (x * 120)), (GemLord.VIRTUAL_HEIGHT - 80) - (y * 180));
			x++;
			if( x >= 3 ) {
				x = 0;
				y++;
			}
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
			stage.addActor(item);
		}
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
