package de.cosh.anothermanager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class GameScreen implements Screen {
    private final AnotherManager myGame;
    private Stage stage;
    private Texture redBall, greenBall, blueBall, purpleBall, whiteBall, yellowBall;


    public GameScreen(final AnotherManager myGame) {
        this.myGame = myGame;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
        stage = new Stage();
        stage.setCamera(myGame.camera);
        redBall = myGame.assets.get("data/ball_red.png", Texture.class);
        greenBall = myGame.assets.get("data/ball_green.png", Texture.class);
        blueBall = myGame.assets.get("data/ball_blue.png", Texture.class);
        purpleBall = myGame.assets.get("data/ball_purple.png", Texture.class);
        whiteBall = myGame.assets.get("data/ball_white.png", Texture.class);
        yellowBall = myGame.assets.get("data/ball_yellow.png", Texture.class);

        for (int x = 0; x < myGame.VIRTUAL_WIDTH; x += 48 ) {
            Image a = new Image(redBall);
            a.setBounds(x, 200, 48, 48);
            stage.addActor(a);
        }
    }

    @Override
    public void hide() {

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
