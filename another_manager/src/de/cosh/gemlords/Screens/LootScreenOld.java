package de.cosh.gemlords.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.cosh.gemlords.GemLord;
import de.cosh.gemlords.Characters.EnemyManager;
import de.cosh.gemlords.Items.BaseItem;

/**
 * Created by cosh on 07.01.14.
 */
public class LootScreenOld implements Screen {
    private final GemLord myGame;
    private final EnemyManager enemyManager;
    private Image chestImage;
    private Stage stage;
    private Skin skin;
    private Label addedToBarLabel, couldNotAddLabel;

    public LootScreenOld(final GemLord gemLord, final EnemyManager enemyManager) {
        this.myGame = gemLord;
        this.enemyManager = enemyManager;
        skin = new Skin(Gdx.files.internal("data/ui/uiskin.json"));
        addedToBarLabel = new Label("The item will be added to your Actionbar", skin);
        addedToBarLabel.setFontScale(0.75f);
        couldNotAddLabel = new Label("Your Actionbar is full. Please check the loadout screen", skin);
        couldNotAddLabel.setFontScale(0.75f);
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
        GemLord.getInstance().stageResize(width, height, stage);
    }

    @Override
    public void resume() {

    }

    @Override
    public void show() {
        stage = new Stage();
        chestImage = new Image(GemLord.assets.get("data/textures/treasure.jpg", Texture.class));
        chestImage.setPosition(GemLord.VIRTUAL_WIDTH / 2 - chestImage.getWidth() / 2, 800);
        stage.addActor(chestImage);

        BitmapFont.TextBounds boundsAdded = addedToBarLabel.getStyle().font.getBounds(addedToBarLabel.getText());
        addedToBarLabel.setPosition(-boundsAdded.width, 350);
        BitmapFont.TextBounds boundsCouldNot = couldNotAddLabel.getStyle().font.getBounds(couldNotAddLabel.getText());
        couldNotAddLabel.setPosition(-boundsCouldNot.width, 350);

        GemLord.getInstance().soundPlayer.playLootMusic();

        final BaseItem i = enemyManager.getSelectedEnemy().getDroppedItem();
        if (i != null) {
            i.setPosition(GemLord.VIRTUAL_WIDTH, 500);
            i.addAction(Actions.moveBy(-400, 0, 0.25f));
            stage.addActor(i);
        }

        if (i != null) {
            myGame.player.getInventory().addItem(i);
            if (myGame.player.getActionBar().hasFreeSlot()) {
                addedToBarLabel.addAction(Actions.moveBy(boundsAdded.width - 40, 0, 0.25f));
                stage.addActor(addedToBarLabel);
            } else {
                couldNotAddLabel.addAction(Actions.moveBy(boundsCouldNot.width + 80, 0, 0.25f));
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
                        GemLord.soundPlayer.stopLootMusic();
                        myGame.player.levelDone(myGame.enemyManager.getSelectedEnemy().getEnemyNumber());
                        if (i != null)
                            myGame.player.getActionBar().addToActionBar(i);
                        myGame.setScreen(myGame.mapTraverseScreen);
                    }
                })));
            }
        });
        button.setBounds(GemLord.VIRTUAL_WIDTH / 2 - 100, 200, 200, 100);
        stage.addActor(button);
        Gdx.input.setInputProcessor(stage);
    }
}
