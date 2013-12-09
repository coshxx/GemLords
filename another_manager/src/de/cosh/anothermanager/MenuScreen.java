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
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MenuScreen implements Screen {
    private AnotherManager myGame;
    private Board board;
    private SpriteBatch batch;

    private Texture logoTexture;
    private Vector2 logoPosition;

    private Image logoImage;

    private float logoSpeed = 5f;
    private float logoTimeMoved = 0f;
    private float logoMaxMoveInOneDirectionTime = 2f;
    private boolean logoMoveUp = false;

    private Stage stage;

    private TextButton newGameButton;
    private TextButton exitGameButton;

    private Sound blub1;
    private Sound blub2;


    public MenuScreen(final AnotherManager myGame) {
        this.myGame = myGame;
        board = new Board();
        batch = new SpriteBatch();
        board.init();

        blub1 = Gdx.audio.newSound(Gdx.files.internal("data/blub1.ogg"));
        blub2 = Gdx.audio.newSound(Gdx.files.internal("data/blub2.ogg"));

        logoTexture = new Texture("data/logo.png");
        logoPosition = new Vector2();
        logoPosition.x = (myGame.VIRTUAL_WIDTH / 2) - (logoTexture.getWidth() / 2);
        logoPosition.y = myGame.VIRTUAL_HEIGHT - (logoTexture.getHeight() * 1.1f);

        logoImage = new Image(logoTexture);
        logoImage.setPosition(logoPosition.x, logoPosition.y);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Texture buttonTexture = new Texture("data/button.png");
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
                stage.addAction(Actions.sequence(Actions.fadeOut(1f), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        myGame.setScreen(myGame.gameScreen);
                    }
                })));
                blub1.play();
            }
        });

        exitGameButton = new TextButton("Exit", style);
        exitGameButton.setPosition(logoPosition.x, 150);
        exitGameButton.addListener(new ClickListener() {
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                blub2.play();
                stage.addAction(Actions.sequence(Actions.fadeOut(1), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        Gdx.app.exit();
                    }
                })));
            }
        });
        stage.addActor(newGameButton);
        stage.addActor(exitGameButton);
        stage.addActor(logoImage);
        logoImage.addAction(Actions.repeat(-1, Actions.sequence(Actions.moveBy(0, 15, .5f), Actions.moveBy(0, -15, .5f))));
        stage.addAction(Actions.alpha(0));
        stage.addAction(Actions.fadeIn(1));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 1.0f, 1.0f);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        update_logo(delta);
        batch.setProjectionMatrix(myGame.camera.combined);

        stage.act(delta);
        stage.draw();
    }

    private void update_logo(float delta) {
        logoTimeMoved += delta;
        if( logoTimeMoved >= logoMaxMoveInOneDirectionTime ) {
            logoMoveUp = !logoMoveUp;
            logoTimeMoved = 0f;
        }
        if( logoMoveUp ) {
            logoPosition.y += delta * logoSpeed;
        } else {
            logoPosition.y -= delta * logoSpeed;
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
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
