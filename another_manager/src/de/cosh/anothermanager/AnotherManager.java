package de.cosh.anothermanager;

import com.badlogic.gdx.Game;

public class AnotherManager extends Game {
	private GameScreen gameScreen;
	
	@Override
	public void create() {
		gameScreen = new GameScreen(this);
		setScreen(gameScreen);
	}

	@Override
	public void dispose() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void render() {		
		super.render();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void resume() {
	}
}
