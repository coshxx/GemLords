package de.cosh.anothermanager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AnotherManager extends Game {
    public MenuScreen menuScreen;
    public SplashScreen splashScreen;
    public GameScreen gameScreen;
    public OrthographicCamera camera;
    public MapTraverseScreen mapTraverseScreen;
    public AssetManager assets;
    public SoundPlayer soundPlayer;

    public Player player;
    public final int VIRTUAL_WIDTH = 720;
    public final int VIRTUAL_HEIGHT = 1280;

    private SpriteBatch batch;
    private BitmapFont bitmapFont;

    @Override
    public void create() {
        assets = new AssetManager();
        batch = new SpriteBatch();
        bitmapFont = new BitmapFont();
        camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        camera.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        player = new Player();
        splashScreen = new SplashScreen(this);
        soundPlayer = new SoundPlayer(this);
        menuScreen = new MenuScreen(this);
        gameScreen = new GameScreen(this);

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
        bitmapFont.draw(batch, "TW:" + Gdx.graphics.getWidth(), 10, 20);
        bitmapFont.draw(batch, "TH:" + Gdx.graphics.getHeight(), 10, 50);
        batch.end();

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void resume() {
    }
}
