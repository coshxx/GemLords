package de.cosh.anothermanager.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import de.cosh.anothermanager.AnotherManager;
import de.cosh.anothermanager.RectActor;

public class MenuScreenOld implements Screen {
	private TextButton exitGameButton;

	private Image logoImage;
	private Vector2 logoPosition;

	private Texture logoTexture;
	private final AnotherManager myGame;
	private TextButton newGameButton;
	private RectActor rectActor;
	private Stage stage;

	public MenuScreenOld(final AnotherManager myGame) {
		this.myGame = myGame;

	}

	@Override
	public void dispose() {

	}

	@Override
	public void hide() {
		stage.dispose();
	}

	@Override
	public void pause() {

	}

	@Override
	public void render(final float delta) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(final int width, final int height) {

	}

	@Override
	public void resume() {

	}

	@Override
	public void show() {
		logoTexture = AnotherManager.assets.get("data/textures/logo.png", Texture.class);
		final Texture buttonTexture = AnotherManager.assets.get("data/textures/button.png", Texture.class);

		logoPosition = new Vector2();
		logoPosition.x = (AnotherManager.VIRTUAL_WIDTH / 2) - (logoTexture.getWidth() / 2);
		logoPosition.y = AnotherManager.VIRTUAL_HEIGHT - (logoTexture.getHeight() * 1.1f);

		logoImage = new Image(logoTexture);
		logoImage.setPosition(logoPosition.x, logoPosition.y);

		stage = new Stage();
		stage.setCamera(myGame.camera);

		final TextureRegion upRegion = new TextureRegion(buttonTexture);
		final TextureRegion downRegion = new TextureRegion(buttonTexture);
		final BitmapFont buttonFont = new BitmapFont();

		final TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
		style.up = new TextureRegionDrawable(upRegion);
		style.down = new TextureRegionDrawable(downRegion);
		style.font = buttonFont;

		newGameButton = new TextButton("New Game", style);
		newGameButton.setPosition(logoPosition.x, 400);
		newGameButton.addListener(new ClickListener() {
			@Override
			public void clicked(final com.badlogic.gdx.scenes.scene2d.InputEvent event, final float x, final float y) {
				AnotherManager.soundPlayer.PlayBlub1();
				stage.addAction(Actions.sequence(Actions.fadeOut(.25f), Actions.delay(.25f),
						Actions.run(new Runnable() {
							@Override
							public void run() {
								myGame.setScreen(myGame.mapTraverseScreen);
							}
						})));
			}
		});

		exitGameButton = new TextButton("Exit", style);
		exitGameButton.setPosition(logoPosition.x, 150);
		exitGameButton.addListener(new ClickListener() {
			@Override
			public void clicked(final com.badlogic.gdx.scenes.scene2d.InputEvent event, final float x, final float y) {
				AnotherManager.soundPlayer.PlayBlub2();
				stage.addAction(Actions.sequence(Actions.fadeOut(.25f), Actions.run(new Runnable() {
					@Override
					public void run() {
						Gdx.app.exit();
					}
				})));
			}
		});
		rectActor = new RectActor(0, 0, AnotherManager.VIRTUAL_WIDTH, AnotherManager.VIRTUAL_HEIGHT);
		stage.addActor(rectActor);
		stage.addActor(newGameButton);
		stage.addActor(exitGameButton);
		stage.addActor(logoImage);
		logoImage.addAction(Actions.repeat(-1,
				Actions.sequence(Actions.moveBy(0, 15, .5f), Actions.moveBy(0, -15, .5f))));
		rectActor.setColor(0.5f, 0.5f, 1.0f, 1.0f);
		stage.addAction(Actions.alpha(0));
		stage.addAction(Actions.fadeIn(1));

		Gdx.input.setInputProcessor(stage);
	}

}
