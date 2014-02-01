package de.cosh.anothermanager.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import de.cosh.anothermanager.AnotherManager;
import de.cosh.anothermanager.Characters.ActionBar;
import de.cosh.anothermanager.GUI.GUIButton;

/**
 * Created by cosh on 07.01.14.
 */
public class LoadoutScreen implements Screen {
	private Stage stage;

	public LoadoutScreen() {
	}

	@Override
	public void dispose() {

	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(final int width, final int height) {
		AnotherManager.getInstance().stageResize(width, height, stage);
	}

	@Override
	public void resume() {

	}

	@Override
	public void show() {
		stage = new Stage();
		
		Gdx.input.setInputProcessor(stage);
		
		Image background = new Image(AnotherManager.assets.get("data/textures/loadout.jpg", Texture.class));
		background.setBounds(0, 0, AnotherManager.VIRTUAL_WIDTH, AnotherManager.VIRTUAL_HEIGHT);
		stage.addActor(background);

		AnotherManager.soundPlayer.playLoadoutMusic();

		ActionBar actionBar = AnotherManager.getInstance().player.getActionBar();
		actionBar.addToLoadoutScreen(stage);
		AnotherManager.getInstance().player.getInventory().addToLoadoutScreen(stage);
		AnotherManager.getInstance().player.getInventory().resortItems();
		
		GUIButton guiButton = new GUIButton();
		guiButton.createBacktoMapButton(stage, AnotherManager.VIRTUAL_WIDTH-200, 0);
		guiButton.createRemoveFromBarButton(stage, 0, 0);

		stage.addAction(Actions.alpha(0.0f));
        stage.addAction(Actions.fadeIn(1.0f));
	}
}
