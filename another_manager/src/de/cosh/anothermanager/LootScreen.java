package de.cosh.anothermanager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by cosh on 07.01.14.
 */
public class LootScreen implements Screen {
    private AnotherManager myGame;
    private Stage stage;

    private Image chestImage;

    public LootScreen(AnotherManager anotherManager) {
        myGame = anotherManager;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1f);
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

        chestImage = new Image(myGame.assets.get("data/treasure.jpg", Texture.class));
        chestImage.setPosition(myGame.VIRTUAL_WIDTH / 2 - 200, myGame.VIRTUAL_HEIGHT / 2);
        stage.addActor(chestImage);

        stage.addAction(Actions.alpha(0.0f));
        stage.addAction(Actions.fadeIn(1.0f));

        myGame.soundPlayer.playLootMusic();

        TextButton button;
        TextButton.TextButtonStyle tStyle;
        tStyle = new TextButton.TextButtonStyle();
        Texture buttonTexture = myGame.assets.get("data/button.png", Texture.class);
        BitmapFont buttonFont = new BitmapFont();
        tStyle.up = new TextureRegionDrawable(new TextureRegion(buttonTexture));
        tStyle.down = new TextureRegionDrawable(new TextureRegion(buttonTexture));
        tStyle.font = buttonFont;

        button = new TextButton("Return to map", tStyle);
        button.setPosition(50, 50);
        button.setSize(100, 100);

        button.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                stage.addAction(Actions.sequence(Actions.fadeOut(0.5f), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        myGame.soundPlayer.stopLootMusic();
                        myGame.setScreen(myGame.mapTraverseScreen);
                    }
                })));
            }
        });
        stage.addActor(button);
        Gdx.input.setInputProcessor(stage);
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
