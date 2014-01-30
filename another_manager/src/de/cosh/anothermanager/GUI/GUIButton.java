package de.cosh.anothermanager.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

import de.cosh.anothermanager.AnotherManager;
import de.cosh.anothermanager.Items.BaseItem;
import de.cosh.anothermanager.Screens.LoadoutScreen;
import de.cosh.anothermanager.Screens.MapTraverseScreen;

/**
 * Created by cosh on 20.01.14.
 */
public class GUIButton {
	private TextButton button;

	private Skin skin;

	public GUIButton() {
		skin = new Skin(Gdx.files.internal("data/ui/uiskin.json"));
	}

	public void createLoadoutButton(final Table table, float x, float y) {
		button = new TextButton("Loadout", skin);
		/*
        button.setPosition(x, y);
        button.setLayoutEnabled(true);
        button.setBounds(x, y, 100, 100);
		 */
		button.addListener(new ClickListener() {
			@Override
			public void clicked(final com.badlogic.gdx.scenes.scene2d.InputEvent event, final float x, final float y) {
				table.getStage().addAction(Actions.sequence(Actions.fadeOut(.25f),
						Actions.run(new Runnable() {
							@Override
							public void run() {
                                final AnotherManager myGame = AnotherManager.getInstance();
                                myGame.mapTraverseScreen.enemyWindowOpen = false;
								AnotherManager.getInstance().setScreen(new LoadoutScreen());
							}
						})));
			}
		});

		table.row();
		table.add(button).expand().bottom().right().size(100, 50);
	}

	public void createBacktoMapButton(final Table table, float x, float y) {
		button = new TextButton("Back to map", skin);
		button.setBounds(x, y, 200, 200);
		button.addListener(new ClickListener() {
			@Override
			public void clicked(final com.badlogic.gdx.scenes.scene2d.InputEvent event, final float x, final float y) {
				table.addAction(Actions.sequence(Actions.fadeOut(.25f),
						Actions.run(new Runnable() {
							@Override
							public void run() {
								AnotherManager.getInstance();
								AnotherManager.soundPlayer.stopLoadoutMusic();
								AnotherManager.getInstance().setScreen(new MapTraverseScreen(AnotherManager.getInstance()));
							}
						})));
			}
		});
		table.add(button).expand().left().bottom().size(100, 50);
	}

	public void createRemoveFromBarButton(final Table table, float x, float y) {
		button = new TextButton("Remove", skin);
		button.setPosition(x, y);
		button.setLayoutEnabled(true);
		button.setBounds(x, y, 200, 200);
		button.addListener(new ClickListener() {
			@Override
			public void clicked(final com.badlogic.gdx.scenes.scene2d.InputEvent event, final float x, final float y) {
				Array<BaseItem> items = AnotherManager.getInstance().player.getInventory().getAllItems();
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
		
		table.add(button).expand().right().bottom().size(100, 50);
	}
}
