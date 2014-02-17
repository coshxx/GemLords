package de.cosh.anothermanager.Screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import de.cosh.anothermanager.GemLord;
import de.cosh.anothermanager.SwapGame.Board;

public class GameScreen implements Screen, GestureListener, InputProcessor {
	private Vector2 flingStartPosition;
	private final GemLord myGame;
	private Stage stage;
	private Board swapGame;

	public GameScreen(final GemLord myGame) {
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

        if( delta > 0.1f ) {
            delta = 0.1f;
        }

		swapGame.update(delta);
		stage.act(delta);
		stage.draw();

        stage.getSpriteBatch().begin();
        stage.getSpriteBatch().end();

        if( Gdx.input.isKeyPressed(Input.Keys.ESCAPE ))
            swapGame.getEnemy().setHealth(0);
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
		stage = new Stage(GemLord.VIRTUAL_WIDTH, GemLord.VIRTUAL_HEIGHT, false);
        myGame.camera.zoom = 1f;
		stage.setCamera(myGame.camera);
		swapGame = new Board(myGame);
		InputMultiplexer input = new InputMultiplexer();
		input.addProcessor(stage);
        input.addProcessor(new GestureDetector(this));
		Gdx.input.setInputProcessor(input);
        Gdx.input.setCatchBackKey(true);
		GemLord.soundPlayer.playGameMusic();
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

    @Override
    public boolean keyDown(int keycode) {
        if( keycode == Input.Keys.ESCAPE || keycode == Input.Keys.BACK ) {
            swapGame.pressedBack();
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public SpriteBatch getBatch() {
        return stage.getSpriteBatch();
    }
}
