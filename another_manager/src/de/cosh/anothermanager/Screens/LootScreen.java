package de.cosh.anothermanager.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import de.cosh.anothermanager.AnotherManager;

/**
 * Created by cosh on 07.01.14.
 */
public class LootScreen implements Screen {
	private Image chestImage;
	private final AnotherManager myGame;

	private Stage stage;

	public LootScreen(final AnotherManager anotherManager) {
		myGame = anotherManager;
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
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1f);
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
		stage = new Stage();
		stage.setCamera(myGame.camera);

		chestImage = new Image(myGame.assets.get("data/treasure.jpg", Texture.class));
		chestImage.setPosition(myGame.VIRTUAL_WIDTH / 2 - 200, myGame.VIRTUAL_HEIGHT / 2);
		stage.addActor(chestImage);

		stage.addAction(Actions.alpha(0.0f));
		stage.addAction(Actions.fadeIn(1.0f));

		myGame.soundPlayer.playLootMusic();

		TextButton button;
		TextButton.TextButtonStyle tStyle;
		tStyle = new TextButton.TextButtonStyle();
		final Texture buttonTexture = myGame.assets.get("data/button.png", Texture.class);
		final BitmapFont buttonFont = new BitmapFont();
		tStyle.up = new TextureRegionDrawable(new TextureRegion(buttonTexture));
		tStyle.down = new TextureRegionDrawable(new TextureRegion(buttonTexture));
		tStyle.font = buttonFont;

		button = new TextButton("Return to map", tStyle);
		button.setPosition(50, 50);
		button.setSize(100, 100);

		button.addListener(new ClickListener() {
			@Override
			public void clicked(final InputEvent event, final float x, final float y) {
				stage.addAction(Actions.sequence(Actions.fadeOut(0.5f), Actions.run(new Runnable() {
					@Override
					public void run() {
						myGame.soundPlayer.stopLootMusic();
						myGame.player.levelDone();
						myGame.setScreen(myGame.mapTraverseScreen);
					}
				})));
			}
		});
		stage.addActor(button);
		Gdx.input.setInputProcessor(stage);
	}
}
