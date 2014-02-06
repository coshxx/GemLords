package de.cosh.anothermanager.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

import de.cosh.anothermanager.AnotherManager;
import de.cosh.anothermanager.Items.BaseItem;
import de.cosh.anothermanager.Screens.LoadoutScreen;
import de.cosh.anothermanager.Screens.MapTraverseScreen;

import java.util.ArrayList;

/**
 * Created by cosh on 20.01.14.
 */
public class GUIButton {
	private TextButton button;

	private Skin skin;

	public GUIButton() {
		skin = new Skin(Gdx.files.internal("data/ui/uiskin.json"));
	}

	public void createLoadoutButton(final Stage stage, float x, float y) {
		button = new TextButton("Loadout", skin);
		button.getStyle().font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		button.getStyle().font.setScale(2f);
		button.setBounds(x-100, y, 200, 100);
		button.addListener(new ClickListener() {
			@Override
			public void clicked(final com.badlogic.gdx.scenes.scene2d.InputEvent event, final float x, final float y) {
				stage.addAction(Actions.after(Actions.sequence(Actions.fadeOut(.25f),
						Actions.run(new Runnable() {
							@Override
							public void run() {
                                final AnotherManager myGame = AnotherManager.getInstance();
                                myGame.mapTraverseScreen.enemyWindowOpen = false;
								AnotherManager.getInstance().setScreen(new LoadoutScreen());
							}
						}))));
			}
		});

		stage.addActor(button);
	}

	public void createBacktoMapButton(final Stage stage, float x, float y) {
		skin.getFont("default-font").getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		button = new TextButton("Back to map", skin);
		button.getLabel().setFontScale(2f);
		button.setBounds(x, y, 200, 100);
		button.addListener(new ClickListener() {
			@Override
			public void clicked(final com.badlogic.gdx.scenes.scene2d.InputEvent event, final float x, final float y) {
				stage.addAction(Actions.after(Actions.sequence(Actions.fadeOut(.25f),
						Actions.run(new Runnable() {
							@Override
							public void run() {
								AnotherManager.getInstance();
								AnotherManager.soundPlayer.stopLoadoutMusic();
								AnotherManager.getInstance().setScreen(new MapTraverseScreen(AnotherManager.getInstance()));
							}
						}))));
			}
		});
		stage.addActor(button);
	}

	public void createRemoveFromBarButton(final Stage stage, float x, float y) {
		skin.getFont("default-font").getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		button = new TextButton("Remove", skin);
		button.getLabel().setFontScale(2f);
		button.setPosition(x, y);
		button.setLayoutEnabled(true);
		
		button.setBounds(x, y, 200, 100);
		button.addListener(new ClickListener() {
			@Override
			public void clicked(final com.badlogic.gdx.scenes.scene2d.InputEvent event, final float x, final float y) {
				ArrayList<BaseItem> items = AnotherManager.getInstance().player.getInventory().getAllItems();
				for (BaseItem item : items) {
					if (item.isAddedToActionBar()) {
						if (item.isSelected()) {
							item.removeFromActionBar();
							item.unselect();
							item.setDrawText(true);
							AnotherManager.getInstance().player.getInventory().resortItems();
						}
					}
				}
			}
		});
		
		stage.addActor(button);
	}
}
