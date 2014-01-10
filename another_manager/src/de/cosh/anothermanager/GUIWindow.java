package de.cosh.anothermanager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by cosh on 10.01.14.
 */
public class GUIWindow {
    private AnotherManager myGame;
    private Stage stage;
    private Window.WindowStyle windowStyle;
    private TextButton.TextButtonStyle textButtonStyle;
    private Label.LabelStyle labelStyle;
    private Table root;
    private boolean showingMapEnemyWindow;

    public GUIWindow(AnotherManager myGame, Stage stage) {
        this.myGame = myGame;
        this.stage = stage;
        showingMapEnemyWindow = false;

        windowStyle = new Window.WindowStyle();
        windowStyle.titleFont = myGame.assets.get("data/font.fnt", BitmapFont.class);
        windowStyle.titleFont.setScale(1f);
        Texture nTexture = new Texture("data/menuskin.png");
        NinePatch nPatch = new NinePatch(new TextureRegion(nTexture, 24, 24), 8, 8, 8, 8);
        windowStyle.background = new NinePatchDrawable(nPatch);

        textButtonStyle = new TextButton.TextButtonStyle();
        Texture buttonTexture = myGame.assets.get("data/button.png", Texture.class);
        BitmapFont buttonFont = myGame.assets.get("data/font.fnt", BitmapFont.class);
        textButtonStyle.up = new TextureRegionDrawable(new TextureRegion(buttonTexture));
        textButtonStyle.down = new TextureRegionDrawable(new TextureRegion(buttonTexture));
        textButtonStyle.font = buttonFont;

        labelStyle = new Label.LabelStyle();
        labelStyle.font = myGame.assets.get("data/font.fnt", BitmapFont.class);
    }

    public void createVictoryWindow(Group foreGround, Group backGround, Group windowGroup) {
        Window window = new Window("You win!", windowStyle);
        window.setPosition(200, 200);
        window.setColor(1.0f, 1.0f, 1.0f, 0.95f);
        window.setMovable(true);
        window.pad(20, 0, 0, 0);
        window.setModal(false);
        window.align(Align.center);
        window.setTitleAlignment(Align.center);
        window.setSize(200, 200);

        TextButton button;
        button = new TextButton("Loot", textButtonStyle);
        button.setPosition(50, 50);
        button.setSize(100, 100);
        Gdx.input.setInputProcessor(stage);
        button.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                fadeToLootScreen();
            }
        });
        window.addActor(button);
        backGround.addAction(Actions.alpha(0.5f, 1.0f));
        foreGround.addAction(Actions.alpha(0.5f, 1.0f));
        windowGroup.addActor(window);
    }

    private void fadeToLootScreen() {
        stage.addAction(Actions.sequence(Actions.fadeOut(1f), Actions.run(new Runnable() {
            @Override
            public void run() {
                myGame.setScreen(myGame.lootScreen);
            }
        })));
    }

    private void fadeToMapScreen() {
        stage.addAction(Actions.sequence(Actions.fadeOut(1f), Actions.run(new Runnable() {
            @Override
            public void run() {
                myGame.setScreen(myGame.mapTraverseScreen);
            }
        })));
    }

    public void createDefeatWindow(Group foreGround, Group backGround, Group windowGroup) {
        Window window = new Window("You lose", windowStyle);
        window.setPosition(200, 200);
        window.setColor(1.0f, 1.0f, 1.0f, 0.95f);
        window.setMovable(true);
        window.pad(20, 0, 0, 0);
        window.setModal(false);
        window.align(Align.center);
        window.setTitleAlignment(Align.center);
        window.setSize(200, 200);

        TextButton button;
        button = new TextButton("Map", textButtonStyle);
        button.setPosition(50, 50);
        button.setSize(100, 100);
        Gdx.input.setInputProcessor(stage);
        button.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                fadeToMapScreen();
            }
        });
        window.addActor(button);
        backGround.addAction(Actions.alpha(0.5f, 1.0f));
        foreGround.addAction(Actions.alpha(0.5f, 1.0f));
        windowGroup.addActor(window);
    }

    public void showMapEnemyWindow() {
        if (!myGame.mapTraverseScreen.enemyWindowOpen) {
            myGame.mapTraverseScreen.enemyWindowOpen = true;
            Window window = new Window("Challenge:", windowStyle);
            window.setPosition(0, 0);
            window.setColor(1.0f, 1.0f, 1.0f, 0.95f);
            window.pad(20, 0, 0, 0);
            window.align(Align.center);
            window.setMovable(false);
            window.setTitleAlignment(Align.center | Align.top);
            window.setSize(250, 250);

            Table t = new Table();
            t.setBounds(0, 0, 250, 250);
            t.setLayoutEnabled(true);
            window.add(t);

            Image enemy = new Image(myGame.assets.get("data/enemy.png", Texture.class));
            enemy.setPosition(0, 0);
            enemy.setAlign(Align.center);
            t.add(enemy);
            t.row();

            Label enemyInfoLabel = new Label("30 HP", labelStyle);
            t.add(enemyInfoLabel);
            t.row();

            TextButton fightButton;
            fightButton = new TextButton("Fight", textButtonStyle);
            fightButton.setPosition(0, 0);
            t.add(fightButton);
            t.row();
            Gdx.input.setInputProcessor(stage);
            fightButton.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    fadeToGameScreen();
                }
            });

            TextButton cancelButton;
            cancelButton = new TextButton("Cancel", textButtonStyle);
            cancelButton.setPosition(0, 0);
            cancelButton.align(Align.bottom);
            cancelButton.setSize(50, 50);
            t.add(cancelButton);
            t.row();
            cancelButton.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    hideEnemyWindow();
                }
            });

            root = new Table();
            root.setBounds(0, 0, myGame.VIRTUAL_WIDTH, myGame.VIRTUAL_HEIGHT);
            root.align(Align.center);
            root.add(window);
            stage.addActor(root);
            root.setPosition(-500, 0);
            root.addAction(Actions.moveBy(500, 0, 0.25f));
        }
    }

    private void hideEnemyWindow() {
        root.addAction(Actions.moveBy(600, 0, 0.25f));
        myGame.mapTraverseScreen.enemyWindowOpen = false;
    }

    private void fadeToGameScreen() {
        myGame.mapTraverseScreen.enemyWindowOpen = false;
        myGame.mapTraverseScreen.fadeMusic();
        root.addAction(Actions.moveBy(600, 0, 0.25f));
        stage.addAction(Actions.sequence(Actions.fadeOut(1f), Actions.run(new Runnable() {
            @Override
            public void run() {
                myGame.setScreen(myGame.gameScreen);
            }
        })));
    }
}
