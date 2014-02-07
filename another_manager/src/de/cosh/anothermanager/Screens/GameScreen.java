package de.cosh.anothermanager.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import de.cosh.anothermanager.AnotherManager;
import de.cosh.anothermanager.SwapGame.Board;

public class GameScreen implements Screen, GestureListener {
	private Vector2 flingStartPosition;
	private final AnotherManager myGame;
	private Stage stage;
	private Board swapGame;

	public GameScreen(final AnotherManager myGame) {
		this.myGame = myGame;
		flingStartPosition = new Vector2();
	}

	@Override
	public void dispose() {
	}

	@Override
	public boolean fling(final float velocityX, final float velocityY, final int button) {
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
	public void hide() {
		stage.dispose();
	}

	@Override
	public boolean longPress(final float x, final float y) {
		return false;
	}

	@Override
	public boolean pan(final float x, final float y, final float deltaX, final float deltaY) {
		return false;
	}

	@Override
	public boolean panStop(final float x, final float y, final int pointer, final int button) {
		return false;
	}

	@Override
	public void pause() {

	}

	@Override
	public boolean pinch(final Vector2 initialPointer1, final Vector2 initialPointer2, final Vector2 pointer1,
			final Vector2 pointer2) {
		return false;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		swapGame.update(delta);
		stage.act(delta);
		stage.draw();

        System.out.println(stage.getSpriteBatch().renderCalls);

        // TODO: crap
        if( Gdx.input.isKeyPressed(Input.Keys.ESCAPE ) ) {
            swapGame.pressedBack();
        }
	}

	@Override
	public void resize(final int width, final int height) {
		AnotherManager.getInstance().stageResize(width, height, stage);
	}

	@Override
	public void resume() {

	}

	@Override
	public void show() {
		stage = new Stage(AnotherManager.VIRTUAL_WIDTH, AnotherManager.VIRTUAL_HEIGHT, false);
		stage.setCamera(myGame.camera);
		swapGame = new Board(myGame);
		InputMultiplexer input = new InputMultiplexer();
		input.addProcessor(stage);
		input.addProcessor(new GestureDetector(this));
		Gdx.input.setInputProcessor(input);
		AnotherManager.soundPlayer.playGameMusic();
		swapGame.init();
		swapGame.addAction(Actions.alpha(0.0f));
		swapGame.addAction(Actions.fadeIn(1));
        stage.addActor(swapGame);
	}

	@Override
	public boolean tap(final float x, final float y, final int count, final int button) {
		return false;
	}

	@Override
	public boolean touchDown(final float x, final float y, final int pointer, final int button) {
		flingStartPosition = stage.screenToStageCoordinates(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
		//flingStartPosition = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight()-Gdx.input.getY());
		return false;
	}

	@Override
	public boolean zoom(final float initialDistance, final float distance) {
		return false;
	}

    public Board getBoard() {
        return swapGame;
    }
}
