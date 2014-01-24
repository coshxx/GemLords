package de.cosh.anothermanager.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import de.cosh.anothermanager.AnotherManager;
import de.cosh.anothermanager.Characters.ActionBar;
import de.cosh.anothermanager.GUI.GUIButton;
import de.cosh.anothermanager.Items.BaseItem;

/**
 * Created by cosh on 07.01.14.
 */
public class LoadoutScreen implements Screen {
    private Stage stage;

    public LoadoutScreen() {
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
        stage.setCamera(AnotherManager.getInstance().camera);

        Image background = new Image(AnotherManager.assets.get("data/textures/loadout.jpg", Texture.class));
        background.setBounds(0, 0, AnotherManager.VIRTUAL_WIDTH, AnotherManager.VIRTUAL_HEIGHT);
        stage.addActor(background);

        stage.addAction(Actions.alpha(0.0f));
        stage.addAction(Actions.fadeIn(1.0f));

        AnotherManager.getInstance();
		AnotherManager.soundPlayer.playLoadoutMusic();

        GUIButton guiButton = new GUIButton();
        guiButton.createBacktoMapButton(stage, AnotherManager.VIRTUAL_WIDTH-100, 0);
        guiButton.createRemoveFromBarButton(stage, 0, 0);

        ActionBar actionBar = AnotherManager.getInstance().player.getActionBar();
        actionBar.addToLoadoutScreen(stage);
        AnotherManager.getInstance().player.getInventory().addToLoadoutScreen(stage);
        AnotherManager.getInstance().player.getInventory().resortItems();

        /*
        final ActionBar actionBar = new ActionBar(ActionBar.ActionBarMode.LOADOUT);
        actionBar.addToStage(stage);

        int counter = 0;
        Array<BaseItem> items = AnotherManager.getInstance().player.getInventoryItems();
        if( items.size > 0 ) {
        for( int x = 0; x < 4; x++ ) {
            for( int y = 0; y < 6; y++ ) {
                if( counter >= items.size )
                    break;
                final BaseItem i = items.get(counter);
                if( i.isAddedToActionBar() )
                    continue;
                i.setPosition( 70 + ( (x * i.getWidth() ) + ( x * 100 )), (AnotherManager.VIRTUAL_HEIGHT-80) - ( y * 140 ) );
                i.addListener(new ClickListener() {
                    public void clicked(InputEvent e, float x, float y) {
                        if (i.isSelected() ) {
                            i.unselect();
                            return;
                        }
                        i.selected();
                        Array<BaseItem> items = AnotherManager.getInstance().player.getInventoryItems();
                        for( BaseItem otherItem : items )
                            if( otherItem != i )
                                otherItem.unselect();
                    }
                });
                stage.addActor(i);
                counter++;
            }
        }
        }
        */
        Gdx.input.setInputProcessor(stage);
    }
}
