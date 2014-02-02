package de.cosh.anothermanager.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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
    private Skin skin;
    private Label addedToBarLabel, couldNotAddLabel;

    public LootScreen(final AnotherManager anotherManager, final EnemyManager enemyManager) {
        this.myGame = anotherManager;
        this.enemyManager = enemyManager;
        skin = new Skin(Gdx.files.internal("data/ui/uiskin.json"));
        addedToBarLabel = new Label("The item will be added to your Actionbar", skin);
        couldNotAddLabel = new Label("Your Actionbar is full. Please check the loadout screen", skin);
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
        AnotherManager.getInstance().stageResize(width, height, stage);
    }

    @Override
    public void resume() {

    }

    @Override
    public void show() {
        stage = new Stage();
        chestImage = new Image(AnotherManager.assets.get("data/textures/treasure.jpg", Texture.class));
        chestImage.setPosition(AnotherManager.VIRTUAL_WIDTH / 2 - chestImage.getWidth() / 2, 800);
        stage.addActor(chestImage);

        addedToBarLabel.setPosition(-200, 400);
        couldNotAddLabel.setPosition(-200, 400);

        AnotherManager.getInstance().soundPlayer.playLootMusic();

        final BaseItem i = enemyManager.getSelectedEnemy().getDroppedItem();
        if (i != null) {
            i.setPosition(AnotherManager.VIRTUAL_WIDTH, 500);
            i.addAction(Actions.moveBy(-400, 0, 0.25f));
            stage.addActor(i);
        }

        if (i != null) {
            myGame.player.getInventory().addItem(i);
            if (myGame.player.getActionBar().hasFreeSlot()) {
                addedToBarLabel.addAction(Actions.moveBy(400, 0, 0.25f));
                stage.addActor(addedToBarLabel);
            } else {
                couldNotAddLabel.addAction(Actions.moveBy(400, 0, 0.25f));
                stage.addActor(couldNotAddLabel);
            }
        }

        TextButton button = new TextButton("Return to map", skin);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                    stage.addAction(Actions.sequence(Actions.fadeOut(0.5f), Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            AnotherManager.soundPlayer.stopLootMusic();
                            myGame.player.levelDone(myGame.enemyManager.getSelectedEnemy().getEnemyNumber());
                            if( i != null )
                                myGame.player.getActionBar().addToActionBar(i);
                            myGame.setScreen(myGame.mapTraverseScreen);
                        }
                    })));
            }
        });
        button.setBounds(AnotherManager.VIRTUAL_WIDTH / 2 - 100, 200, 200, 100);
        stage.addActor(button);
        Gdx.input.setInputProcessor(stage);
    }
}
