package de.cosh.gemlords.Screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Json;

import de.cosh.gemlords.CustomActions.EndEpisode1Action;
import de.cosh.gemlords.CustomActions.ToMenuScreenAction;
import de.cosh.gemlords.GemLord;
import de.cosh.gemlords.Characters.Enemy;
import de.cosh.gemlords.Characters.Player;
import de.cosh.gemlords.GUI.GUIButton;
import de.cosh.gemlords.Items.*;

/**
 * Created by cosh on 10.12.13.
 */
public class Episode1TraverseScreen implements Screen, InputProcessor {
	public boolean enemyWindowOpen;
	private boolean fadeMusic;
	private Image mapImage;
	private final GemLord myGame;
	private Json json;
	private Stage stage;

	public Episode1TraverseScreen(final GemLord gemLord) {
		myGame = gemLord;
		json = new Json();
	}

	@Override
	public void dispose() {

	}

	public void fadeMusic() {
		fadeMusic = true;
	}

	@Override
	public void hide() {
		GemLord.soundPlayer.stopMapMusic();
		enemyWindowOpen = false;
	}

	private void initEnemyLocations() {
		Player player = GemLord.getInstance().player;
		int counter = 0;
		while( true ) {
			FileHandle handle = Gdx.files.internal("data/enemies/episode1/enemy" + counter + ".dat");
			if( !handle.exists() )
				break;
			Enemy e = json.fromJson(Enemy.class, handle.readString());
			e.setDefeated(player.levelDone[e.getEnemyNumber()]);
			e.loadImage();
			if( e.getEnemyNumber() == 0 ) {
				e.addPositionalButtonToMap(e.getLocationOnMap(), e.getImage(), e.getHealth(), stage, myGame.enemyManager);
                player.setPositionOnMap(e.getLocationOnMap());
			} else {
				int previous = e.getEnemyNumber() - 1;
				if( previous < 0 )
					break;
				if( player.levelDone[previous] ) {
					e.addPositionalButtonToMap(e.getLocationOnMap(), e.getImage(), e.getHealth(), stage, myGame.enemyManager);
                    player.setPositionOnMap(e.getLocationOnMap());
				}
			}
			counter++;
		}

        if( player.levelDone[15] ) {
            stage.addAction(Actions.after(
                    Actions.sequence(
                            Actions.fadeOut(2f),
                            new EndEpisode1Action())));
        }
	}

	@Override
	public void pause() {

	}

	@Override
	public void render(final float delta) {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		stage.act(delta);
		stage.draw();

		if (fadeMusic) {
			GemLord.soundPlayer.fadeOutMapMusic(delta);
		}
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
		fadeMusic = false;
		enemyWindowOpen = false;

		mapImage = new Image(GemLord.assets.get("data/textures/map.jpg", Texture.class));
		mapImage.setBounds(0, 0, GemLord.VIRTUAL_WIDTH, GemLord.VIRTUAL_HEIGHT);
		stage.addActor(mapImage);

		initEnemyLocations();

		GUIButton guiButton = new GUIButton();
		guiButton.createLoadoutButton(stage, GemLord.VIRTUAL_WIDTH-100, 0);
		GemLord.soundPlayer.playMapMusic();

        InputMultiplexer plex = new InputMultiplexer();
        plex.addProcessor(stage);
        plex.addProcessor(this);
		Gdx.input.setInputProcessor(plex);

		stage.addAction(Actions.alpha(0));
		stage.addAction(Actions.fadeIn(1));
	}

    @Override
    public boolean keyDown(int keycode) {
        if( keycode == Input.Keys.ESCAPE || keycode == Input.Keys.BACK ) {
            stage.addAction(Actions.sequence(
                    Actions.fadeOut(1f),
                    new ToMenuScreenAction()
            ));
            return true;
        } else
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
