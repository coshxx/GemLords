package de.cosh.anothermanager.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import de.cosh.anothermanager.AnotherManager;

/**
 * Created by cosh on 10.01.14.
 */
public class GUIWindow {
	private final Label.LabelStyle labelStyle;
	private Table root;
	private final boolean showingMapEnemyWindow;
	private final Stage stage;
	private final TextButton.TextButtonStyle textButtonStyle;
	private final Window.WindowStyle windowStyle;
    private AnotherManager myGame;

	public GUIWindow( final Stage stage) {
		this.stage = stage;
        this.myGame = AnotherManager.getInstance();
		showingMapEnemyWindow = false;

		windowStyle = new Window.WindowStyle();
		windowStyle.titleFont = AnotherManager.assets.get("data/font.fnt", BitmapFont.class);
		windowStyle.titleFont.setScale(1f);
		final Texture nTexture = new Texture("data/menuskin.png");
		final NinePatch nPatch = new NinePatch(new TextureRegion(nTexture, 24, 24), 8, 8, 8, 8);
		windowStyle.background = new NinePatchDrawable(nPatch);

		textButtonStyle = new TextButton.TextButtonStyle();
		final Texture buttonTexture = AnotherManager.assets.get("data/button.png", Texture.class);
		final BitmapFont buttonFont = AnotherManager.assets.get("data/font.fnt", BitmapFont.class);
		textButtonStyle.up = new TextureRegionDrawable(new TextureRegion(buttonTexture));
		textButtonStyle.down = new TextureRegionDrawable(new TextureRegion(buttonTexture));
		textButtonStyle.font = buttonFont;

		labelStyle = new Label.LabelStyle();
		labelStyle.font = AnotherManager.assets.get("data/font.fnt", BitmapFont.class);
	}

	public void createDefeatWindow(final Group foreGround, final Group backGround, final Group windowGroup) {
		final Window window = new Window("You lose", windowStyle);
		window.setPosition(200, 200);
		window.setColor(1.0f, 1.0f, 1.0f, 0.95f);
		window.setMovable(true);
		window.pad(20, 0, 0, 0);
		window.setModal(false);
		window.align(Align.center);
		window.setTitleAlignment(Align.center);
		window.setSize(200, 200);

		TextButton button;
		button = new TextButton("Map", textButtonStyle);
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
		final Window window = new Window("You win!", windowStyle);
		window.setPosition(200, 200);
		window.setColor(1.0f, 1.0f, 1.0f, 0.95f);
		window.setMovable(true);
		window.pad(20, 0, 0, 0);
		window.setModal(false);
		window.align(Align.center);
		window.setTitleAlignment(Align.center);
		window.setSize(200, 200);

		TextButton button;
		button = new TextButton("Loot", textButtonStyle);
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
		root.addAction(Actions.moveBy(600, 0, 0.25f));
		stage.addAction(Actions.sequence(Actions.fadeOut(1f), Actions.run(new Runnable() {
			@Override
			public void run() {
				myGame.setScreen(myGame.gameScreen);
			}
		})));
	}

	private void fadeToLootScreen() {
		stage.addAction(Actions.sequence(Actions.fadeOut(1f), Actions.run(new Runnable() {
			@Override
			public void run() {
				myGame.setScreen(myGame.lootScreen);
			}
		})));
	}

	private void fadeToMapScreen() {
		stage.addAction(Actions.sequence(Actions.fadeOut(1f), Actions.run(new Runnable() {
			@Override
			public void run() {
				myGame.setScreen(myGame.mapTraverseScreen);
			}
		})));
	}

	private void hideEnemyWindow() {
		root.addAction(Actions.moveBy(600, 0, 0.25f));
		myGame.mapTraverseScreen.enemyWindowOpen = false;
	}

	public void showMapEnemyWindow(final int enemyHP, final Image enemyImage) {
		if (!myGame.mapTraverseScreen.enemyWindowOpen) {
			myGame.mapTraverseScreen.enemyWindowOpen = true;
			final Window window = new Window("Challenge:", windowStyle);
			window.setPosition(0, 0);
			window.setColor(1.0f, 1.0f, 1.0f, 0.95f);
			window.pad(20, 0, 0, 0);
			window.align(Align.center);
			window.setMovable(false);
			window.setTitleAlignment(Align.center | Align.top);
			window.setSize(250, 250);

			final Table t = new Table();
			t.setBounds(0, 0, 250, 250);
			t.setLayoutEnabled(true);
			window.add(t);

			enemyImage.setPosition(0, 0);
			enemyImage.setAlign(Align.center);
			t.add(enemyImage);
			t.row();

			final Label enemyInfoLabel = new Label(enemyHP + " HP", labelStyle);
			t.add(enemyInfoLabel);
			t.row();

			TextButton fightButton;
			fightButton = new TextButton("Fight", textButtonStyle);
			fightButton.setPosition(0, 0);
			t.add(fightButton);
			t.row();
			Gdx.input.setInputProcessor(stage);
			fightButton.addListener(new ClickListener() {
				@Override
				public void clicked(final InputEvent event, final float x, final float y) {
					fadeToGameScreen();
				}
			});

			TextButton cancelButton;
			cancelButton = new TextButton("Cancel", textButtonStyle);
			cancelButton.setPosition(0, 0);
			cancelButton.align(Align.bottom);
			cancelButton.setSize(50, 50);
			t.add(cancelButton);
			t.row();
			cancelButton.addListener(new ClickListener() {
				@Override
				public void clicked(final InputEvent event, final float x, final float y) {
					hideEnemyWindow();
				}
			});

			root = new Table();
			root.setBounds(0, 0, myGame.VIRTUAL_WIDTH, myGame.VIRTUAL_HEIGHT);
			root.align(Align.center);
			root.add(window);
			stage.addActor(root);
			root.setPosition(-500, 0);
			root.addAction(Actions.moveBy(500, 0, 0.25f));
		}
	}
}
