package de.cosh.anothermanager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

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

    public boolean enemyWindowOpen;

    public MapTraverseScreen(AnotherManager anotherManager) {
        myGame = anotherManager;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
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
        mapTexture = myGame.assets.get("data/map.png", Texture.class);

        stage.setCamera(myGame.camera);

        mapImage = new Image(mapTexture);
        mapImage.setBounds(0, 0, myGame.VIRTUAL_WIDTH, myGame.VIRTUAL_HEIGHT);

        stage.addActor(mapImage);
        stage.addAction(Actions.alpha(0));
        stage.addAction(Actions.fadeIn(1));

        initEnemyLocations();

        for (int x = 0; x < 5; x++) {
            Image heartEmpty = new Image(myGame.assets.get("data/heartempty.png", Texture.class));
            heartEmpty.setPosition(32 + (32 * x), myGame.VIRTUAL_HEIGHT - 64);
            stage.addActor(heartEmpty);
        }

        for (int x = 0; x < myGame.player.getLives(); x++) {
            Image heart = new Image(myGame.assets.get("data/heart.png", Texture.class));
            heart.setPosition(32 + (32 * x), myGame.VIRTUAL_HEIGHT - 64);
            stage.addActor(heart);
        }
        myGame.soundPlayer.playMapMusic();
        Gdx.input.setInputProcessor(stage);
    }

    private void initEnemyLocations() {
        Enemy enemy = new Enemy(myGame, myGame.assets.get("data/enemy.png", Texture.class));
        enemy.setDefeated(myGame.player.levelDone[0]);
        enemy.addPositionalButtonToMap(110, 110, enemy.getImage(), 10, stage);

        Enemy enemy2 = new Enemy(myGame, myGame.assets.get("data/enemy.png", Texture.class));
        enemy2.setDefeated(myGame.player.levelDone[1]);
        enemy2.addPositionalButtonToMap(150, 110, enemy.getImage(), 50, stage);

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
