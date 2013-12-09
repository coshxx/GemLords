package de.cosh.anothermanager;

import com.badlogic.gdx.Game;

public class AnotherManager extends Game {
	public GameScreen gameScreen;
	public SplashScreen splashScreen;

    public final int VIRTUAL_WIDTH = 1280;
    public final int VIRTUAL_HEIGHT = 720;
	
	@Override
	public void create() {
		gameScreen = new GameScreen(this);
		splashScreen = new SplashScreen(this);
		setScreen(splashScreen);
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
