package de.cosh.anothermanager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.ArrayList;

/**
 * Created by cosh on 10.12.13.
 */
public class MapTraverseScreen implements Screen {
    private AnotherManager myGame;
    private Stage stage;
    private Texture mapTexture;
    private Image mapImage;
    private Skin skin;
    private boolean fadeMusic;

    private ArrayList<MapEnemyLocation> enemyLocations;
    public boolean enemyWindowOpen;

    public MapTraverseScreen(AnotherManager anotherManager) {
        myGame = anotherManager;
        enemyLocations = new ArrayList<MapEnemyLocation>();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 1f, 1f);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();

        if (fadeMusic) {
            myGame.soundPlayer.fadeOutMapMusic(delta);
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        stage = new Stage();
        skin = new Skin();
        fadeMusic = false;
        enemyWindowOpen = false;
        enemyLocations.clear();
        mapTexture = myGame.assets.get("data/map.png", Texture.class);

        stage.setCamera(myGame.camera);

        mapImage = new Image(mapTexture);
        mapImage.setBounds(0, 0, myGame.VIRTUAL_WIDTH, myGame.VIRTUAL_HEIGHT);

        stage.addActor(mapImage);
        stage.addAction(Actions.alpha(0));
        stage.addAction(Actions.fadeIn(1));

        initEnemyLocations();

        myGame.soundPlayer.playMapMusic();
        Gdx.input.setInputProcessor(stage);
    }

    private void initEnemyLocations() {
        Vector2 position = new Vector2(110, 110);
        Image enemyImage = new Image(myGame.assets.get("data/enemy.png", Texture.class));

        MapEnemyLocation enemy1 = new MapEnemyLocation(myGame);
        enemy1.revealed = true;
        enemy1.addPositionalButtonToMap(position, enemyImage, stage);

        MapEnemyLocation enemy2 = new MapEnemyLocation(myGame);
        enemy2.revealed = true;
        position = new Vector2(150, 110);
        enemy2.addPositionalButtonToMap(position, enemyImage, stage);
    }

    @Override
    public void hide() {
        stage.dispose();
        myGame.soundPlayer.stopMapMusic();
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

    public void fadeMusic() {
        fadeMusic = true;
    }
}
