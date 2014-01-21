package de.cosh.anothermanager.GUI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import de.cosh.anothermanager.AnotherManager;
import de.cosh.anothermanager.Items.BaseItem;
import de.cosh.anothermanager.Screens.LoadoutScreen;
import de.cosh.anothermanager.Screens.MapTraverseScreen;

/**
 * Created by cosh on 20.01.14.
 */
public class GUIButton {
    private Texture buttonTexture;
    private TextButton button;

    public GUIButton() {
        buttonTexture = AnotherManager.assets.get("data/textures/button.png", Texture.class);
    }

    public void createLoadoutButton(final Stage stage, float x, float y) {
        final TextureRegion upRegion = new TextureRegion(buttonTexture);
        final TextureRegion downRegion = new TextureRegion(buttonTexture);
        final BitmapFont buttonFont = new BitmapFont();

        final TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = new TextureRegionDrawable(upRegion);
        style.down = new TextureRegionDrawable(downRegion);
        style.font = buttonFont;


        button = new TextButton("Loadout", style);
        button.setPosition(x, y);
        button.setLayoutEnabled(true);
        button.setBounds(x, y, 100, 100);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(final com.badlogic.gdx.scenes.scene2d.InputEvent event, final float x, final float y) {
                stage.addAction(Actions.sequence(Actions.fadeOut(.25f),
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                AnotherManager.getInstance().setScreen(new LoadoutScreen());
                            }
                        })));
            }
        });

        stage.addActor(button);
    }

    public void createBacktoMapButton(final Stage stage, float x, float y) {
        final TextureRegion upRegion = new TextureRegion(buttonTexture);
        final TextureRegion downRegion = new TextureRegion(buttonTexture);
        final BitmapFont buttonFont = new BitmapFont();

        final TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = new TextureRegionDrawable(upRegion);
        style.down = new TextureRegionDrawable(downRegion);
        style.font = buttonFont;


        button = new TextButton("Back to map", style);
        button.setPosition(x, y);
        button.setLayoutEnabled(true);
        button.setBounds(x, y, 100, 100);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(final com.badlogic.gdx.scenes.scene2d.InputEvent event, final float x, final float y) {
                stage.addAction(Actions.sequence(Actions.fadeOut(.25f),
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                AnotherManager.getInstance().soundPlayer.stopLoadoutMusic();
                                AnotherManager.getInstance().setScreen(new MapTraverseScreen(AnotherManager.getInstance()));
                            }
                        })));
            }
        });

        stage.addActor(button);
    }

    public void createRemoveFromBarButton(final Stage stage, float x, float y) {
        final TextureRegion upRegion = new TextureRegion(buttonTexture);
        final TextureRegion downRegion = new TextureRegion(buttonTexture);
        final BitmapFont buttonFont = new BitmapFont();

        final TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = new TextureRegionDrawable(upRegion);
        style.down = new TextureRegionDrawable(downRegion);
        style.font = buttonFont;


        button = new TextButton("Remove", style);
        button.setPosition(x, y);
        button.setLayoutEnabled(true);
        button.setBounds(x, y, 100, 100);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(final com.badlogic.gdx.scenes.scene2d.InputEvent event, final float x, final float y) {
                Array<BaseItem> items = AnotherManager.getInstance().player.getInventory().getAllItems();
                for (BaseItem item : items) {
                    if (item.isAddedToActionBar()) {
                        if (item.isSelected()) {
                            item.removeFromActionBar();
                            item.unselect();
                            item.setDrawText(true);
                            AnotherManager.getInstance().player.getInventory().resortItems();
                        }
                    }
                }
            }
        });

        stage.addActor(button);
    }
}
