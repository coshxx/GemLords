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
	private BitmapFont bmf;
	private NinePatch empty;
	private Texture emptyT;
	private NinePatch full;

	private Texture fullT;
	private final AnotherManager myGame;
	private Image splashImage;
	private Texture splashTexture;

	private Stage stage;

	public SplashScreen(final AnotherManager myGame) {
		this.myGame = myGame;
	}

	@Override
	public void dispose() {

	}

	@Override
	public void hide() {

	}

	private void loadAllAssets() {
		myGame.assets.load("data/textures/background.png", Texture.class);

		myGame.assets.load("data/textures/ball_blue.png", Texture.class);
		myGame.assets.load("data/textures/ball_green.png", Texture.class);
		myGame.assets.load("data/textures/ball_purple.png", Texture.class);
		myGame.assets.load("data/textures/ball_red.png", Texture.class);
		myGame.assets.load("data/textures/ball_white.png", Texture.class);
		myGame.assets.load("data/textures/ball_yellow.png", Texture.class);

		myGame.assets.load("data/textures/special_blueh.png", Texture.class);
		myGame.assets.load("data/textures/special_greenh.png", Texture.class);
		myGame.assets.load("data/textures/special_purpleh.png", Texture.class);
		myGame.assets.load("data/textures/special_redh.png", Texture.class);
		myGame.assets.load("data/textures/special_whiteh.png", Texture.class);
		myGame.assets.load("data/textures/special_yellowh.png", Texture.class);

        myGame.assets.load("data/textures/special_bluev.png", Texture.class);
        myGame.assets.load("data/textures/special_greenv.png", Texture.class);
        myGame.assets.load("data/textures/special_purplev.png", Texture.class);
        myGame.assets.load("data/textures/special_redv.png", Texture.class);
        myGame.assets.load("data/textures/special_whitev.png", Texture.class);
        myGame.assets.load("data/textures/special_yellowv.png", Texture.class);

		myGame.assets.load("data/textures/abilityattack.png", Texture.class);
		myGame.assets.load("data/textures/abilityfireball.png", Texture.class);
		myGame.assets.load("data/textures/abilitypoison.png", Texture.class);


		myGame.assets.load("data/textures/debuff_border.png", Texture.class);
		myGame.assets.load("data/textures/item_border.png", Texture.class);
		myGame.assets.load("data/textures/minorhealthpotion.png", Texture.class);

		myGame.assets.load("data/textures/empty.png", Texture.class);
		myGame.assets.load("data/textures/full.png", Texture.class);
		myGame.assets.load("data/textures/logo.png", Texture.class);
		myGame.assets.load("data/textures/map.png", Texture.class);
		myGame.assets.load("data/textures/point.png", Texture.class);
		myGame.assets.load("data/textures/pointdone.png", Texture.class);
		myGame.assets.load("data/textures/button.png", Texture.class);
		myGame.assets.load("data/textures/splash.jpg", Texture.class);
		myGame.assets.load("data/textures/cell_back.png", Texture.class);
		myGame.assets.load("data/textures/enemy.png", Texture.class);
		myGame.assets.load("data/textures/enemyscarecrow.png", Texture.class);
		myGame.assets.load("data/textures/enemysnake.png", Texture.class);
		myGame.assets.load("data/textures/enemyrat.png", Texture.class);
		myGame.assets.load("data/textures/star.png", Texture.class);
		myGame.assets.load("data/textures/treasure.jpg", Texture.class);
		myGame.assets.load("data/textures/heart.png", Texture.class);
		myGame.assets.load("data/textures/heartempty.png", Texture.class);
		myGame.assets.load("data/textures/robe.png", Texture.class);
		myGame.assets.load("data/textures/robe_scholar.png", Texture.class);
        myGame.assets.load("data/textures/awesome.png", Texture.class);
        myGame.assets.load("data/textures/loadout.jpg", Texture.class);

		myGame.assets.load("data/sounds/blub1.ogg", Sound.class);
		myGame.assets.load("data/sounds/blub2.ogg", Sound.class);
		myGame.assets.load("data/sounds/blub3.ogg", Sound.class);

		myGame.assets.load("data/sounds/bang.ogg", Sound.class);
		myGame.assets.load("data/sounds/music.ogg", Music.class);
		myGame.assets.load("data/sounds/loot.ogg", Sound.class);
		myGame.assets.load("data/sounds/victory.ogg", Sound.class);
		myGame.assets.load("data/sounds/boo.ogg", Sound.class);
		myGame.assets.load("data/sounds/woosh.ogg", Sound.class);
		myGame.assets.load("data/sounds/convert.ogg", Sound.class);
		myGame.assets.load("data/sounds/ding.ogg", Sound.class);
		myGame.assets.load("data/sounds/gulp.ogg", Sound.class);
        myGame.assets.load("data/sounds/loadoutmusic.ogg", Music.class);

        myGame.assets.load("data/sounds/abilityattack_fire.ogg", Sound.class);
        myGame.assets.load("data/sounds/abilityfireball_fire.ogg", Sound.class);
        myGame.assets.load("data/sounds/abilitypoison_fire.ogg", Sound.class);

		myGame.assets.load("data/sounds/awesome.ogg", Sound.class);


		myGame.assets.load("data/sounds/error.ogg", Sound.class);

		myGame.assets.load("data/fonts/font.fnt", BitmapFont.class);
		myGame.assets.load("data/fonts/font2.fnt", BitmapFont.class);

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
		final float left = Gdx.graphics.getWidth() * 0.1f;
		final float bot = Gdx.graphics.getHeight() * 0.5f;
		final float width = Gdx.graphics.getWidth() - (2 * left);
		final float done = myGame.assets.getProgress() * width;

		final SpriteBatch sb = stage.getSpriteBatch();
		sb.begin();
		empty.draw(sb, left, bot, Gdx.graphics.getWidth() - (2 * left), 30);
		full.draw(sb, left, bot, done, 30);
		bmf.drawMultiLine(sb, (int) (myGame.assets.getProgress() * 100) + "% loaded", width / 2 + 60, bot + 20, 0,
				BitmapFont.HAlignment.CENTER);
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
		splashTexture = new Texture("data/textures/splash.jpg");
		stage = new Stage();
		stage.setCamera(myGame.camera);
		splashImage = new Image(splashTexture);

		splashImage.setBounds(0, 0, myGame.VIRTUAL_WIDTH, myGame.VIRTUAL_HEIGHT);
		splashImage.addAction(Actions.alpha(0));
		splashImage.addAction(Actions.fadeIn(0.5f));
		stage.addActor(splashImage);

		emptyT = new Texture(Gdx.files.internal("data/textures/empty.png"));
		fullT = new Texture(Gdx.files.internal("data/textures/full.png"));
		empty = new NinePatch(new TextureRegion(emptyT, 24, 24), 8, 8, 8, 8);
		full = new NinePatch(new TextureRegion(fullT, 24, 24), 8, 8, 8, 8);

		bmf = new BitmapFont();

		loadAllAssets();
        myGame.assets.finishLoading();
	}

}
