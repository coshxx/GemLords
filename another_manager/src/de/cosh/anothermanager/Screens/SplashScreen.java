package de.cosh.anothermanager.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import de.cosh.anothermanager.AnotherManager;

public class SplashScreen implements Screen {
    private final AnotherManager myGame;
    private Texture splashTexture;
    private Stage stage;
    private Image splashImage;

    private Texture emptyT;
    private Texture fullT;
    private NinePatch empty;
    private NinePatch full;

    private BitmapFont bmf;

    public SplashScreen(final AnotherManager myGame) {
        this.myGame = myGame;
    }

    @Override
    public void dispose() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void render(final float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();

        if (myGame.assets.update()) {
            myGame.soundPlayer.touchSounds();
            myGame.soundPlayer.PlayBlub2();
            myGame.setScreen(myGame.mapTraverseScreen);
        }
        float left = Gdx.graphics.getWidth() * 0.1f;
        float bot = Gdx.graphics.getHeight() * 0.5f;
        float width = Gdx.graphics.getWidth() - (2*left);
        float done = myGame.assets.getProgress()*width;

        SpriteBatch sb = stage.getSpriteBatch();
        sb.begin();
        empty.draw(sb, left, bot, Gdx.graphics.getWidth()-(2*left), 30);
        full.draw(sb, left, bot, done, 30);
        bmf.drawMultiLine(sb, (int) (myGame.assets.getProgress() * 100) + "% loaded", width/2+60, bot+20, 0, BitmapFont.HAlignment.CENTER);
        sb.end();

    }

    @Override
    public void resize(final int width, final int height) {
    }

    @Override
    public void resume() {

    }

    @Override
    public void show() {
        splashTexture = new Texture("data/splash.jpg");
        stage = new Stage();
        stage.setCamera(myGame.camera);
        splashImage = new Image(splashTexture);
        splashImage.setBounds(0, 0, myGame.VIRTUAL_WIDTH, myGame.VIRTUAL_HEIGHT);
        splashImage.addAction(Actions.alpha(0));
        splashImage.addAction(Actions.sequence(Actions.fadeIn(.5f), Actions.delay(2), Actions.fadeOut(.5f), Actions.run(new Runnable() {
            @Override
            public void run() {
                myGame.setScreen(myGame.menuScreen);
            }
        })));
        stage.addActor(splashImage);

        emptyT = new Texture(Gdx.files.internal("data/empty.png"));
        fullT = new Texture(Gdx.files.internal("data/full.png"));
        empty = new NinePatch(new TextureRegion(emptyT, 24, 24), 8, 8, 8, 8);
        full = new NinePatch(new TextureRegion(fullT, 24, 24), 8, 8, 8, 8);

        bmf = new BitmapFont();

        loadAllAssets();
    }

    private void loadAllAssets() {
        myGame.assets.load("data/background.png", Texture.class);

        myGame.assets.load("data/ball_blue.png", Texture.class);
        myGame.assets.load("data/ball_green.png", Texture.class);
        myGame.assets.load("data/ball_purple.png", Texture.class);
        myGame.assets.load("data/ball_red.png", Texture.class);
        myGame.assets.load("data/ball_white.png", Texture.class);
        myGame.assets.load("data/ball_yellow.png", Texture.class);

        myGame.assets.load("data/special_blue.png", Texture.class);
        myGame.assets.load("data/special_green.png", Texture.class);
        myGame.assets.load("data/special_purple.png", Texture.class);
        myGame.assets.load("data/special_red.png", Texture.class);
        myGame.assets.load("data/special_white.png", Texture.class);
        myGame.assets.load("data/special_yellow.png", Texture.class);

        myGame.assets.load("data/abilityattack.png", Texture.class);
        myGame.assets.load("data/abilityfireball.png", Texture.class);

        myGame.assets.load("data/debuff_border.png", Texture.class);

        myGame.assets.load("data/empty.png", Texture.class);
        myGame.assets.load("data/full.png", Texture.class);
        myGame.assets.load("data/logo.png", Texture.class);
        myGame.assets.load("data/map.png", Texture.class);
        myGame.assets.load("data/point.png", Texture.class);
        myGame.assets.load("data/pointdone.png", Texture.class);
        myGame.assets.load("data/button.png", Texture.class);
        myGame.assets.load("data/splash.jpg", Texture.class);
        myGame.assets.load("data/cell_back.png", Texture.class);
        myGame.assets.load("data/enemy.png", Texture.class);
        myGame.assets.load("data/star.png", Texture.class);
        myGame.assets.load("data/treasure.jpg", Texture.class);
        myGame.assets.load("data/heart.png", Texture.class);
        myGame.assets.load("data/heartempty.png", Texture.class);

        myGame.assets.load("data/blub1.ogg", Sound.class);
        myGame.assets.load("data/blub2.ogg", Sound.class);
        myGame.assets.load("data/blub3.ogg", Sound.class);

        myGame.assets.load("data/bang.ogg", Sound.class);
        myGame.assets.load("data/music.ogg", Music.class);
        myGame.assets.load("data/loot.ogg", Sound.class);
        myGame.assets.load("data/victory.ogg", Sound.class);
        myGame.assets.load("data/boo.ogg", Sound.class);
        myGame.assets.load("data/woosh.ogg", Sound.class);
        myGame.assets.load("data/convert.ogg", Sound.class);
        myGame.assets.load("data/ding.ogg", Sound.class);
        myGame.assets.load("data/abilityattack.ogg", Sound.class);
        myGame.assets.load("data/fireball_start.ogg", Sound.class);

        myGame.assets.load("data/awesome.ogg", Sound.class);
        myGame.assets.load("data/awesome.png", Texture.class);

        myGame.assets.load("data/error.ogg", Sound.class);

        myGame.assets.load("data/font.fnt", BitmapFont.class);
        myGame.assets.load("data/font2.fnt", BitmapFont.class);
    }

}
