package de.cosh.anothermanager.Items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import de.cosh.anothermanager.AnotherManager;


public class ItemApprenticeRobe extends BaseItem {

	public ItemApprenticeRobe() {
        super(AnotherManager.assets.get("data/textures/robe.png", Texture.class));
        setItemName("Apprentice Robe");
        setItemText("Grants an additional\n 25 hp");
	}
}
