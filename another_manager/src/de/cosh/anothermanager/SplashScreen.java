package de.cosh.anothermanager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SplashScreen implements Screen {

	private SpriteBatch batch;
	private final float fadeCompleteTime = 1f;
	private float fadeInTime = 0f;
	private final Game myGame;
	private Texture splashTexture;

	public SplashScreen(final Game myGame) {
		this.myGame = myGame;
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
	public void render(final float delta) {
		fadeInTime += delta;
		if (fadeInTime >= 1f) {
			fadeInTime = 1f;
		}
		final float d = 1f - (fadeCompleteTime - fadeInTime);
		batch.setColor(d, d, d, d);
		batch.begin();
		batch.draw(splashTexture, 0, 0);
		batch.end();
	}

	@Override
	public void resize(final int width, final int height) {
	}

	@Override
	public void resume() {

	}

	@Override
	public void show() {
		splashTexture = new Texture("data/splash.jpg");
		batch = new SpriteBatch();
	}

}
