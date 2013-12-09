package de.cosh.anothermanager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class MenuScreen implements Screen {
	private AnotherManager myGame;
	private Board board;
	private SpriteBatch batch;

    private Texture logoTexture;
    private Vector2 logoPosition;
    private float logoSpeed;

	public MenuScreen(AnotherManager myGame) {
		this.myGame = myGame;
		board = new Board();
		batch = new SpriteBatch();
		board.init();

        logoTexture = new Texture("data/logo.png");
        logoPosition = new Vector2();
        logoSpeed = 50f;

        logoPosition.x = 160;
        logoPosition.y = 20;
	}
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.5f, 0.5f, 1.0f, 1.0f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(logoTexture, logoPosition.x, logoPosition.y);
        batch.end();
	}

	@Override
	public void resize(int width, int height) {
		
	}	

	@Override
	public void show() {
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		
	}

}
