package de.cosh.anothermanager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.cosh.anothermanager.Characters.Enemy;
import de.cosh.anothermanager.Characters.EnemyManager;
import de.cosh.anothermanager.Characters.Player;
import de.cosh.anothermanager.Screens.GameScreen;
import de.cosh.anothermanager.Screens.LootScreen;
import de.cosh.anothermanager.Screens.MapTraverseScreen;
import de.cosh.anothermanager.Screens.MenuScreen;
import de.cosh.anothermanager.Screens.SplashScreen;
import de.cosh.anothermanager.SwapGame.GemFactory;

public class AnotherManager extends Game {
	private static final String FONT_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	public AssetManager assets;
	private SpriteBatch batch;
	private BitmapFont bitmapFont;
	public OrthographicCamera camera;
	public Enemy enemy;
	public EnemyManager enemyManager;
	public GameScreen gameScreen;
	public GemFactory gemFactory;
	public LootScreen lootScreen;

	public MapTraverseScreen mapTraverseScreen;
	public MenuScreen menuScreen;
	public Player player;
	public SoundPlayer soundPlayer;

	public SplashScreen splashScreen;
	public final int VIRTUAL_HEIGHT = 1280;
	public final int VIRTUAL_WIDTH = 720;

	@Override
	public void create() {
		assets = new AssetManager();
		batch = new SpriteBatch();

		camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		camera.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		camera.update();
		player = new Player(this);
		splashScreen = new SplashScreen(this);
		soundPlayer = new SoundPlayer(this);
		menuScreen = new MenuScreen(this);
		gameScreen = new GameScreen(this);
		lootScreen = new LootScreen(this);
		mapTraverseScreen = new MapTraverseScreen(this);
		gemFactory = new GemFactory(this);
		enemyManager = new EnemyManager();
		bitmapFont = new BitmapFont();
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
	public void resize(final int width, final int height) {
		super.resize(width, height);
		camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		camera.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		camera.update();
	}

	@Override
	public void resume() {
	}
}
