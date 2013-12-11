package de.cosh.anothermanager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by cosh on 10.12.13.
 */
public class MapTraverseScreen implements Screen {
    private AnotherManager myGame;
    private Stage stage;

    private Texture mapTexture;
    private TextureRegion pointTexture;
    private Image mapImage;
    private Skin skin;
    private ImageButton pointButton;

    public MapTraverseScreen(AnotherManager anotherManager) {
        myGame = anotherManager;

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 1f, 1f);
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
        skin = new Skin();
        pointTexture = new TextureRegion(myGame.assets.get("data/point.png", Texture.class));
        mapTexture = myGame.assets.get("data/map.png", Texture.class);

        stage.setCamera(myGame.camera);
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.up = new TextureRegionDrawable(pointTexture);
        style.down = new TextureRegionDrawable(pointTexture);

        pointButton = new ImageButton(style.up, style.down);
        Vector2 position = new Vector2(110, 110);
        pointButton.setPosition(position.x, position.y);
        pointButton.setScale(0.8f);
        mapImage = new Image(mapTexture);
        mapImage.setBounds(0, 0, myGame.VIRTUAL_WIDTH, myGame.VIRTUAL_HEIGHT);
        stage.addActor(mapImage);
        stage.addActor(pointButton);
        stage.addAction(Actions.alpha(0));
        stage.addAction(Actions.fadeIn(1));

        pointButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                myGame.soundPlayer.PlayBlub1();
                stage.addAction(Actions.sequence(Actions.fadeOut(.5f), Actions.delay(.5f), Actions.run(new Runnable() {
                    @Override
                    public void run() {

                        myGame.setScreen(myGame.gameScreen);
                    }
                })));
            }
        });

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
