package de.cosh.anothermanager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class SplashScreen implements Screen {
    private final AnotherManager myGame;
    private Texture splashTexture;
    private Stage stage;
    private Image splashImage;

    public SplashScreen(final AnotherManager myGame) {
        this.myGame = myGame;
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
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
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
        splashTexture = new Texture("data/splash.jpg");
        //batch = new SpriteBatch();
        stage = new Stage();
        stage.setCamera(myGame.camera);
        splashImage = new Image(splashTexture);
        splashImage.setBounds(0, 0, myGame.VIRTUAL_WIDTH, myGame.VIRTUAL_HEIGHT);
        splashImage.addAction(Actions.alpha(0));
        splashImage.addAction(Actions.sequence(Actions.fadeIn(.5f), Actions.delay(2), Actions.fadeOut(.5f), Actions.run(new Runnable() {
            @Override
            public void run() {
                myGame.setScreen(myGame.menuScreen);
            }
        })));
        stage.addActor(splashImage);
    }

}
