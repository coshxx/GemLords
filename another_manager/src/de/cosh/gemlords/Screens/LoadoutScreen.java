package de.cosh.gemlords.Screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import de.cosh.gemlords.GemLord;
import de.cosh.gemlords.Characters.ActionBar;
import de.cosh.gemlords.GUI.GUIButton;

/**
 * Created by cosh on 07.01.14.
 */
public class LoadoutScreen implements Screen, InputProcessor {
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
		GemLord.getInstance().stageResize(width, height, stage);
	}

	@Override
	public void resume() {

	}

	@Override
	public void show() {
		stage = new Stage();
        InputMultiplexer plex = new InputMultiplexer();
        plex.addProcessor(stage);
        plex.addProcessor(this);
        Gdx.input.setInputProcessor(plex);
        TextureAtlas atlas = GemLord.assets.get("data/textures/pack.atlas", TextureAtlas.class);
		Image background = new Image(atlas.findRegion("background"));
		background.setBounds(0, 0, GemLord.VIRTUAL_WIDTH, GemLord.VIRTUAL_HEIGHT);
		stage.addActor(background);

		GemLord.soundPlayer.playLoadoutMusic();

		ActionBar actionBar = GemLord.getInstance().player.getActionBar();
		actionBar.addToLoadoutScreen(stage);
		GemLord.getInstance().player.getInventory().addToLoadoutScreen(stage);
		GemLord.getInstance().player.getInventory().resortItems();
		
		GUIButton guiButton = new GUIButton();
		guiButton.createBacktoMapButton(stage, GemLord.VIRTUAL_WIDTH-200, 0);
		guiButton.createRemoveFromBarButton(stage, 0, 0);

		stage.addAction(Actions.alpha(0.0f));
        stage.addAction(Actions.fadeIn(1.0f));
	}

    @Override
    public boolean keyDown(int keycode) {
        if( keycode == Input.Keys.ESCAPE || keycode == Input.Keys.BACK ) {
            GemLord.getInstance().soundPlayer.stopLoadoutMusic();
            GemLord.getInstance().setScreen(GemLord.getInstance().mapTraverseScreen);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
