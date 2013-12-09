package de.cosh.anothermanager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class AnotherManager extends Game {
	public MenuScreen menuScreen;
	public SplashScreen splashScreen;
    public OrthographicCamera camera;

    public final int VIRTUAL_WIDTH = 720;
    public final int VIRTUAL_HEIGHT = 1280;
	
	@Override
	public void create() {
        camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        camera.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		menuScreen = new MenuScreen(this);
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
