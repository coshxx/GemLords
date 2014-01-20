package de.cosh.anothermanager.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import de.cosh.anothermanager.AnotherManager;
import de.cosh.anothermanager.Characters.EnemyManager;
import de.cosh.anothermanager.Items.BaseItem;

/**
 * Created by cosh on 07.01.14.
 */
public class LootScreen implements Screen {
    private final AnotherManager myGame;
    private final EnemyManager enemyManager;
    private Image chestImage;
    private Stage stage;

    public LootScreen(final AnotherManager anotherManager, final EnemyManager enemyManager) {
        this.myGame = anotherManager;
        this.enemyManager = enemyManager;
    }

    @Override
    public void dispose() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void render(final float delta) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1f);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(final int width, final int height) {
    }

    @Override
    public void resume() {

    }

    @Override
    public void show() {
        stage = new Stage();
        stage.setCamera(myGame.camera);

        chestImage = new Image(myGame.assets.get("data/textures/treasure.jpg", Texture.class));
        chestImage.setPosition(myGame.VIRTUAL_WIDTH / 2 - 200, myGame.VIRTUAL_HEIGHT / 2);
        stage.addActor(chestImage);

        stage.addAction(Actions.alpha(0.0f));
        stage.addAction(Actions.fadeIn(1.0f));

        myGame.soundPlayer.playLootMusic();

        TextButton button;
        TextButton.TextButtonStyle tStyle;
        tStyle = new TextButton.TextButtonStyle();
        final Texture buttonTexture = myGame.assets.get("data/textures/button.png", Texture.class);
        final BitmapFont buttonFont = new BitmapFont();
        tStyle.up = new TextureRegionDrawable(new TextureRegion(buttonTexture));
        tStyle.down = new TextureRegionDrawable(new TextureRegion(buttonTexture));
        tStyle.font = buttonFont;

        button = new TextButton("Return to map", tStyle);
        button.setPosition(50, 50);
        button.setSize(100, 100);

        final BaseItem i = enemyManager.getSelectedEnemy().getDroppedItem();

        i.setPosition(AnotherManager.VIRTUAL_WIDTH + i.getWidth(), (AnotherManager.VIRTUAL_HEIGHT / 2) - 200);
        i.addAction(Actions.sequence(
                Actions.delay(0.5f),
                Actions.moveBy(-((AnotherManager.VIRTUAL_WIDTH / 2) + i.getWidth()*1.5f), 0, 0.25f)
        ));
        stage.addActor(i);

        button.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                i.addAction(Actions.moveBy(-AnotherManager.VIRTUAL_WIDTH, 0, 0.25f));
                stage.addAction(Actions.sequence(Actions.fadeOut(0.5f), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        myGame.soundPlayer.stopLootMusic();
                        myGame.player.levelDone(myGame.enemyManager.getSelectedEnemy().getEnemyNumber());
                        myGame.player.addItem(i);
                        myGame.setScreen(myGame.mapTraverseScreen);
                    }
                })));
            }
        });
        stage.addActor(button);
        Gdx.input.setInputProcessor(stage);
    }
}
