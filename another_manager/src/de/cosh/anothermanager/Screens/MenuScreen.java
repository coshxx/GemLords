package de.cosh.anothermanager.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
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


	public MenuScreen(final AnotherManager myGame) {
		this.myGame = myGame;
		this.stage = new Stage();
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

	}

	@Override
	public void resume() {

	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		table.setFillParent(true);
		//table.debug();
		table.bottom();
		table.add(newGameButton).pad(20).height(75).width(200);
		table.row();
		table.add(optionsButton).pad(20).height(75).width(200);
		table.row();
		table.add(exitGameButton).pad(20).height(75).width(200);

		addButtonListeners();

		stage.addAction(Actions.alpha(0));
		stage.addAction(Actions.fadeIn(0.5f));
		stage.addActor(table);
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
