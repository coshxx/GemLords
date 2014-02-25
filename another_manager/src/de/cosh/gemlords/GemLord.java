package de.cosh.gemlords;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Scaling;

import de.cosh.gemlords.Characters.Enemy;
import de.cosh.gemlords.Characters.EnemyManager;
import de.cosh.gemlords.Characters.Player;
import de.cosh.gemlords.Screens.*;

public class GemLord extends Game {
	public static final int VIRTUAL_HEIGHT = 1280;
	public static final int VIRTUAL_WIDTH = 720;
	public static final float ASPECT_RATIO = (float) VIRTUAL_WIDTH / (float) VIRTUAL_HEIGHT;
	public static AssetManager assets;
	public static SoundPlayer soundPlayer;
	public static boolean DEBUGMODE = false;
    public static boolean DEBUGITEMSADDED = false;
	private static GemLord instance;
	public OrthographicCamera camera;
	private OrthographicCamera guiCamera;
	public Rectangle viewport;
	public Enemy enemy;
	public EnemyManager enemyManager;
	public GameScreen gameScreen;
	public LootScreen lootScreen;
    public LoadoutScreen loadoutScreen;
    public ShadyIntroScreen shadyIntroScreen;
    public CreditScreen creditScreen;
	public MapTraverseScreen mapTraverseScreen;
	public MenuScreen menuScreen;
    public AfterActionReport afterActionReport;
	public Player player;
    public LanguageManager languageManager;
	private SplashScreen splashScreen;
	private SpriteBatch batch;
	private BitmapFont bitmapFont;
    public IActivityRequestHandler requestHandler;


    public GemLord(IActivityRequestHandler requestHandler ) {
        this.requestHandler = requestHandler;
    }
    public static GemLord getInstance() {
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
        shadyIntroScreen = new ShadyIntroScreen();
		gameScreen = new GameScreen(this);
        creditScreen = new CreditScreen();
		lootScreen = new LootScreen(this, enemyManager);
		mapTraverseScreen = new MapTraverseScreen(this);
		bitmapFont = new BitmapFont();
        loadoutScreen = new LoadoutScreen();
        afterActionReport = new AfterActionReport();
        languageManager = LanguageManager.getInstance();
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
		Vector2 size = Scaling.fit.apply(GemLord.VIRTUAL_WIDTH,
				GemLord.VIRTUAL_HEIGHT, width, height);
		
		float ratio = (float) width / (float) height;
		if (ratio < 0.66f && ratio > 0.55f) {
			stage.setViewport(GemLord.VIRTUAL_WIDTH,
					GemLord.VIRTUAL_HEIGHT);
			return;
		}
		int viewportX = (int) (width - size.x) / 2;
		int viewportY = (int) (height - size.y) / 2;
		int viewportWidth = (int) size.x;
		int viewportHeight = (int) size.y;
		Gdx.gl.glViewport(viewportX, viewportY, viewportWidth, viewportHeight);
		stage.setViewport(GemLord.VIRTUAL_WIDTH,
				GemLord.VIRTUAL_HEIGHT, true, viewportX, viewportY,
				viewportWidth, viewportHeight);
	}
}
