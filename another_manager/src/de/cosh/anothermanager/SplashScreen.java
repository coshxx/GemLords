package de.cosh.anothermanager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SplashScreen implements Screen {

    private SpriteBatch batch;
    private final float fadeCompleteTime = 1f;
    private final float showCompleteTime = 3f;
    private float showTime = 0f;
    private boolean showComplete = false;
    private float fadeInTime = 0f;
    private final AnotherManager myGame;
    private Texture splashTexture;

    private OrthographicCamera camera;

    public SplashScreen(final AnotherManager myGame) {
        this.myGame = myGame;
        camera = new OrthographicCamera(myGame.VIRTUAL_WIDTH, myGame.VIRTUAL_HEIGHT);
        camera.setToOrtho(false, myGame.VIRTUAL_WIDTH, myGame.VIRTUAL_HEIGHT);
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
        if (!showComplete) {
            fadeInTime += delta;
            if (fadeInTime >= 1f) {
                fadeInTime = 1f;
                showTime += delta;
                if (showTime > showCompleteTime)
                    showComplete = true;
            }
        } else {
            fadeInTime -= delta;
            if( fadeInTime <= 0f ) {
                myGame.setScreen(myGame.gameScreen);
            }
        }
        float d = 1f - (fadeCompleteTime - fadeInTime);
        batch.setColor(d, d, d, d);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(splashTexture, 0, 0, myGame.VIRTUAL_WIDTH, myGame.VIRTUAL_HEIGHT);
        batch.end();
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
        batch = new SpriteBatch();
    }

}
