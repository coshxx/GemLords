package de.cosh.anothermanager.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import de.cosh.anothermanager.AnotherManager;

public class MenuScreen implements Screen {

	private final AnotherManager myGame;
	private Stage stage;
	private Table table;

	private Skin skin;

	private TextButton newGameButton;
	private TextButton optionsButton;
	private TextButton exitGameButton;

    private BitmapFont test;

	public MenuScreen(final AnotherManager myGame) {
		this.myGame = myGame;
		table = new Table();
		skin = new Skin(Gdx.files.internal("data/ui/uiskin.json"));
		newGameButton = new TextButton("New game", skin);
		optionsButton = new TextButton("Options", skin);
		exitGameButton = new TextButton("Exit game", skin);
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
		Table.drawDebug(stage);
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
		this.stage = new Stage();
		AnotherManager.getInstance().soundPlayer.playMenuMusic();
		Gdx.input.setInputProcessor(stage);
		table.setFillParent(true);
		Image backGround = new Image(AnotherManager.getInstance().assets.get(
				"data/textures/menu.png", Texture.class));
		table.setBackground(backGround.getDrawable());
		table.debug();

		addWobbleToButtons();
		
		addButtonListeners();

		backGround.setBounds(0, 0, AnotherManager.VIRTUAL_WIDTH, AnotherManager.VIRTUAL_HEIGHT);
		newGameButton.setBounds(265, 450, 200, 80);
		newGameButton.getStyle().font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		newGameButton.getStyle().font.setScale(1f);
		
		optionsButton.setBounds(265, 350, 200, 80);
		optionsButton.getStyle().font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		optionsButton.getStyle().font.setScale(1f);
		
		exitGameButton.setBounds(265, 250, 200, 80);
		exitGameButton.getStyle().font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		exitGameButton.getStyle().font.setScale(1f);
		
		stage.setCamera(AnotherManager.getInstance().camera);
		stage.addActor(backGround);
		stage.addActor(newGameButton);
		stage.addActor(optionsButton);
		stage.addActor(exitGameButton);
		stage.addAction(Actions.alpha(0));
		stage.addAction(Actions.fadeIn(0.5f));
	}

	private void addWobbleToButtons() {
		newGameButton.addAction(Actions.forever(Actions.sequence(
				Actions.moveBy(0, 5f, 0.5f), Actions.moveBy(0, -5f, 0.5f))));

		optionsButton.addAction(Actions.forever(Actions.sequence(
				Actions.moveBy(0, 5f, 0.5f), Actions.moveBy(0, -5f, 0.5f))));

		exitGameButton.addAction(Actions.forever(Actions.sequence(
				Actions.moveBy(0, 5f, 0.5f), Actions.moveBy(0, -5f, 0.5f))));
	}

	private void addButtonListeners() {
		newGameButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				stage.addAction(Actions.sequence(Actions.fadeOut(0.5f),
						Actions.run(new Runnable() {
							@Override
							public void run() {
								myGame.soundPlayer.stopMenuMusic();
								myGame.setScreen(myGame.mapTraverseScreen);
							}
						})));
			}
		});

		exitGameButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				stage.addAction(Actions.sequence(Actions.fadeOut(0.5f),
						Actions.run(new Runnable() {
							@Override
							public void run() {
								Gdx.app.exit();
							}
						})));
			}
		});
	}
}
