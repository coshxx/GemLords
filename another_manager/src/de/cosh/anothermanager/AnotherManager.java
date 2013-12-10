package de.cosh.anothermanager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AnotherManager extends Game {
	public MenuScreen menuScreen;
	public SplashScreen splashScreen;
    public GameScreen gameScreen;
    public OrthographicCamera camera;
    public MapTraverseScreen mapTraverseScreen;

    public Player player;
    public final int VIRTUAL_WIDTH = 768;
    public final int VIRTUAL_HEIGHT = 1280;

    private SpriteBatch batch;
    private BitmapFont bitmapFont;
	
	@Override
	public void create() {
        batch = new SpriteBatch();
        bitmapFont = new BitmapFont();
        camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        camera.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        player = new Player();
		menuScreen = new MenuScreen(this);
        gameScreen = new GameScreen(this);
		splashScreen = new SplashScreen(this);
        mapTraverseScreen = new MapTraverseScreen(this);
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
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        bitmapFont.draw(batch, "VW:" + VIRTUAL_WIDTH, 10, 20 );
        bitmapFont.draw(batch, "VH:" + VIRTUAL_HEIGHT, 10, 50 );
        batch.end();

	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void resume() {
	}
}
