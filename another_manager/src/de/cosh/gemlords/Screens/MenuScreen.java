package de.cosh.gemlords.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import de.cosh.gemlords.GemLord;

public class MenuScreen implements Screen {

	private final GemLord myGame;
	private Stage stage;
	private Table table;

	private Skin skin;

	private TextButton newGameButton;
	private TextButton optionsButton;
	private TextButton exitGameButton;
	private TextButton creditsButton;
    private TextButton returnFromOptions;
    private TextButton continueButton;
    private Image backGround;
    private BitmapFont test;

    private boolean stageShiftingRight, stageShiftingLeft;

	public MenuScreen(final GemLord myGame) {
		this.myGame = myGame;
		table = new Table();
		skin = new Skin(Gdx.files.internal("data/ui/uiskin.json"));
        continueButton = new TextButton("Continue", skin);
		newGameButton = new TextButton("New game", skin);
		optionsButton = new TextButton("Options", skin);
		exitGameButton = new TextButton("Exit game", skin);
		creditsButton = new TextButton("Credits", skin);
        returnFromOptions = new TextButton("Back", skin);
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
		GemLord.getInstance().stageResize(width, height, stage);
	}

	@Override
	public void resume() {

	}

	@Override
	public void show() {
		this.stage = new Stage();
		GemLord.getInstance().soundPlayer.playMenuMusic();
		Gdx.input.setInputProcessor(stage);
		table.setFillParent(true);
		backGround = new Image(GemLord.getInstance().assets.get(
				"data/textures/menu.png", Texture.class));
        TextureAtlas atlas = GemLord.assets.get("data/textures/pack.atlas", TextureAtlas.class);
        Image titleImage = new Image(atlas.findRegion("title"));

        titleImage.setPosition(0, GemLord.VIRTUAL_HEIGHT-titleImage.getHeight());

		table.setBackground(backGround.getDrawable());

		addWobbleToButtons();
		titleImage.addAction(Actions.forever(Actions.sequence(
                Actions.moveBy(0, 5f, 1f), Actions.moveBy(0, -5f, 1f))));

		addButtonListeners();

		backGround.setBounds(0, 0, GemLord.VIRTUAL_WIDTH*2, GemLord.VIRTUAL_HEIGHT);

        continueButton.setBounds(265, 500, 200, 50);
        continueButton.getStyle().font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        continueButton.getStyle().font.setScale(1f);

		newGameButton.setBounds(265, 440, 200, 50);
		newGameButton.getStyle().font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		newGameButton.getStyle().font.setScale(1f);
		
		optionsButton.setBounds(265, 380, 200, 50);
		optionsButton.getStyle().font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		optionsButton.getStyle().font.setScale(1f);

        creditsButton.setBounds(265, 320, 200, 50);
        creditsButton.getStyle().font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        creditsButton.getStyle().font.setScale(1f);
        
        exitGameButton.setBounds(265, 260, 200, 50);
		exitGameButton.getStyle().font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		exitGameButton.getStyle().font.setScale(1f);

        returnFromOptions.setBounds(990, GemLord.VIRTUAL_HEIGHT - 80, 200, 80);
        returnFromOptions.getStyle().font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        returnFromOptions.getStyle().font.setScale(1f);

		stage.setCamera(GemLord.getInstance().camera);
		stage.addActor(backGround);
        stage.addActor(titleImage);
        stage.addActor(continueButton);
		stage.addActor(newGameButton);
		stage.addActor(optionsButton);
        stage.addActor(creditsButton);
		stage.addActor(exitGameButton);
        stage.addActor(returnFromOptions);
		stage.addAction(Actions.alpha(0));
		stage.addAction(Actions.fadeIn(0.5f));
	}


    private void addWobbleToButtons() {
        continueButton.addAction(Actions.forever(Actions.sequence(
                Actions.moveBy(0, 5f, 0.5f), Actions.moveBy(0, -5f, 0.5f))));

		newGameButton.addAction(Actions.forever(Actions.sequence(
				Actions.moveBy(0, 5f, 0.5f), Actions.moveBy(0, -5f, 0.5f))));

		optionsButton.addAction(Actions.forever(Actions.sequence(
				Actions.moveBy(0, 5f, 0.5f), Actions.moveBy(0, -5f, 0.5f))));

		exitGameButton.addAction(Actions.forever(Actions.sequence(
				Actions.moveBy(0, 5f, 0.5f), Actions.moveBy(0, -5f, 0.5f))));

        creditsButton.addAction(Actions.forever(Actions.sequence(
                Actions.moveBy(0, 5f, 0.5f), Actions.moveBy(0, -5f, 0.5f))));

        returnFromOptions.addAction(Actions.forever(Actions.sequence(
                Actions.moveBy(0, 5f, 0.5f), Actions.moveBy(0, -5f, 0.5f))));
	}

	private void addButtonListeners() {
		continueButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				stage.addAction(Actions.sequence(Actions.fadeOut(0.5f),
						Actions.run(new Runnable() {
							@Override
							public void run() {
								myGame.soundPlayer.stopMenuMusic();
								myGame.player.loadPreferences();
								myGame.setScreen(myGame.mapTraverseScreen);
							}
						})));
			}
		});

		
		newGameButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				stage.addAction(Actions.sequence(Actions.fadeOut(0.5f),
						Actions.run(new Runnable() {
							@Override
							public void run() {
								myGame.soundPlayer.stopMenuMusic();
                                myGame.player.clearPreferences();
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

        optionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if( !stageShiftingRight ) {
                stage.addAction(Actions.moveBy(-stage.getWidth(), 0, 0.5f));
                    stageShiftingLeft = false;
                    stageShiftingRight = true;
                }

            }
        });

        returnFromOptions.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!stageShiftingLeft) {
                    stage.addAction(Actions.moveBy(stage.getWidth(), 0, 0.5f));
                    stageShiftingLeft = true;
                    stageShiftingRight = false;
                }
            }
        });

        creditsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.addAction(Actions.sequence(Actions.fadeOut(0.5f),
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                myGame.soundPlayer.stopMenuMusic();
                                myGame.setScreen(myGame.creditScreen);
                            }
                        })));
            }
        });

	}
}
