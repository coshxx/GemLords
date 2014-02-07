package de.cosh.anothermanager.GUI;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import de.cosh.anothermanager.AnotherManager;

/**
 * Created by cosh on 10.01.14.
 */
public class GUIWindow {
	private Skin skin;
	private final Stage stage;
	private AnotherManager myGame;
	
	private Window window;

	public GUIWindow( final Stage stage) {
		this.stage = stage;
		this.myGame = AnotherManager.getInstance();
		skin = new Skin(Gdx.files.internal("data/ui/uiskin.json"));
	}

	public void createDefeatWindow(final Group foreGround, final Group backGround, final Group windowGroup) {
		final Window window = new Window("You lose", skin);
		window.setPosition(200, 200);
		window.setColor(1.0f, 1.0f, 1.0f, 0.95f);
		window.setMovable(true);
		window.pad(20, 0, 0, 0);
		window.setModal(false);
		window.align(Align.center);
		window.setTitleAlignment(Align.center);
		window.setSize(200, 200);

		TextButton button;
		button = new TextButton("Map", skin);
		button.setPosition(50, 50);
		button.setSize(100, 100);
		Gdx.input.setInputProcessor(stage);
		button.addListener(new ClickListener() {
			@Override
			public void clicked(final InputEvent event, final float x, final float y) {
				fadeToMapScreen();
			}
		});
		window.addActor(button);
		backGround.addAction(Actions.alpha(0.5f, 1.0f));
		foreGround.addAction(Actions.alpha(0.5f, 1.0f));
		windowGroup.addActor(window);
	}

	public void createVictoryWindow(final Group foreGround, final Group backGround, final Group windowGroup) {
		final Window window = new Window("You win!", skin);
		window.setPosition(200, 200);
		window.setColor(1.0f, 1.0f, 1.0f, 0.95f);
		window.setMovable(true);
		window.setModal(false);
		window.align(Align.center);
		window.setTitleAlignment(Align.center);
		window.setSize(200, 200);

		TextButton button;
		button = new TextButton("Continue", skin);
		button.setPosition(50, 50);
		button.setSize(100, 100);
		Gdx.input.setInputProcessor(stage);
		button.addListener(new ClickListener() {
			@Override
			public void clicked(final InputEvent event, final float x, final float y) {
				fadeToLootScreen();
			}
		});
		window.addActor(button);
		backGround.addAction(Actions.alpha(0.5f, 1.0f));
		foreGround.addAction(Actions.alpha(0.5f, 1.0f));
		windowGroup.addActor(window);
	}

	private void fadeToGameScreen() {
		myGame.mapTraverseScreen.enemyWindowOpen = false;
		myGame.mapTraverseScreen.fadeMusic();
		window.addAction(Actions.moveBy(600, 0, 0.25f));
		stage.addAction(Actions.sequence(Actions.fadeOut(1f), Actions.run(new Runnable() {
			@Override
			public void run() {
		myGame.setScreen(myGame.gameScreen);
			}
		})));
	}

	private void fadeToLootScreen() {
        myGame.mapTraverseScreen.enemyWindowOpen = false;
		stage.addAction(Actions.sequence(Actions.fadeOut(1f), Actions.run(new Runnable() {
			@Override
			public void run() {
				myGame.setScreen(myGame.lootScreen);
			}
		})));
	}

	private void fadeToMapScreen() {
        myGame.mapTraverseScreen.enemyWindowOpen = false;
		stage.addAction(Actions.sequence(Actions.fadeOut(1f), Actions.run(new Runnable() {
			@Override
			public void run() {
				myGame.setScreen(myGame.mapTraverseScreen);
			}
		})));
	}

	private void hideEnemyWindow() {
		window.addAction(Actions.moveBy(800, 0, 0.35f));
		myGame.mapTraverseScreen.enemyWindowOpen = false;
	}

	public void showMapEnemyWindow(int enemyHP, Image enemyImage, String enemyName) {
		if (!myGame.mapTraverseScreen.enemyWindowOpen) {
			myGame.mapTraverseScreen.enemyWindowOpen = true;

			window = new Window("Challenge:", skin);
			window.setKeepWithinStage(false);
			window.setBounds(-400, 400, 300, 550);
			window.setMovable(false);
			window.getStyle().titleFont.setScale(1f);

			window.add(enemyImage).size(250, 250);
			window.row();

            Label enemyNameLabel = new Label(enemyName, skin);
            enemyNameLabel.setFontScale(1f);
            window.add(enemyNameLabel);
            window.row();

			skin.getFont("default-font").getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			skin.getFont("default-font").setScale(1f);
			Label hpLabel = new Label(enemyHP + " HP", skin);
			hpLabel.setFontScale(1f);
			window.add(hpLabel);
			window.row();

			TextButton fightButton = new TextButton("Fight", skin);
			fightButton.getLabel().setFontScale(1f);
			window.add(fightButton).size(250, 75);
			window.row();
			TextButton cancelButton = new TextButton("Cancel", skin);
			cancelButton.getLabel().setFontScale(1f);
			window.add(cancelButton).size(250, 75);

			window.addAction(Actions.moveBy(600, 0, 0.25f));
			stage.addActor(window);

			fightButton.addListener(new ClickListener() {
				@Override
				public void clicked(final InputEvent event, final float x, final float y) {
					fadeToGameScreen();
				}
			});

			cancelButton.addListener(new ClickListener() {
				@Override
				public void clicked(final InputEvent event, final float x, final float y) {
					hideEnemyWindow();
				}
			});
		}
	}
}
