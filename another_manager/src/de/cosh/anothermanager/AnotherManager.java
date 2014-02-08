package de.cosh.anothermanager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;

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
	public static final float ASPECT_RATIO = (float) VIRTUAL_WIDTH / (float) VIRTUAL_HEIGHT;
	public static AssetManager assets;
	public static SoundPlayer soundPlayer;
	public static boolean DEBUGMODE = false;
    public static boolean DEBUGITEMSADDED = false;
	private static AnotherManager instance;
	public OrthographicCamera camera;
	private OrthographicCamera guiCamera;
	public Rectangle viewport;
	public Enemy enemy;
	public EnemyManager enemyManager;
	public GameScreen gameScreen;
	public LootScreen lootScreen;
	public MapTraverseScreen mapTraverseScreen;
	public MenuScreen menuScreen;
    public AfterActionReport afterActionReport;
	public Player player;
	private SplashScreen splashScreen;
	private SpriteBatch batch;

	private BitmapFont bitmapFont;

	public static AnotherManager getInstance() {
        return instance;
	}

	@Override
	public void create() {
		instance = this;
		assets = new AssetManager();
		batch = new SpriteBatch();
		camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		guiCamera = new OrthographicCamera(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		player = new Player(this);
		enemyManager = new EnemyManager();
		splashScreen = new SplashScreen(this);
		soundPlayer = new SoundPlayer(this);
		menuScreen = new MenuScreen(this);
		gameScreen = new GameScreen(this);
		lootScreen = new LootScreen(this, enemyManager);
		mapTraverseScreen = new MapTraverseScreen(this);
		bitmapFont = new BitmapFont();
        afterActionReport = new AfterActionReport();
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
        /*
		// Gdx.gl.glViewport((int)viewport.x, (int)viewport.y,
		// (int)viewport.width, (int)viewport.height);
		if (DEBUGMODE) {
			// batch.setProjectionMatrix(camera.combined);
			batch.begin();
			bitmapFont.setColor(1f, 1f, 1f, 1f);
			bitmapFont.draw(batch, "TW:" + Gdx.graphics.getWidth(), 10, 20);
			bitmapFont.draw(batch, "TH:" + Gdx.graphics.getHeight(), 10, 50);
			batch.end();
		}
		*/
	}

	@Override
	public void resize(final int width, final int height) {
		super.resize(width, height);
	}

	@Override
	public void resume() {
	}

	public void stageResize(int width, int height, Stage stage) {
		Vector2 size = Scaling.fit.apply(AnotherManager.VIRTUAL_WIDTH,
				AnotherManager.VIRTUAL_HEIGHT, width, height);
		
		float ratio = (float) width / (float) height;
		if (ratio < 0.66f && ratio > 0.55f) {
			stage.setViewport(AnotherManager.VIRTUAL_WIDTH,
					AnotherManager.VIRTUAL_HEIGHT);
			return;
		}
		int viewportX = (int) (width - size.x) / 2;
		int viewportY = (int) (height - size.y) / 2;
		int viewportWidth = (int) size.x;
		int viewportHeight = (int) size.y;
		Gdx.gl.glViewport(viewportX, viewportY, viewportWidth, viewportHeight);
		stage.setViewport(AnotherManager.VIRTUAL_WIDTH,
				AnotherManager.VIRTUAL_HEIGHT, true, viewportX, viewportY,
				viewportWidth, viewportHeight);
	}
}
