package de.cosh.gemlords.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.cosh.gemlords.AfterActionReport;
import de.cosh.gemlords.Characters.EnemyManager;
import de.cosh.gemlords.CustomActions.PlayConvertSoundAction;
import de.cosh.gemlords.CustomActions.PlayGotItemSoundAction;
import de.cosh.gemlords.CustomStyle;
import de.cosh.gemlords.GemLord;
import de.cosh.gemlords.Items.BaseItem;
import de.cosh.gemlords.LanguageManager;

/**
 * Created by cosh on 07.01.14.
 */
public class LootScreen implements Screen {
    private final GemLord myGame;
    private final EnemyManager enemyManager;
    private Stage stage;
    private Skin skin;
    private Table table;

    public LootScreen(final GemLord gemLord, final EnemyManager enemyManager) {
        this.myGame = gemLord;
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
        GemLord.getInstance().stageResize(width, height, stage);
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

        AfterActionReport aar = GemLord.getInstance().afterActionReport;
        GemLord.soundPlayer.playLootMusic();
        LanguageManager lm = LanguageManager.getInstance();

        Table leftSide = table.top().left();

        Label label1 = new Label(lm.getString("Total damage dealt: "), skin);
        label1.getStyle().font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Label label2 = new Label(aar.totalDamgeDealt.toString(), skin);
        leftSide.add(label1).left();
        leftSide.add(label2).expandX().right();
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

        Label label3 = new Label(lm.getString("Total damage received: "), skin);
        Label label4 = new Label(aar.totalDamageReceived.toString(), skin);
        leftSide.add(label3).left();
        leftSide.add(label4).expandX().right();
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

        Label label5 = new Label(lm.getString("Highest damage dealt in one turn: "), skin);
        Label label6 = new Label(aar.highestDamageDealtInOneTurn.toString(), skin);
        leftSide.add(label5).left();
        leftSide.add(label6).expandX().right();
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

        Label label7 = new Label(lm.getString("Highest damage received in one turn: "), skin);
        Label label8 = new Label(aar.highestDamageReceivedInOneTurn.toString(), skin);
        leftSide.add(label7).left();
        leftSide.add(label8).expandX().right();
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

        Label label9 = new Label(lm.getString("Player total healed: "), skin);
        Label label10 = new Label(aar.playerTotalHealed.toString(), skin);
        leftSide.add(label9).left();
        leftSide.add(label10).expandX().right();
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

        Label label11 = new Label(lm.getString("Enemy total healed: "), skin);
        Label label12 = new Label(aar.enemyTotalHealed.toString(), skin);
        leftSide.add(label11).left();
        leftSide.add(label12).expandX().right();
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

        Label label13 = new Label(lm.getString("Longest combo: "), skin);
        Label label14 = new Label(aar.longestCombo.toString(), skin);
        leftSide.add(label13).left();
        leftSide.add(label14).expandX().right();
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
        //final BaseItem item = BaseItem.getNewItemByID(1);

        BaseItem oldItem = null;
        if (item != null) {
            boolean upgradeFound = false;
            for (BaseItem invItem : myGame.player.getInventory().getAllItems()) {
                if (item.getItemSlotType() == invItem.getItemSlotType()) {
                    // upgrade
                    oldItem = invItem;
                    upgradeFound = true;
                    break;
                }
            }
            Label label15 = new Label("", skin);
            if (upgradeFound) {
                label15.setText(lm.getString("Upgraded Item:"));
                oldItem.addAction(Actions.alpha(0));
                oldItem.addAction(Actions.sequence(
                        Actions.delay(1.75f),
                        Actions.fadeIn(0.25f)));
                oldItem.setPosition(GemLord.VIRTUAL_WIDTH / 2 - item.getWidth() / 2, 800);
                oldItem.setDrawText(true);
                stage.addActor(oldItem);
                TextureAtlas atlas = GemLord.assets.get("data/textures/pack.atlas", TextureAtlas.class);
                Image upgradeArrow = new Image(atlas.findRegion("upgradearrow"));
                upgradeArrow.setBounds(GemLord.VIRTUAL_WIDTH/2 - upgradeArrow.getWidth()/2, 560, upgradeArrow.getWidth(), upgradeArrow.getHeight());
                upgradeArrow.addAction(Actions.alpha(0));
                upgradeArrow.addAction(Actions.sequence(
                        Actions.delay(1.75f),
                        Actions.fadeIn(0.25f)));
                stage.addActor(upgradeArrow);

            } else {
                label15 = new Label(lm.getString("Received Item:"), skin);
            }
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
            item.setPosition(GemLord.VIRTUAL_WIDTH / 2 - item.getWidth()/2, 440);
            myGame.player.getInventory().addItem(item);
            stage.addActor(item);

        }
        final BaseItem oldInventoryItem = oldItem;
        TextButton button = new TextButton(lm.getString("Return to map"), skin);
        button.setStyle(CustomStyle.getInstance().getTextButtonStyle());
        button.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                stage.addAction(Actions.sequence(Actions.fadeOut(0.5f), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        GemLord.soundPlayer.stopLootMusic();
                        myGame.player.levelDone(myGame.enemyManager.getSelectedEnemy().getEnemyNumber());
                        if (item != null) {
                            if (oldInventoryItem != null) {
                                myGame.player.getActionBar().removeFromBar(oldInventoryItem);
                                myGame.player.getInventory().removeItem(oldInventoryItem);
                            }
                            myGame.player.getActionBar().addToActionBar(item);
                        }
                        myGame.player.savePreferences();
                        myGame.setScreen(myGame.player.getCurrentEpisodeScreen());
                    }
                })));
            }
        });

        button.setBounds(-200, 0, 200, 100);
        //button.setBounds(GemLord.VIRTUAL_WIDTH / 2 - 100, 0, 200, 100);
        button.addAction(Actions.moveTo(GemLord.VIRTUAL_WIDTH / 2 - 100, 0, 0.25f));

        // TODO: ENABLE BEFORE RLZ
        /*
        button.addAction(Actions.sequence(
                Actions.delay(1.75f),
                Actions.moveTo(GemLord.VIRTUAL_WIDTH / 2 - 100, 0, 0.25f)));
        */
        stage.addActor(button);
        stage.addActor(table);
    }
}
