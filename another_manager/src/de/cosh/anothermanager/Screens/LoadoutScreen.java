package de.cosh.anothermanager.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import de.cosh.anothermanager.AnotherManager;
import de.cosh.anothermanager.Characters.ActionBar;
import de.cosh.anothermanager.GUI.GUIButton;
import de.cosh.anothermanager.Items.ItemApprenticeRobe;

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

        stage.addAction(Actions.alpha(0.0f));
        stage.addAction(Actions.fadeIn(1.0f));

        GUIButton backtomap = new GUIButton();
        backtomap.createBacktoMapButton(stage, AnotherManager.VIRTUAL_WIDTH-100, 0);
        /*
        for( BaseItem i : AnotherManager.getInstance().player.getInventoryItems() ) {
            i.setPosition(AnotherManager.VIRTUAL_WIDTH-200, 400 );
            stage.addActor(i);
        }
        */

        for( int x = 0; x < 4; x++ ) {
            for( int y = 0; y < 6; y++ ) {
                ItemApprenticeRobe i = new ItemApprenticeRobe();
                i.setPosition( 70 + ( (x * i.getWidth() ) + ( x * 100 )), (AnotherManager.VIRTUAL_HEIGHT-80) - ( y * 140 ) );
                stage.addActor(i);
            }
        }
        ActionBar actionBar = new ActionBar();
        stage.addActor(actionBar);
        Gdx.input.setInputProcessor(stage);
    }
}
