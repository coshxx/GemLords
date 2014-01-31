package de.cosh.anothermanager.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;

import de.cosh.anothermanager.AnotherManager;

public class MenuScreen implements Screen {

	private final AnotherManager myGame;
	private Stage stage;
	private Table table;

	private Skin skin;

	private TextButton newGameButton;
	private TextButton optionsButton;
	private TextButton exitGameButton;
	
	private Vector2 crop;


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
		//Table.drawDebug(stage);
	}

	@Override
	public void resize(final int width, final int height) {
		stage.setViewport(width, height, false);
	}

	@Override
	public void resume() {

	}

	@Override
	public void show() {
		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();
		float buttonWidth = width * 0.3f;
		float buttonHeight = height * 0.05f;
		this.stage = new Stage();
		AnotherManager.getInstance().soundPlayer.playMenuMusic();
		Gdx.input.setInputProcessor(stage);
		table.setFillParent(true);
		Image backGround = new Image(AnotherManager.getInstance().assets.get("data/textures/menu.png", Texture.class));
		table.setBackground(backGround.getDrawable());
		//table.debug();

		addWobbleToButtons();
		
		table.bottom().pad(Gdx.graphics.getHeight() * 0.15f);
		table.add(newGameButton).pad(height*0.02f).height(buttonHeight).width(buttonWidth);
		table.row();
		table.add(optionsButton).pad(height*0.02f).height(buttonHeight).width(buttonWidth);
		table.row();
		table.add(exitGameButton).pad(height*0.02f).height(buttonHeight).width(buttonWidth);

		addButtonListeners();

		stage.addAction(Actions.alpha(0));
		stage.addAction(Actions.fadeIn(0.5f));
		stage.addActor(table);
	}

	private void addWobbleToButtons() {
		newGameButton.addAction(Actions.forever(
				Actions.sequence(
						Actions.moveBy(0, 5f, 0.5f),
						Actions.moveBy(0, -5f, 0.5f)
						)));
		
		optionsButton.addAction(Actions.forever(
				Actions.sequence(
						Actions.moveBy(0, 5f, 0.5f),
						Actions.moveBy(0, -5f, 0.5f)
						)));
		
		exitGameButton.addAction(Actions.forever(
				Actions.sequence(
						Actions.moveBy(0, 5f, 0.5f),
						Actions.moveBy(0, -5f, 0.5f)
						)));
	}

	private void addButtonListeners() {
		newGameButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				stage.addAction(Actions.sequence(
						Actions.fadeOut(0.5f),
						Actions.run(new Runnable() {
							@Override
							public void run() {
								myGame.soundPlayer.stopMenuMusic();
								myGame.setScreen(myGame.mapTraverseScreen);
							}})));
			}});

		exitGameButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				stage.addAction(Actions.sequence(
						Actions.fadeOut(0.5f),
						Actions.run(new Runnable() {
							@Override
							public void run() {
								Gdx.app.exit();
							}})));
			}});
	}
}
