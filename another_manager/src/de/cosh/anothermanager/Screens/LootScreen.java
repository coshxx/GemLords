package de.cosh.anothermanager.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.cosh.anothermanager.AfterActionReport;
import de.cosh.anothermanager.AnotherManager;
import de.cosh.anothermanager.Characters.EnemyManager;
import de.cosh.anothermanager.Items.BaseItem;
import de.cosh.anothermanager.Items.ItemApprenticeRobe;

/**
 * Created by cosh on 07.01.14.
 */
public class LootScreen implements Screen {
    private final AnotherManager myGame;
    private final EnemyManager enemyManager;
    private Stage stage;
    private Skin skin;
    private Table table;

    public LootScreen(final AnotherManager anotherManager, final EnemyManager enemyManager) {
        this.myGame = anotherManager;
        this.enemyManager = enemyManager;
        skin = new Skin(Gdx.files.internal("data/ui/uiskin.json"));
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
        table = new Table();
        table.setFillParent(true);
        Gdx.input.setInputProcessor(stage);

        AfterActionReport aar = AnotherManager.getInstance().afterActionReport;

        Table leftSide = table.top().left();

        Label label1 = new Label("Total damage dealt: ", skin);
        Label label2 = new Label(aar.totalDamgeDealt.toString(), skin);
        leftSide.add(label1).left();
        leftSide.add(label2);
        leftSide.row();

        Label label3 = new Label("Total damage received: ", skin);
        Label label4 = new Label(aar.totalDamageReceived.toString(), skin);
        leftSide.add(label3).left();
        leftSide.add(label4);
        leftSide.row();

        Label label5 = new Label("Highest damage dealt in one turn: ", skin);
        Label label6 = new Label(aar.highestDamageDealtInOneTurn.toString(), skin);
        leftSide.add(label5).left();
        leftSide.add(label6);
        leftSide.row();

        Label label7 = new Label("Highest damage received in one turn: ", skin);
        Label label8 = new Label(aar.highestDamageReceivedInOneTurn.toString(), skin);
        leftSide.add(label7).left();
        leftSide.add(label8);
        leftSide.row();

        Label label9 = new Label("Player total healed: ", skin);
        Label label10 = new Label(aar.playerTotalHealed.toString(), skin);
        leftSide.add(label9).left();
        leftSide.add(label10);
        leftSide.row();

        Label label11 = new Label("Enemy total healed: ", skin);
        Label label12 = new Label(aar.enemyTotalHealed.toString(), skin);
        leftSide.add(label11).left();
        leftSide.add(label12);
        leftSide.row();

        Label label13 = new Label("Longest combo: ", skin);
        Label label14 = new Label(aar.longestCombo.toString(), skin);
        leftSide.add(label13).left();
        leftSide.add(label14);
        leftSide.row();



        final BaseItem item = enemyManager.getSelectedEnemy().getDroppedItem();
        //final BaseItem item = new ItemApprenticeRobe();
        if( item != null ) {
            Label label15 = new Label("Received Item:", skin);
            leftSide.add(label15).left();
            item.setPosition(AnotherManager.VIRTUAL_WIDTH/2 - item.getWidth()/2, 800);
            myGame.player.getInventory().addItem(item);
            stage.addActor(item);

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
                        if (item != null)
                            myGame.player.getActionBar().addToActionBar(item);
                        myGame.setScreen(myGame.mapTraverseScreen);
                    }
                })));
            }
        });
        button.setBounds(AnotherManager.VIRTUAL_WIDTH / 2 - 100, 0, 200, 100);
        stage.addActor(table);
        stage.addActor(button);
    }
}
