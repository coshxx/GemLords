package de.cosh.gemlords.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import de.cosh.gemlords.CustomActions.EndSplashScreenAction;
import de.cosh.gemlords.GemLord;

public class SplashScreen implements Screen {
	private final GemLord myGame;
	private Image splashImage;
	private Texture splashTexture;
	private Stage stage;
    private SpriteBatch batch;
    private BitmapFont bmf;

	public SplashScreen(final GemLord myGame) {
		this.myGame = myGame;
	}

	@Override
	public void dispose() {

	}

	@Override
	public void hide() {

	}

	private void loadAllAssets() {
		GemLord.assets.load("data/textures/map.jpg", Texture.class);
		GemLord.assets.load("data/textures/map2.jpg", Texture.class);
		GemLord.assets.load("data/textures/splash.jpg", Texture.class);
		GemLord.assets.load("data/textures/menu.png", Texture.class);

        GemLord.assets.load("data/textures/pack.atlas", TextureAtlas.class);

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
        GemLord.assets.load("data/sounds/music6.ogg", Music.class);
        GemLord.assets.load("data/sounds/sequence.ogg", Music.class);
        GemLord.assets.load("data/sounds/creditsmusic.ogg", Music.class);
        GemLord.assets.load("data/sounds/scribble.ogg", Sound.class);
        GemLord.assets.load("data/sounds/loot.ogg", Music.class);
		GemLord.assets.load("data/sounds/victory.ogg", Sound.class);
		GemLord.assets.load("data/sounds/boo.ogg", Sound.class);
		GemLord.assets.load("data/sounds/woosh.ogg", Sound.class);
		GemLord.assets.load("data/sounds/abilityclaw.ogg", Sound.class);
		GemLord.assets.load("data/sounds/convert.ogg", Sound.class);
		GemLord.assets.load("data/sounds/ding.ogg", Sound.class);
		GemLord.assets.load("data/sounds/challenge.ogg", Sound.class);
		GemLord.assets.load("data/sounds/slidein.ogg", Sound.class);
		GemLord.assets.load("data/sounds/totem.ogg", Sound.class);
		GemLord.assets.load("data/sounds/gulp.ogg", Sound.class);
		GemLord.assets.load("data/sounds/pocketwatch.ogg", Sound.class);
		GemLord.assets.load("data/sounds/wolfhowl.ogg", Sound.class);
		GemLord.assets.load("data/sounds/bite.ogg", Sound.class);
		GemLord.assets.load("data/sounds/squeal.ogg", Sound.class);
		GemLord.assets.load("data/sounds/loadoutmusic.ogg", Music.class);
        GemLord.assets.load("data/sounds/curse.ogg", Sound.class);
        GemLord.assets.load("data/sounds/abilitymudbolt.ogg", Sound.class);
        GemLord.assets.load("data/sounds/swordthrow.ogg", Sound.class);
		GemLord.assets.load("data/sounds/menumusic.ogg", Music.class);
        GemLord.assets.load("data/particles/test.p", ParticleEffect.class);
		GemLord.assets.load("data/ui/uiskin.json", Skin.class);

		GemLord.assets.load("data/sounds/abilityattack_fire.ogg", Sound.class);
		GemLord.assets.load("data/sounds/abilityfireball_fire.ogg", Sound.class);
		GemLord.assets.load("data/sounds/abilitypoison_fire.ogg", Sound.class);
		GemLord.assets.load("data/sounds/abilitypetrify.ogg", Sound.class);

		GemLord.assets.load("data/sounds/awesome.ogg", Sound.class);
	}

	@Override
	public void pause() {

	}

	@Override
	public void render(final float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(delta);
		stage.draw();

		if (GemLord.assets.update()) {
            stage.addAction(Actions.sequence(
                    Actions.fadeOut(0.5f),
                    new EndSplashScreenAction()
            ));}
        batch.begin();
        bmf.setColor(0.5f, 0.5f, 0.5f, 1f);
        bmf.draw(batch, "Loading...", Gdx.graphics.getWidth()/2, 200);
        batch.end();
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
        bmf = new BitmapFont();
        batch = new SpriteBatch();

        GemLord.soundPlayer.playSplash();
        loadAllAssets();
	}

}
