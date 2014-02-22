package de.cosh.gemlords.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import de.cosh.gemlords.CustomStyle;
import de.cosh.gemlords.GemLord;
import de.cosh.gemlords.Items.BaseItem;
import de.cosh.gemlords.LanguageManager;
import de.cosh.gemlords.Screens.LoadoutScreen;
import de.cosh.gemlords.Screens.MapTraverseScreen;

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
        LanguageManager lm = LanguageManager.getInstance();
		button = new TextButton(lm.getString("Loadout"), skin);
        button.setStyle(CustomStyle.getInstance().getStyle());
		button.setBounds(x-100, y, 200, 100);
		button.addListener(new ClickListener() {
			@Override
			public void clicked(final com.badlogic.gdx.scenes.scene2d.InputEvent event, final float x, final float y) {
				stage.addAction(Actions.after(Actions.sequence(Actions.fadeOut(.25f),
						Actions.run(new Runnable() {
							@Override
							public void run() {
                                final GemLord myGame = GemLord.getInstance();
                                myGame.mapTraverseScreen.enemyWindowOpen = false;
								GemLord.getInstance().setScreen(new LoadoutScreen());
							}
						}))));
			}
		});

		stage.addActor(button);
	}

	public void createBacktoMapButton(final Stage stage, float x, float y) {
		skin.getFont("default-font").getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        LanguageManager lm = LanguageManager.getInstance();
		button = new TextButton(lm.getString("Back to map"), skin);
        button.setStyle(CustomStyle.getInstance().getStyle());
		button.getLabel().setFontScale(1f);
		button.setBounds(x, y, 200, 100);
		button.addListener(new ClickListener() {
			@Override
			public void clicked(final com.badlogic.gdx.scenes.scene2d.InputEvent event, final float x, final float y) {
				stage.addAction(Actions.after(Actions.sequence(Actions.fadeOut(.25f),
						Actions.run(new Runnable() {
							@Override
							public void run() {
								GemLord.getInstance();
								GemLord.soundPlayer.stopLoadoutMusic();
                                GemLord.getInstance().player.savePreferences();
								GemLord.getInstance().setScreen(new MapTraverseScreen(GemLord.getInstance()));
							}
						}))));
			}
		});
		stage.addActor(button);
	}

	public void createRemoveFromBarButton(final Stage stage, float x, float y) {
		skin.getFont("default-font").getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        LanguageManager lm = LanguageManager.getInstance();
		button = new TextButton(lm.getString("Remove"), skin);
        button = new TextButton(lm.getString("Loadout"), skin);
        button.setStyle(CustomStyle.getInstance().getStyle());
		button.getLabel().setFontScale(1f);
		button.setPosition(x, y);
		button.setLayoutEnabled(true);
		
		button.setBounds(x, y, 200, 100);
		button.addListener(new ClickListener() {
			@Override
			public void clicked(final com.badlogic.gdx.scenes.scene2d.InputEvent event, final float x, final float y) {
				ArrayList<BaseItem> items = GemLord.getInstance().player.getInventory().getAllItems();
				for (BaseItem item : items) {
					if (item.isAddedToActionBar()) {
						if (item.isSelected()) {
							item.removeFromActionBar();
							item.unselect();
							item.setDrawText(true);
							GemLord.getInstance().player.getInventory().resortItems();
						}
					}
				}
			}
		});
		
		stage.addActor(button);
	}
}
