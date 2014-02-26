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
import de.cosh.gemlords.CustomStyle;
import de.cosh.gemlords.GemLord;
import de.cosh.gemlords.LanguageManager;

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
    private TextButton buyGameButton;
    private Image backGround;
    private BitmapFont test;


    private boolean stageShiftingRight, stageShiftingLeft;

    public MenuScreen(final GemLord myGame) {
        this.myGame = myGame;
        table = new Table();
        skin = new Skin(Gdx.files.internal("data/ui/uiskin.json"));
        LanguageManager lm = LanguageManager.getInstance();
        continueButton = new TextButton(lm.getString("Continue"), skin);
        newGameButton = new TextButton(lm.getString("New Game"), skin);
        optionsButton = new TextButton(lm.getString("Options"), skin);
        exitGameButton = new TextButton(lm.getString("Exit Game"), skin);
        creditsButton = new TextButton(lm.getString("Credits"), skin);
        returnFromOptions = new TextButton(lm.getString("Back"), skin);
        buyGameButton = new TextButton(lm.getString("Remove Ads"), skin);
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
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();

        if (GemLord.getInstance().requestHandler.isFullVersionUser()) {
            if (buyGameButton != null) {
                buyGameButton.remove();
                buyGameButton = null;
            }
            GemLord.getInstance().requestHandler.showAds(false);
        }
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

        titleImage.setPosition(0, GemLord.VIRTUAL_HEIGHT - titleImage.getHeight());

        table.setBackground(backGround.getDrawable());

        addWobbleToButtons();
        titleImage.addAction(Actions.forever(Actions.sequence(
                Actions.moveBy(0, 5f, 1f), Actions.moveBy(0, -5f, 1f))));

        addButtonListeners();

        backGround.setBounds(0, 0, GemLord.VIRTUAL_WIDTH * 2, GemLord.VIRTUAL_HEIGHT);

        continueButton.setStyle(CustomStyle.getInstance().getTextButtonStyle());
        newGameButton.setStyle(CustomStyle.getInstance().getTextButtonStyle());
        optionsButton.setStyle(CustomStyle.getInstance().getTextButtonStyle());
        creditsButton.setStyle(CustomStyle.getInstance().getTextButtonStyle());
        exitGameButton.setStyle(CustomStyle.getInstance().getTextButtonStyle());

        if (buyGameButton != null) {
            buyGameButton.setStyle(CustomStyle.getInstance().getTextButtonStyle());
        }
        returnFromOptions.setStyle(CustomStyle.getInstance().getTextButtonStyle());

        continueButton.setBounds(265, 500, 200, 50);
        continueButton.getStyle().font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

        newGameButton.setBounds(265, 440, 200, 50);
        newGameButton.getStyle().font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

        optionsButton.setBounds(265, 380, 200, 50);
        optionsButton.getStyle().font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

        creditsButton.setBounds(265, 320, 200, 50);
        creditsButton.getStyle().font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

        exitGameButton.setBounds(265, 260, 200, 50);
        exitGameButton.getStyle().font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

        if (buyGameButton != null) {
            buyGameButton.setBounds(265, 180, 200, 50);
            buyGameButton.getStyle().font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        }

        returnFromOptions.setBounds(990, 260, 200, 50);
        returnFromOptions.getStyle().font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        returnFromOptions.getStyle().font.setScale(1f);

        stage.setCamera(GemLord.getInstance().camera);
        stage.addActor(backGround);
        stage.addActor(titleImage);
        if (GemLord.getInstance().player.existsPreferences()) {
            stage.addActor(continueButton);
        }
        stage.addActor(newGameButton);
        stage.addActor(optionsButton);
        stage.addActor(creditsButton);
        stage.addActor(exitGameButton);
        if (GemLord.getInstance().requestHandler.googleConnectionEstablished()) {
            if (!GemLord.getInstance().player.hasFullVersion()) {
                GemLord.getInstance().requestHandler.showAds(true);
                stage.addActor(buyGameButton);
            }
        }
        stage.addActor(returnFromOptions);
        stage.addAction(Actions.alpha(0));
        stage.addAction(Actions.fadeIn(0.5f));
    }


    private void addWobbleToButtons() {

        continueButton.clearActions();
        newGameButton.clearActions();
        optionsButton.clearActions();
        exitGameButton.clearActions();
        returnFromOptions.clearActions();
        creditsButton.clearActions();

        if (buyGameButton != null)
            buyGameButton.clearActions();

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

        if (buyGameButton != null) {
            buyGameButton.addAction(Actions.forever(Actions.sequence(
                    Actions.moveBy(0, 5f, 0.5f), Actions.moveBy(0, -5f, 0.5f))));
        }
    }

    private void addButtonListeners() {

        continueButton.clearListeners();
        newGameButton.clearListeners();
        exitGameButton.clearListeners();
        optionsButton.clearListeners();
        returnFromOptions.clearListeners();
        creditsButton.clearListeners();
        if (buyGameButton != null)
            buyGameButton.clearListeners();

        continueButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.addAction(Actions.sequence(Actions.fadeOut(0.5f),
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                myGame.soundPlayer.stopMenuMusic();
                                myGame.player.loadPreferences();
                                myGame.setScreen(myGame.episode1TraverseScreen);
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
                                myGame.setScreen(myGame.episode1IntroScreen);
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
                if (!stageShiftingRight) {
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

        if (buyGameButton != null) {
            buyGameButton.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    GemLord.getInstance().requestHandler.showRemoveAdsInAppPurchaseWindow();
                }
            });
        }

    }
}
