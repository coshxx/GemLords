package de.cosh.anothermanager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import de.cosh.anothermanager.Characters.Enemy;
import de.cosh.anothermanager.Characters.EnemyManager;
import de.cosh.anothermanager.Characters.Player;
import de.cosh.anothermanager.Screens.GameScreen;
import de.cosh.anothermanager.Screens.LootScreen;
import de.cosh.anothermanager.Screens.MapTraverseScreen;
import de.cosh.anothermanager.Screens.MenuScreen;
import de.cosh.anothermanager.Screens.SplashScreen;


public class AnotherManager extends Game {
	public static final int VIRTUAL_HEIGHT = 1280;
	public static final int VIRTUAL_WIDTH = 720;
	public static final float ASPECT_RATIO = (float)VIRTUAL_WIDTH/(float)VIRTUAL_HEIGHT;

	public static AssetManager assets;
	public static SoundPlayer soundPlayer;
	public static boolean DEBUGMODE = true;
	private static AnotherManager instance;
	public OrthographicCamera camera;
	public OrthographicCamera guiCamera;
	public Rectangle viewport;
	public Enemy enemy;
	public EnemyManager enemyManager;
	public GameScreen gameScreen;
	public LootScreen lootScreen;
	public MapTraverseScreen mapTraverseScreen;
	public MenuScreen menuScreen;
	public Player player;
	public SplashScreen splashScreen;
	private SpriteBatch batch;

	public BitmapFont bitmapFont;

	public static AnotherManager getInstance() {
		return instance;
	}

	@Override
	public void create() {
		instance = this;
		assets = new AssetManager();
		batch = new SpriteBatch();
		camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		guiCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		player = new Player(this);
		enemyManager = new EnemyManager();
		splashScreen = new SplashScreen(this);
		soundPlayer = new SoundPlayer(this);
		menuScreen = new MenuScreen(this);
		gameScreen = new GameScreen(this);
		lootScreen = new LootScreen(this, enemyManager);
		mapTraverseScreen = new MapTraverseScreen(this);
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
		camera.update();
		
		Gdx.gl.glViewport((int)viewport.x, (int)viewport.y, (int)viewport.width, (int)viewport.height);
		if (DEBUGMODE) {
			//batch.setProjectionMatrix(camera.combined);
			batch.begin();
			bitmapFont.setColor(1f, 1f, 1f, 1f);
			bitmapFont.draw(batch, "TW:" + Gdx.graphics.getWidth(), 10, 20);
			bitmapFont.draw(batch, "TH:" + Gdx.graphics.getHeight(), 10, 50);
			batch.end();
		}
	}

	@Override
	public void resize(final int width, final int height) {
		super.resize(width, height);
		float aspectRatio = (float)width / (float)height;
		float scale = 1f;
		Vector2 crop = new Vector2(0f, 0f);
		
		if( aspectRatio > ASPECT_RATIO ) {
			scale = (float)height / (float) VIRTUAL_HEIGHT;
			crop.x = (width - VIRTUAL_WIDTH*scale)/2f;
		} else if (aspectRatio < ASPECT_RATIO ) {
			scale = (float)width / (float) VIRTUAL_WIDTH;
			crop.y = (height - VIRTUAL_HEIGHT*scale)/2f;
		} else {
			scale = (float) width / (float) VIRTUAL_WIDTH;
		}
		
		float w = (float)VIRTUAL_WIDTH * scale;
		float h = (float)VIRTUAL_HEIGHT * scale;
		viewport = new Rectangle(crop.x, crop.y, w, h);
		/*
		camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		camera.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		camera.update();
		
		guiCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		guiCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		guiCamera.update();
		*/
		
		}

	@Override
	public void resume() {
	}
}
