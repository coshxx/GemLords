package de.cosh.anothermanager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

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
        chestImage.setPosition(myGame.VIRTUAL_WIDTH/2-200, myGame.VIRTUAL_HEIGHT/2);
        stage.addActor(chestImage);

        stage.addAction(Actions.alpha(0.0f));
        stage.addAction(Actions.fadeIn(1.0f));

        myGame.soundPlayer.playLootMusic();
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
