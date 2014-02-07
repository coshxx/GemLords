package de.cosh.anothermanager.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import de.cosh.anothermanager.AnotherManager;

public class SplashScreen implements Screen {
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
		AnotherManager.assets.load("data/textures/background.png", Texture.class);

		AnotherManager.assets.load("data/textures/ball_blue.png", Texture.class);
		AnotherManager.assets.load("data/textures/ball_green.png", Texture.class);
		AnotherManager.assets.load("data/textures/ball_purple.png", Texture.class);
		AnotherManager.assets.load("data/textures/ball_red.png", Texture.class);
		AnotherManager.assets.load("data/textures/ball_white.png", Texture.class);
		AnotherManager.assets.load("data/textures/ball_yellow.png", Texture.class);

		AnotherManager.assets.load("data/textures/special_blueh.png", Texture.class);
		AnotherManager.assets.load("data/textures/special_greenh.png", Texture.class);
		AnotherManager.assets.load("data/textures/special_purpleh.png", Texture.class);
		AnotherManager.assets.load("data/textures/special_redh.png", Texture.class);
		AnotherManager.assets.load("data/textures/special_whiteh.png", Texture.class);
		AnotherManager.assets.load("data/textures/special_yellowh.png", Texture.class);

		AnotherManager.assets.load("data/textures/special_bluev.png", Texture.class);
		AnotherManager.assets.load("data/textures/special_greenv.png", Texture.class);
		AnotherManager.assets.load("data/textures/special_purplev.png", Texture.class);
		AnotherManager.assets.load("data/textures/special_redv.png", Texture.class);
		AnotherManager.assets.load("data/textures/special_whitev.png", Texture.class);
		AnotherManager.assets.load("data/textures/special_yellowv.png", Texture.class);

		AnotherManager.assets.load("data/textures/special_5.png", Texture.class);

		AnotherManager.assets.load("data/textures/abilityattack.png", Texture.class);
		AnotherManager.assets.load("data/textures/abilityfireball.png", Texture.class);
		AnotherManager.assets.load("data/textures/abilitypoison.png", Texture.class);
		AnotherManager.assets.load("data/textures/abilitypetrify.png", Texture.class);
		AnotherManager.assets.load("data/textures/abilitysmashblue.png", Texture.class);
		AnotherManager.assets.load("data/textures/abilitysmashred.png", Texture.class);
		AnotherManager.assets.load("data/textures/abilitythink.png", Texture.class);
		AnotherManager.assets.load("data/textures/abilitybite.png", Texture.class);

        AnotherManager.assets.load("data/textures/actionbar.png", Texture.class);

		AnotherManager.assets.load("data/textures/debuff_border.png", Texture.class);
		AnotherManager.assets.load("data/textures/buff_border.png", Texture.class);
		AnotherManager.assets.load("data/textures/item_border.png", Texture.class);
		AnotherManager.assets.load("data/textures/minorhealthpotion.png", Texture.class);
		AnotherManager.assets.load("data/textures/pocketwatch.png", Texture.class);

		AnotherManager.assets.load("data/textures/empty.png", Texture.class);
		AnotherManager.assets.load("data/textures/full.png", Texture.class);
		AnotherManager.assets.load("data/textures/logo.png", Texture.class);
		AnotherManager.assets.load("data/textures/map.png", Texture.class);
		AnotherManager.assets.load("data/textures/point.png", Texture.class);
		AnotherManager.assets.load("data/textures/abilityclaw.png", Texture.class);
		AnotherManager.assets.load("data/textures/clawfullscreen.png", Texture.class);
		AnotherManager.assets.load("data/textures/pointdone.png", Texture.class);
		AnotherManager.assets.load("data/textures/splash.jpg", Texture.class);
		AnotherManager.assets.load("data/textures/totem.png", Texture.class);
		AnotherManager.assets.load("data/textures/cell_back.png", Texture.class);
		AnotherManager.assets.load("data/textures/star.png", Texture.class);
		AnotherManager.assets.load("data/textures/treasure.jpg", Texture.class);
		AnotherManager.assets.load("data/textures/heart.png", Texture.class);
		AnotherManager.assets.load("data/textures/heartempty.png", Texture.class);
		AnotherManager.assets.load("data/textures/robe.png", Texture.class);
		AnotherManager.assets.load("data/textures/itemdagger.png", Texture.class);
		AnotherManager.assets.load("data/textures/itembow.png", Texture.class);
		AnotherManager.assets.load("data/textures/itemarrow.png", Texture.class);
		AnotherManager.assets.load("data/textures/itemamulet.png", Texture.class);
		AnotherManager.assets.load("data/textures/robe_scholar.png", Texture.class);
		AnotherManager.assets.load("data/textures/awesome.png", Texture.class);
		AnotherManager.assets.load("data/textures/loadout.jpg", Texture.class);
		AnotherManager.assets.load("data/textures/menu.png", Texture.class);
		AnotherManager.assets.load("data/textures/shield.png", Texture.class);

		AnotherManager.assets.load("data/sounds/blub1.ogg", Sound.class);
		AnotherManager.assets.load("data/sounds/blub2.ogg", Sound.class);
		AnotherManager.assets.load("data/sounds/blub3.ogg", Sound.class);

		AnotherManager.assets.load("data/sounds/bang.ogg", Sound.class);
		AnotherManager.assets.load("data/sounds/block.ogg", Sound.class);
		AnotherManager.assets.load("data/sounds/smash.ogg", Sound.class);
		AnotherManager.assets.load("data/sounds/music.ogg", Music.class);
		AnotherManager.assets.load("data/sounds/loot.ogg", Sound.class);
		AnotherManager.assets.load("data/sounds/victory.ogg", Sound.class);
		AnotherManager.assets.load("data/sounds/boo.ogg", Sound.class);
		AnotherManager.assets.load("data/sounds/woosh.ogg", Sound.class);
		AnotherManager.assets.load("data/sounds/abilityclaw.ogg", Sound.class);
		AnotherManager.assets.load("data/sounds/convert.ogg", Sound.class);
		AnotherManager.assets.load("data/sounds/ding.ogg", Sound.class);
		AnotherManager.assets.load("data/sounds/challenge.ogg", Sound.class);
		AnotherManager.assets.load("data/sounds/slidein.ogg", Sound.class);
		AnotherManager.assets.load("data/sounds/impressive.ogg", Sound.class);
		AnotherManager.assets.load("data/sounds/godlike.ogg", Sound.class);
		AnotherManager.assets.load("data/sounds/unstoppable.ogg", Sound.class);
		AnotherManager.assets.load("data/sounds/totem.ogg", Sound.class);
		AnotherManager.assets.load("data/sounds/music0.ogg", Music.class);
		AnotherManager.assets.load("data/sounds/gulp.ogg", Sound.class);
		AnotherManager.assets.load("data/sounds/pocketwatch.ogg", Sound.class);
		AnotherManager.assets.load("data/sounds/loadoutmusic.ogg", Music.class);
		AnotherManager.assets.load("data/sounds/menumusic.ogg", Music.class);
		

		AnotherManager.assets.load("data/ui/uiskin.json", Skin.class);

		AnotherManager.assets.load("data/sounds/abilityattack_fire.ogg", Sound.class);
		AnotherManager.assets.load("data/sounds/abilityfireball_fire.ogg", Sound.class);
		AnotherManager.assets.load("data/sounds/abilitypoison_fire.ogg", Sound.class);
		AnotherManager.assets.load("data/sounds/abilitypetrify.ogg", Sound.class);

		AnotherManager.assets.load("data/sounds/awesome.ogg", Sound.class);
		AnotherManager.assets.load("data/sounds/error.ogg", Sound.class);

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

		if (AnotherManager.assets.update()) {
			AnotherManager.soundPlayer.touchSounds();
			AnotherManager.soundPlayer.PlayBlub2();
			// set font texture scaling to linear
			AnotherManager.assets.get("data/ui/uiskin.json", Skin.class).getFont("default-font").getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			myGame.setScreen(myGame.menuScreen);
		}
		final float left = Gdx.graphics.getWidth() * 0.1f;
		final float bot = Gdx.graphics.getHeight() * 0.5f;
		final float width = Gdx.graphics.getWidth() - (2 * left);
		final float done = AnotherManager.assets.getProgress() * width;

		final SpriteBatch sb = stage.getSpriteBatch();
		sb.begin();
		empty.draw(sb, left, bot, Gdx.graphics.getWidth() - (2 * left), 30);
		full.draw(sb, left, bot, done, 30);
		/*
		.drawMultiLine(sb, (int) (AnotherManager.assets.getProgress() * 100) + "% loaded", width / 2 + 60, bot + 20, 0,
				BitmapFont.HAlignment.CENTER);
		 */
		sb.end();

	}

	@Override
	public void resize(final int width, final int height) {
		AnotherManager.getInstance().stageResize(width, height, stage);
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

		splashImage.setBounds(0, 0, AnotherManager.VIRTUAL_WIDTH, AnotherManager.VIRTUAL_HEIGHT);
		splashImage.addAction(Actions.alpha(0));
		splashImage.addAction(Actions.fadeIn(0.5f));
		stage.addActor(splashImage);

		emptyT = new Texture(Gdx.files.internal("data/textures/empty.png"));
		fullT = new Texture(Gdx.files.internal("data/textures/full.png"));
		empty = new NinePatch(new TextureRegion(emptyT, 24, 24), 8, 8, 8, 8);
		full = new NinePatch(new TextureRegion(fullT, 24, 24), 8, 8, 8, 8);


		loadAllAssets();
		AnotherManager.assets.finishLoading();
	}

}
