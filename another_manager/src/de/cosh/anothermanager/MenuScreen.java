package de.cosh.anothermanager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MenuScreen implements Screen {
    private AnotherManager myGame;

    private Texture logoTexture;
    private Vector2 logoPosition;

    private Image logoImage;
    private Stage stage;
    private TextButton newGameButton;
    private TextButton exitGameButton;
    private RectActor rectActor;


    public MenuScreen(final AnotherManager myGame) {
        this.myGame = myGame;

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
        logoTexture = myGame.assets.get("data/logo.png", Texture.class);
        Texture buttonTexture = myGame.assets.get("data/button.png", Texture.class);

        logoPosition = new Vector2();
        logoPosition.x = (myGame.VIRTUAL_WIDTH / 2) - (logoTexture.getWidth() / 2);
        logoPosition.y = myGame.VIRTUAL_HEIGHT - (logoTexture.getHeight() * 1.1f);

        logoImage = new Image(logoTexture);
        logoImage.setPosition(logoPosition.x, logoPosition.y);

        stage = new Stage();
        stage.setCamera(myGame.camera);

        TextureRegion upRegion = new TextureRegion(buttonTexture);
        TextureRegion downRegion = new TextureRegion(buttonTexture);
        BitmapFont buttonFont = new BitmapFont();

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = new TextureRegionDrawable(upRegion);
        style.down = new TextureRegionDrawable(downRegion);
        style.font = buttonFont;

        newGameButton = new TextButton("New Game", style);
        newGameButton.setPosition(logoPosition.x, 400);
        newGameButton.addListener(new ClickListener() {
            public void clicked (com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                myGame.soundPlayer.PlayBlub1();
                stage.addAction(Actions.sequence(Actions.fadeOut(.25f), Actions.delay(.25f), Actions.run(new Runnable() {
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
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                myGame.soundPlayer.PlayBlub2();
                stage.addAction(Actions.sequence(Actions.fadeOut(.25f), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        Gdx.app.exit();
                    }
                })));
            }
        });
        rectActor = new RectActor(0, 0, myGame.VIRTUAL_WIDTH, myGame.VIRTUAL_HEIGHT);
        stage.addActor(rectActor);
        stage.addActor(newGameButton);
        stage.addActor(exitGameButton);
        stage.addActor(logoImage);
        logoImage.addAction(Actions.repeat(-1, Actions.sequence(Actions.moveBy(0, 15, .5f), Actions.moveBy(0, -15, .5f))));
        rectActor.setColor(0.5f, 0.5f, 1.0f, 1.0f);
        stage.addAction(Actions.alpha(0));
        stage.addAction(Actions.fadeIn(1));

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        stage.dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

}
