package de.cosh.anothermanager.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.cosh.anothermanager.AfterActionReport;
import de.cosh.anothermanager.AnotherManager;
import de.cosh.anothermanager.Characters.EnemyManager;
import de.cosh.anothermanager.CustomActions.PlayGotItemSoundAction;
import de.cosh.anothermanager.Items.BaseItem;
import de.cosh.anothermanager.CustomActions.PlayConvertSoundAction;

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
        AnotherManager.soundPlayer.playLootMusic();

        Table leftSide = table.top().left();

        Label label1 = new Label("Total damage dealt: ", skin);
        Label label2 = new Label(aar.totalDamgeDealt.toString(), skin);
        leftSide.add(label1).left();
        leftSide.add(label2);
        leftSide.row();

        label1.addAction(Actions.alpha(0));
        label2.addAction(Actions.alpha(0));
        label1.addAction(Actions.sequence(
                Actions.delay(0.0f),
                new PlayConvertSoundAction(),
                Actions.fadeIn(0.25f)));


        label2.addAction(Actions.sequence(
                Actions.delay(0.0f),
                Actions.fadeIn(0.25f)));

        Label label3 = new Label("Total damage received: ", skin);
        Label label4 = new Label(aar.totalDamageReceived.toString(), skin);
        leftSide.add(label3).left();
        leftSide.add(label4);
        leftSide.row();

        label3.addAction(Actions.alpha(0));
        label4.addAction(Actions.alpha(0));
        label3.addAction(Actions.sequence(
                Actions.delay(0.25f),
                new PlayConvertSoundAction(),
                Actions.fadeIn(0.25f)));


        label4.addAction(Actions.sequence(
                Actions.delay(0.25f),
                Actions.fadeIn(0.25f)));

        Label label5 = new Label("Highest damage dealt in one turn: ", skin);
        Label label6 = new Label(aar.highestDamageDealtInOneTurn.toString(), skin);
        leftSide.add(label5).left();
        leftSide.add(label6);
        leftSide.row();

        label5.addAction(Actions.alpha(0));
        label6.addAction(Actions.alpha(0));
        label5.addAction(Actions.sequence(
                Actions.delay(0.5f),
                new PlayConvertSoundAction(),
                Actions.fadeIn(0.25f)));


        label6.addAction(Actions.sequence(
                Actions.delay(0.5f),
                Actions.fadeIn(0.25f)));

        Label label7 = new Label("Highest damage received in one turn: ", skin);
        Label label8 = new Label(aar.highestDamageReceivedInOneTurn.toString(), skin);
        leftSide.add(label7).left();
        leftSide.add(label8);
        leftSide.row();

        label7.addAction(Actions.alpha(0));
        label8.addAction(Actions.alpha(0));
        label7.addAction(Actions.sequence(
                Actions.delay(0.75f),
                new PlayConvertSoundAction(),
                Actions.fadeIn(0.25f)));


        label8.addAction(Actions.sequence(
                Actions.delay(0.75f),
                Actions.fadeIn(0.25f)));

        Label label9 = new Label("Player total healed: ", skin);
        Label label10 = new Label(aar.playerTotalHealed.toString(), skin);
        leftSide.add(label9).left();
        leftSide.add(label10);
        leftSide.row();

        label9.addAction(Actions.alpha(0));
        label10.addAction(Actions.alpha(0));
        label9.addAction(Actions.sequence(
                Actions.delay(1f),
                new PlayConvertSoundAction(),
                Actions.fadeIn(0.25f)));


        label10.addAction(Actions.sequence(
                Actions.delay(1f),
                Actions.fadeIn(0.25f)));

        Label label11 = new Label("Enemy total healed: ", skin);
        Label label12 = new Label(aar.enemyTotalHealed.toString(), skin);
        leftSide.add(label11).left();
        leftSide.add(label12);
        leftSide.row();

        label11.addAction(Actions.alpha(0));
        label12.addAction(Actions.alpha(0));
        label11.addAction(Actions.sequence(
                Actions.delay(1.25f),
                new PlayConvertSoundAction(),
                Actions.fadeIn(0.25f)));

        label12.addAction(Actions.sequence(
                        Actions.delay(1.25f),
                        Actions.fadeIn(0.25f)));

        Label label13 = new Label("Longest combo: ", skin);
        Label label14 = new Label(aar.longestCombo.toString(), skin);
        leftSide.add(label13).left();
        leftSide.add(label14);
        leftSide.row();

        label13.addAction(Actions.alpha(0));
        label14.addAction(Actions.alpha(0));
        label13.addAction(Actions.sequence(
                Actions.delay(1.5f),
                new PlayConvertSoundAction(),
                Actions.fadeIn(0.25f)));


        label14.addAction(Actions.sequence(
                Actions.delay(1.5f),
                Actions.fadeIn(0.25f)));



        final BaseItem item = enemyManager.getSelectedEnemy().getDroppedItem();
        //final BaseItem item = new ItemApprenticeRobe();
        if( item != null ) {
            Label label15 = new Label("Received Item:", skin);
            label15.addAction(Actions.alpha(0));
            item.addAction(Actions.alpha(0));

            label15.addAction(Actions.sequence(
                    Actions.delay(1.75f),
                    new PlayGotItemSoundAction(),
                    Actions.fadeIn(0.25f)));


            item.addAction(Actions.sequence(
                    Actions.delay(1.75f),
                    Actions.fadeIn(0.25f)));

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
