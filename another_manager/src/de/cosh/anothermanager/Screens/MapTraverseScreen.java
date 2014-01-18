package de.cosh.anothermanager.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import de.cosh.anothermanager.AnotherManager;
import de.cosh.anothermanager.Characters.Enemy;

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
		final Enemy enemy = new Enemy(myGame, myGame.assets.get("data/enemy.png", Texture.class));
		enemy.setDefeated(myGame.player.levelDone[0]);
		enemy.addPositionalButtonToMap(120, 110, enemy.getImage(), 30, stage);

		final Enemy enemy2 = new Enemy(myGame, myGame.assets.get("data/enemy.png", Texture.class));
		enemy2.setDefeated(myGame.player.levelDone[1]);
		enemy2.addPositionalButtonToMap(350, 110, enemy2.getImage(), 60, stage);

		final Enemy enemy3 = new Enemy(myGame, myGame.assets.get("data/enemysnake.png", Texture.class));
		enemy3.setDefeated(myGame.player.levelDone[2]);
		enemy3.addPositionalButtonToMap(450, 110, enemy3.getImage(), 90, stage);

		final Enemy enemy4 = new Enemy(myGame, myGame.assets.get("data/enemyrat.png", Texture.class));
		enemy4.setDefeated(myGame.player.levelDone[3]);
		enemy4.addPositionalButtonToMap(550, 110, enemy4.getImage(), 150, stage);

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
		mapTexture = myGame.assets.get("data/map.png", Texture.class);

		stage.setCamera(myGame.camera);

		mapImage = new Image(mapTexture);
		mapImage.setBounds(0, 0, myGame.VIRTUAL_WIDTH, myGame.VIRTUAL_HEIGHT);

		stage.addActor(mapImage);
		stage.addAction(Actions.alpha(0));
		stage.addAction(Actions.fadeIn(1));

		initEnemyLocations();

		for (int x = 0; x < 5; x++) {
			final Image heartEmpty = new Image(myGame.assets.get("data/heartempty.png", Texture.class));
			heartEmpty.setPosition(32 + (32 * x), myGame.VIRTUAL_HEIGHT - 64);
			stage.addActor(heartEmpty);
		}

		for (int x = 0; x < myGame.player.getLives(); x++) {
			final Image heart = new Image(myGame.assets.get("data/heart.png", Texture.class));
			heart.setPosition(32 + (32 * x), myGame.VIRTUAL_HEIGHT - 64);
			stage.addActor(heart);
		}
		myGame.soundPlayer.playMapMusic();
		Gdx.input.setInputProcessor(stage);
	}
}
