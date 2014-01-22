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
import de.cosh.anothermanager.Screens.*;
import de.cosh.anothermanager.SwapGame.GemFactory;


public class AnotherManager extends Game {
    public static final int VIRTUAL_HEIGHT = 1280;
    public static final int VIRTUAL_WIDTH = 720;
    public static AssetManager assets;
    public static SoundPlayer soundPlayer;
    public static boolean DEBUGMODE = false;
    private static AnotherManager instance;
    public OrthographicCamera camera;
    public Enemy enemy;
    public EnemyManager enemyManager;
    public GameScreen gameScreen;
    public GemFactory gemFactory;
    public LootScreen lootScreen;
    public MapTraverseScreen mapTraverseScreen;
    public MenuScreen menuScreen;
    public Player player;
    public SplashScreen splashScreen;
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
        camera.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        camera.update();
        player = new Player(this);
        enemyManager = new EnemyManager();
        splashScreen = new SplashScreen(this);
        soundPlayer = new SoundPlayer(this);
        menuScreen = new MenuScreen(this);
        gameScreen = new GameScreen(this);
        lootScreen = new LootScreen(this, enemyManager);
        mapTraverseScreen = new MapTraverseScreen(this);
        gemFactory = new GemFactory(this);
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
        if (DEBUGMODE) {
            batch.setProjectionMatrix(camera.combined);
            batch.begin();
            bitmapFont.draw(batch, "TW:" + Gdx.graphics.getWidth(), 10, 20);
            bitmapFont.draw(batch, "TH:" + Gdx.graphics.getHeight(), 10, 50);
            batch.end();
        }
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
