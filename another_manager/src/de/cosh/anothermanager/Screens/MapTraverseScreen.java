package de.cosh.anothermanager.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import com.badlogic.gdx.utils.Json;
import de.cosh.anothermanager.AnotherManager;
import de.cosh.anothermanager.Characters.Enemy;
import de.cosh.anothermanager.GUI.GUIButton;

/**
 * Created by cosh on 10.12.13.
 */
public class MapTraverseScreen implements Screen {
	public boolean enemyWindowOpen;
	private boolean fadeMusic;
	private Image mapImage;
	private Texture mapTexture;
	private final AnotherManager myGame;
	private Skin skin;

	private Stage stage;

	public MapTraverseScreen(final AnotherManager anotherManager) {
		myGame = anotherManager;
	}

	@Override
	public void dispose() {

	}

	public void fadeMusic() {
		fadeMusic = true;
	}

	@Override
	public void hide() {
		stage.dispose();
		myGame.soundPlayer.stopMapMusic();
	}

	private void initEnemyLocations() {
         Json json = new Json();
        int counter = 0;
        while( true ) {
            FileHandle handle = Gdx.files.internal("data/enemies/enemy" + counter + ".dat");
            if( !handle.exists() )
                break;

            Enemy e = json.fromJson(Enemy.class, handle.readString());
            e.loadImage();
            e.addPositionalButtonToMap(e.getLocationOnMap(), e.getImage(), e.getHealth(), stage, myGame.enemyManager);
            counter++;
        }

        FileHandle newHandle = Gdx.files.local("data/enemydump.txt");
        Enemy e = new Enemy();
        newHandle.writeString(json.prettyPrint(e), false);
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
			myGame.soundPlayer.fadeOutMapMusic(delta);
		}
	}

	@Override
	public void resize(final int width, final int height) {
	}

	@Override
	public void resume() {

	}

	@Override
	public void show() {
		stage = new Stage();
		skin = new Skin();
		fadeMusic = false;
		enemyWindowOpen = false;
		mapTexture = myGame.assets.get("data/textures/map.png", Texture.class);

		stage.setCamera(myGame.camera);

		mapImage = new Image(mapTexture);
		mapImage.setBounds(0, 0, myGame.VIRTUAL_WIDTH, myGame.VIRTUAL_HEIGHT);

		stage.addActor(mapImage);
		stage.addAction(Actions.alpha(0));
		stage.addAction(Actions.fadeIn(1));

		initEnemyLocations();

        GUIButton loadout = new GUIButton();
        loadout.createLoadoutButton(stage, AnotherManager.VIRTUAL_WIDTH-100, 0);

		for (int x = 0; x < 5; x++) {
			final Image heartEmpty = new Image(myGame.assets.get("data/textures/heartempty.png", Texture.class));
			heartEmpty.setPosition(32 + (32 * x), myGame.VIRTUAL_HEIGHT - 64);
			stage.addActor(heartEmpty);
		}

		for (int x = 0; x < myGame.player.getLives(); x++) {
			final Image heart = new Image(myGame.assets.get("data/textures/heart.png", Texture.class));
			heart.setPosition(32 + (32 * x), myGame.VIRTUAL_HEIGHT - 64);
			stage.addActor(heart);
		}
		myGame.soundPlayer.playMapMusic();
		Gdx.input.setInputProcessor(stage);
	}
}
