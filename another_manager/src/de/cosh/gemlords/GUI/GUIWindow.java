package de.cosh.gemlords.GUI;


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

import de.cosh.gemlords.CustomStyle;
import de.cosh.gemlords.GemLord;
import de.cosh.gemlords.LanguageManager;

/**
 * Created by cosh on 10.01.14.
 */
public class GUIWindow {
	private Skin skin;
	private final Stage stage;
	private GemLord myGame;
	
	private Window window;

	public GUIWindow( final Stage stage) {
		this.stage = stage;
		this.myGame = GemLord.getInstance();
        skin = GemLord.assets.get("data/ui/uiskin.json", Skin.class);
	}

	public void createDefeatWindow(final Group foreGround, final Group backGround, final Group windowGroup) {
        LanguageManager lm = LanguageManager.getInstance();
		final Window window = new Window(lm.getString("You lose"), skin);
		window.setPosition(200, 200);
		window.setColor(1.0f, 1.0f, 1.0f, 0.95f);
		window.setMovable(true);
		window.pad(20, 0, 0, 0);
		window.setModal(false);
		window.align(Align.center);
		window.setTitleAlignment(Align.center);
		window.setSize(200, 200);

		TextButton button;
		button = new TextButton(lm.getString("Map"), skin);
        button.setStyle(CustomStyle.getInstance().getTextButtonStyle());
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
        LanguageManager lm = LanguageManager.getInstance();
		final Window window = new Window(lm.getString("You win"), skin);
		window.setPosition(200, 200);
		window.setColor(1.0f, 1.0f, 1.0f, 0.95f);
		window.setMovable(true);
		window.setModal(false);
		window.align(Align.center);
		window.setTitleAlignment(Align.center);
		window.setSize(200, 200);

		TextButton button;
		button = new TextButton(lm.getString("Continue"), skin);
        button.setStyle(CustomStyle.getInstance().getTextButtonStyle());
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
		myGame.episode1TraverseScreen.enemyWindowOpen = false;
		myGame.episode1TraverseScreen.fadeMusic();
		window.addAction(Actions.moveBy(600, 0, 0.25f));
		stage.addAction(Actions.sequence(Actions.fadeOut(1f), Actions.run(new Runnable() {
			@Override
			public void run() {
		myGame.setScreen(myGame.gameScreen);
			}
		})));
	}

	private void fadeToLootScreen() {
        myGame.episode1TraverseScreen.enemyWindowOpen = false;
        myGame.episode2TraverseScreen.enemyWindowOpen = false;
		stage.addAction(Actions.sequence(Actions.fadeOut(1f), Actions.run(new Runnable() {
			@Override
			public void run() {
				myGame.setScreen(myGame.lootScreen);
			}
		})));
	}

	private void fadeToMapScreen() {
        myGame.episode1TraverseScreen.enemyWindowOpen = false;
        myGame.episode2TraverseScreen.enemyWindowOpen = false;
		stage.addAction(Actions.sequence(Actions.fadeOut(1f), Actions.run(new Runnable() {
			@Override
			public void run() {
				myGame.setScreen(myGame.player.getCurrentEpisodeScreen());
			}
		})));
	}

	private void hideEnemyWindow() {
		window.addAction(Actions.moveBy(800, 0, 0.35f));
		myGame.episode1TraverseScreen.enemyWindowOpen = false;
	}

	public void showMapEnemyWindow(int enemyHP, Image enemyImage, String enemyName) {
		if (!myGame.episode1TraverseScreen.enemyWindowOpen) {
			myGame.episode1TraverseScreen.enemyWindowOpen = true;

            LanguageManager lm = LanguageManager.getInstance();
			window = new Window(lm.getString("Challenge:"), skin);
			window.setKeepWithinStage(false);
			window.setBounds(-400, 400, 300, 550);
			window.setMovable(false);
			window.getStyle().titleFont.setScale(1f);

			window.add(enemyImage).size(250, 250);
			window.row();

            Label enemyNameLabel = new Label(lm.getString(enemyName), skin);
            enemyNameLabel.setFontScale(1f);
            window.add(enemyNameLabel);
            window.row();

			skin.getFont("default-font").getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			skin.getFont("default-font").setScale(1f);
			Label hpLabel = new Label(enemyHP + " HP", skin);
			hpLabel.setFontScale(1f);
			window.add(hpLabel);
			window.row();

			TextButton fightButton = new TextButton(lm.getString("Fight"), skin);
            fightButton.setStyle(CustomStyle.getInstance().getTextButtonStyle());
			fightButton.getLabel().setFontScale(1f);
			window.add(fightButton).size(250, 75);
			window.row();
			TextButton cancelButton = new TextButton(lm.getString("Cancel"), skin);
            cancelButton.setStyle(CustomStyle.getInstance().getTextButtonStyle());
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

    public void createAskGiveUpWindow(final Group foreGround, final Group backGround, final Group effectGroup) {
        LanguageManager lm = LanguageManager.getInstance();
        final Window window = new Window(lm.getString("Give up?"), skin);
        window.setPosition(200, 200);
        window.setColor(1.0f, 1.0f, 1.0f, 0.95f);
        window.setMovable(true);
        window.pad(20, 0, 0, 0);
        window.setModal(false);
        window.align(Align.center);
        window.setTitleAlignment(Align.center);
        window.setSize(300, 300);

        TextButton buttonYes;
        buttonYes = new TextButton(lm.getString("Yes"), skin);
        buttonYes.setPosition(00, 50);
        buttonYes.setSize(150, 100);

        buttonYes.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, final float x, final float y) {
                GemLord.soundPlayer.stopGameMusic();
                fadeToMapScreen();
            }
        });
        window.addActor(buttonYes);


        TextButton buttonNo;
        buttonNo = new TextButton(lm.getString("No"), skin);
        buttonNo.setPosition(150, 50);
        buttonNo.setSize(150, 100);
        buttonNo.addListener(new ClickListener() {
            public void clicked(InputEvent event, final float x, final float y) {
                window.remove();
                backGround.addAction(Actions.alpha(1f, 1.0f));
                foreGround.addAction(Actions.alpha(1f, 1.0f));
                GemLord.getInstance().gameScreen.getBoard().removedForeGroundWindow();
            }
        });
        window.addActor(buttonNo);
        Gdx.input.setInputProcessor(stage);
        backGround.addAction(Actions.alpha(0.5f, 1.0f));
        foreGround.addAction(Actions.alpha(0.5f, 1.0f));
        effectGroup.addActor(window);
    }

    public void createBuyFinalWindow() {
        LanguageManager lm = LanguageManager.getInstance();
        final Window window = new Window(lm.getString("Buy the full version"), skin);
        window.getStyle().titleFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        window.setColor(1.0f, 1.0f, 1.0f, 0.95f);
        window.setMovable(true);
        window.setModal(false);
        window.setSize(600, 600);
        window.setPosition(60, 340);

        Label label1 = new Label(lm.getString("Thank you for playing Gem Lords.\nIf you enjoyed this game,\nplease consider buying\nthe full version. This will allow me\nto keep on developing games and\nwill allow you to keep on playing\nand also remove all ads\nDo you wish to buy the game now?\nFull version gets you:\n*No ads\n*What we have of Episode 2 so far"), skin);
        label1.getStyle().font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        window.add(label1).expand().top();
        window.row();
        TextButton buttonBuy = new TextButton(lm.getString("Buy now"), skin);
        buttonBuy.setStyle(CustomStyle.getInstance().getTextButtonStyle());

        buttonBuy.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
            }
        });

        window.add(buttonBuy).size(150, 100).left();
        TextButton buttonCancel = new TextButton(lm.getString("Cancel"), skin);
        buttonCancel.setStyle(CustomStyle.getInstance().getTextButtonStyle());

        buttonCancel.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                GemLord.getInstance().setScreen(GemLord.getInstance().menuScreen);
            }
        });

        window.add(buttonCancel).size(150, 100);
        window.pack();


        Gdx.input.setInputProcessor(stage);
        stage.addActor(window);
    }
}
