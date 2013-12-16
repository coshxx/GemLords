package de.cosh.anothermanager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GameScreen implements Screen, GestureListener {
    private final AnotherManager myGame;
    private Stage stage;
    private SwapGame swapGame;
    private Vector2 flingStartPosition;

    public GameScreen(final AnotherManager myGame) {
        this.myGame = myGame;
        flingStartPosition = new Vector2();
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
        Gdx.input.setInputProcessor(new GestureDetector(this));
        swapGame = new SwapGame(myGame);
        swapGame.init();
        stage.addActor(swapGame);
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

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        flingStartPosition.set(x, y);
        stage.screenToStageCoordinates(flingStartPosition);
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        if (Math.abs(velocityX) > Math.abs(velocityY)) {
            if (velocityX > 0) {
                swapGame.swapToTheRight(flingStartPosition);
            } else {
                swapGame.swapToTheLeft(flingStartPosition);
            }
        } else {
            if (velocityY > 0) {
                swapGame.swapToTheBottom(flingStartPosition);
            } else {
                swapGame.swapToTheTop(flingStartPosition);
            }
        }
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }
}
