package de.cosh.anothermanager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;

public class GameScreen implements Screen {
	private Game myGame;
	private Board board;

	public GameScreen(Game myGame) {
		this.myGame = myGame;
		board = new Board();
	}
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.5f, 0.5f, 1.0f, 1.0f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		update_input();
		update_client(delta);
		draw_client();
	}

	private void draw_client() {
		board.draw();
	}
	private void update_client(float delta) {
		
	}
	private void update_input() {
		
	}
	@Override
	public void resize(int width, int height) {
		
	}	

	@Override
	public void show() {
		board.init();
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
