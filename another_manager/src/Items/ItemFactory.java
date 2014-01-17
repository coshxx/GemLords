package Items;

import de.cosh.anothermanager.AnotherManager;

/**
 * Created by cosh on 17.01.14.
 */
public class ItemFactory {
    private AnotherManager myGame;

    public ItemFactory(AnotherManager myGame) {
        this.myGame = myGame;
    }

    public Item generateRobe() {
        Item item = new Item(myGame, Item.ItemType.ITEM_ROBE);
        return item;
    }
}
