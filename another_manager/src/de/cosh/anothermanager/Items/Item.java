package de.cosh.anothermanager.Items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import de.cosh.anothermanager.AnotherManager;


public class Item {
	public enum ItemType {
		ITEM_ROBE("data/robe.png");

		private String texturePath;
		public String getTexturePath() {
			return texturePath;
		}
		
		ItemType(String texturePath) {
			this.texturePath = texturePath;
		}
	}

	private Image itemImage;
	private String itemName;
	private String itemText;
	
	public Item(AnotherManager myGame, ItemType t) {
		itemImage = new Image(myGame.assets.get(t.texturePath, Texture.class));
	}
	
	public void setItemName(String name) {
		this.itemName = name;
	}
	
	public void setItemText(String text) {
		this.itemText = text;
	}
	
	public Image getItemImage() {
		return itemImage;
	}
}
