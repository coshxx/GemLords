package de.cosh.anothermanager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;

public class GameScreen implements Screen, GestureListener {
    private final AnotherManager myGame;
    private Stage stage;
    private Board swapGame;
    private Vector2 flingStartPosition;

    public GameScreen(final AnotherManager myGame) {
        this.myGame = myGame;
        flingStartPosition = new Vector2();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        swapGame.update(delta);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.setViewport(myGame.VIRTUAL_WIDTH, myGame.VIRTUAL_HEIGHT, false);
    }

    @Override
    public void show() {
        stage = new Stage(myGame.VIRTUAL_WIDTH, myGame.VIRTUAL_HEIGHT, false);
        stage.setCamera(myGame.camera);
        Gdx.input.setInputProcessor(new GestureDetector(this));
        swapGame = new Board(myGame);
        stage.addActor(swapGame);
        /*
        swapGame = myGame.swapGame;
        swapGame.init();
        swapGame.addAction(Actions.alpha(0.0f));
        swapGame.addAction(Actions.fadeIn(0.5f));
        stage.addActor(swapGame);
        */
    }

    @Override
    public void hide() {
        //swapGame.dispose();
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

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        flingStartPosition = stage.screenToStageCoordinates(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
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
                swapGame.swapTo(flingStartPosition, 1, 0);
            } else {
                swapGame.swapTo(flingStartPosition, -1, 0);
            }
        } else {
            if (velocityY > 0) {
                swapGame.swapTo(flingStartPosition, 0, -1);
            } else {
                swapGame.swapTo(flingStartPosition, 0, 1);
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
