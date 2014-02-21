package de.cosh.gemlords.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import de.cosh.gemlords.GemLord;

public class SplashScreen implements Screen {
	private NinePatch empty;
	private Texture emptyT;
	private NinePatch full;

	private Texture fullT;
	private final GemLord myGame;
	private Image splashImage;
	private Texture splashTexture;

	private Stage stage;

	public SplashScreen(final GemLord myGame) {
		this.myGame = myGame;
	}

	@Override
	public void dispose() {

	}

	@Override
	public void hide() {

	}

    public TextureAtlas getAtlas() {
        return GemLord.assets.get("data/texture/pack.atlas", TextureAtlas.class);
    }

	private void loadAllAssets() {
		GemLord.assets.load("data/textures/map.jpg", Texture.class);
		GemLord.assets.load("data/textures/splash.jpg", Texture.class);
		GemLord.assets.load("data/textures/menu.png", Texture.class);
        GemLord.assets.load("data/textures/pack.atlas", TextureAtlas.class);

		GemLord.assets.load("data/sounds/blub1.ogg", Sound.class);
		GemLord.assets.load("data/sounds/blub2.ogg", Sound.class);
		GemLord.assets.load("data/sounds/blub3.ogg", Sound.class);

		GemLord.assets.load("data/sounds/bang.ogg", Sound.class);
		GemLord.assets.load("data/sounds/lightningrod.ogg", Sound.class);
		GemLord.assets.load("data/sounds/block.ogg", Sound.class);
		GemLord.assets.load("data/sounds/critical.ogg", Sound.class);
		GemLord.assets.load("data/sounds/smash.ogg", Sound.class);
		GemLord.assets.load("data/sounds/gotitem.ogg", Sound.class);
		GemLord.assets.load("data/sounds/bow.ogg", Sound.class);
		GemLord.assets.load("data/sounds/turn.ogg", Sound.class);
		GemLord.assets.load("data/sounds/music.ogg", Music.class);
        GemLord.assets.load("data/sounds/music0.ogg", Music.class);
        GemLord.assets.load("data/sounds/music3.ogg", Music.class);
        GemLord.assets.load("data/sounds/music4.ogg", Music.class);
        GemLord.assets.load("data/sounds/music5.ogg", Music.class);
        GemLord.assets.load("data/sounds/DONOTUSE.ogg", Music.class);
        GemLord.assets.load("data/sounds/creditsmusic.ogg", Music.class);
        GemLord.assets.load("data/sounds/loot.ogg", Sound.class);
		GemLord.assets.load("data/sounds/victory.ogg", Sound.class);
		GemLord.assets.load("data/sounds/boo.ogg", Sound.class);
		GemLord.assets.load("data/sounds/woosh.ogg", Sound.class);
		GemLord.assets.load("data/sounds/abilityclaw.ogg", Sound.class);
		GemLord.assets.load("data/sounds/convert.ogg", Sound.class);
		GemLord.assets.load("data/sounds/ding.ogg", Sound.class);
		GemLord.assets.load("data/sounds/challenge.ogg", Sound.class);
		GemLord.assets.load("data/sounds/slidein.ogg", Sound.class);
		GemLord.assets.load("data/sounds/impressive.ogg", Sound.class);
		GemLord.assets.load("data/sounds/godlike.ogg", Sound.class);
		GemLord.assets.load("data/sounds/unstoppable.ogg", Sound.class);
		GemLord.assets.load("data/sounds/totem.ogg", Sound.class);
		GemLord.assets.load("data/sounds/gulp.ogg", Sound.class);
		GemLord.assets.load("data/sounds/pocketwatch.ogg", Sound.class);
		GemLord.assets.load("data/sounds/bite.ogg", Sound.class);
		GemLord.assets.load("data/sounds/loadoutmusic.ogg", Music.class);
		GemLord.assets.load("data/sounds/menumusic.ogg", Music.class);

		GemLord.assets.load("data/ui/uiskin.json", Skin.class);

		GemLord.assets.load("data/sounds/abilityattack_fire.ogg", Sound.class);
		GemLord.assets.load("data/sounds/abilityfireball_fire.ogg", Sound.class);
		GemLord.assets.load("data/sounds/abilitypoison_fire.ogg", Sound.class);
		GemLord.assets.load("data/sounds/abilitypetrify.ogg", Sound.class);

		GemLord.assets.load("data/sounds/awesome.ogg", Sound.class);
		GemLord.assets.load("data/sounds/error.ogg", Sound.class);

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

		if (GemLord.assets.update()) {
			GemLord.soundPlayer.touchSounds();
			GemLord.soundPlayer.PlayBlub2();
			// set font texture scaling to linear
			GemLord.assets.get("data/ui/uiskin.json", Skin.class).getFont("default-font").getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
            GemLord.assets.get("data/ui/uiskin.json", Skin.class).getFont("credit-font").getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			myGame.setScreen(myGame.menuScreen);
		}
		final float left = GemLord.VIRTUAL_WIDTH * 0.3f;
		final float bot = GemLord.VIRTUAL_HEIGHT * 0.5f;
		final float width = 300;
		final float done = GemLord.assets.getProgress() * width;

		final SpriteBatch sb = stage.getSpriteBatch();
		sb.begin();
		empty.draw(sb, left, bot, 300, 30);
		full.draw(sb, left, bot, done, 30);
		/*
		.drawMultiLine(sb, (int) (GemLord.assets.getProgress() * 100) + "% loaded", width / 2 + 60, bot + 20, 0,
				BitmapFont.HAlignment.CENTER);
		 */
		sb.end();

	}

	@Override
	public void resize(final int width, final int height) {
		GemLord.getInstance().stageResize(width, height, stage);
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

		splashImage.setBounds(0, 0, GemLord.VIRTUAL_WIDTH, GemLord.VIRTUAL_HEIGHT);
		splashImage.addAction(Actions.alpha(0));
		splashImage.addAction(Actions.fadeIn(0.5f));
		stage.addActor(splashImage);

		emptyT = new Texture(Gdx.files.internal("data/textures/empty.png"));
		fullT = new Texture(Gdx.files.internal("data/textures/full.png"));
		empty = new NinePatch(new TextureRegion(emptyT, 24, 24), 8, 8, 8, 8);
		full = new NinePatch(new TextureRegion(fullT, 24, 24), 8, 8, 8, 8);


		loadAllAssets();
		GemLord.assets.finishLoading();
	}

}