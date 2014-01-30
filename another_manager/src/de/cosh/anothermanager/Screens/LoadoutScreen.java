package de.cosh.anothermanager.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import de.cosh.anothermanager.AnotherManager;
import de.cosh.anothermanager.Characters.ActionBar;
import de.cosh.anothermanager.GUI.GUIButton;

/**
 * Created by cosh on 07.01.14.
 */
public class LoadoutScreen implements Screen {
	private Stage gameStage;
	private Stage guiStage;
	private Table table;

	public LoadoutScreen() {
	}

	@Override
	public void dispose() {

	}

	@Override
	public void hide() {
        gameStage.dispose();
	}

	@Override
	public void pause() {

	}

	@Override
	public void render(final float delta) {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		gameStage.act(delta);
		gameStage.draw();
		
		guiStage.act(delta);
		guiStage.draw();
	}

	@Override
	public void resize(final int width, final int height) {
		//stage.setViewport(width, height, false);
		//gameStage.setViewport(AnotherManager.VIRTUAL_WIDTH, AnotherManager.VIRTUAL_HEIGHT, false);
		guiStage.setViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
	}

	@Override
	public void resume() {

	}

	@Override
	public void show() {
		gameStage = new Stage();
		guiStage = new Stage();
		
		gameStage.setCamera(AnotherManager.getInstance().camera);
		guiStage.setCamera(AnotherManager.getInstance().guiCamera);

		table = new Table();
		table.setFillParent(true);
		GUIButton guiButton = new GUIButton();
		guiButton.createBacktoMapButton(table, 0, 0);
		guiButton.createRemoveFromBarButton(table, 0, 0);
		
		InputMultiplexer plex = new InputMultiplexer();
		plex.addProcessor(gameStage);
		plex.addProcessor(guiStage);
		Gdx.input.setInputProcessor(plex);
		guiStage.addActor(table);
		
		Image background = new Image(AnotherManager.assets.get("data/textures/loadout.jpg", Texture.class));
		background.setBounds(0, 0, AnotherManager.VIRTUAL_WIDTH, AnotherManager.VIRTUAL_HEIGHT);
		gameStage.addActor(background);

		AnotherManager.soundPlayer.playLoadoutMusic();

		ActionBar actionBar = AnotherManager.getInstance().player.getActionBar();
		actionBar.addToLoadoutScreen(gameStage);
		AnotherManager.getInstance().player.getInventory().addToLoadoutScreen(gameStage);
		AnotherManager.getInstance().player.getInventory().resortItems();

		gameStage.addAction(Actions.alpha(0.0f));
        gameStage.addAction(Actions.fadeIn(1.0f));
		guiStage.addAction(Actions.alpha(0.0f));
        guiStage.addAction(Actions.fadeIn(1.0f));
	}
}
