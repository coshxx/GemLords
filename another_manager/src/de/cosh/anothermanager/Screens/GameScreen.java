package de.cosh.anothermanager.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;

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
		// swapGame.dispose();
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
	public void render(final float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		swapGame.update(delta);

		stage.act(delta);
		stage.draw();

		/*
		final SpriteBatch batch = stage.getSpriteBatch();
		batch.begin();
        final Vector2 begin = stage.stageToScreenCoordinates(new Vector2(0, AnotherManager.VIRTUAL_HEIGHT - 247));
        final Vector2 end = stage.stageToScreenCoordinates(new Vector2(AnotherManager.VIRTUAL_WIDTH ,
                AnotherManager.VIRTUAL_HEIGHT - 720));
        final Rectangle scissor = new Rectangle();
        final Rectangle clipBounds = new Rectangle(begin.x, begin.y, end.x, end.y);
        ScissorStack.calculateScissors(myGame.camera, 0, 0, AnotherManager.VIRTUAL_WIDTH, AnotherManager.VIRTUAL_HEIGHT,
                batch.getTransformMatrix(), clipBounds, scissor);
        //batch.flush();
        ScissorStack.pushScissors(scissor);
        swapGame.drawGems(batch, 1);
        //super.draw(batch, parentAlpha);
        //batch.flush();
        ScissorStack.popScissors();
        batch.end();
        */
        stage.draw();
	}

	@Override
	public void resize(final int width, final int height) {
		stage.setViewport(AnotherManager.VIRTUAL_WIDTH, AnotherManager.VIRTUAL_HEIGHT, false);
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
		stage.addActor(swapGame);
		AnotherManager.soundPlayer.playGameMusic();
		swapGame.init();
		swapGame.addAction(Actions.alpha(0.0f));
		swapGame.addAction(Actions.fadeIn(0.5f));
	}

	@Override
	public boolean tap(final float x, final float y, final int count, final int button) {
		return false;
	}

	@Override
	public boolean touchDown(final float x, final float y, final int pointer, final int button) {
		flingStartPosition = stage.screenToStageCoordinates(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
		return false;
	}

	@Override
	public boolean zoom(final float initialDistance, final float distance) {
		return false;
	}
}
