package de.cosh.anothermanager.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import com.badlogic.gdx.utils.Json;
import de.cosh.anothermanager.AnotherManager;
import de.cosh.anothermanager.Characters.Enemy;
import de.cosh.anothermanager.Characters.Player;
import de.cosh.anothermanager.GUI.GUIButton;
import de.cosh.anothermanager.Items.ItemApprenticeRobe;
import de.cosh.anothermanager.Items.ItemMinorHealthPotion;
import de.cosh.anothermanager.Items.ItemScholarRobe;

/**
 * Created by cosh on 10.12.13.
 */
public class MapTraverseScreen implements Screen {
	public boolean enemyWindowOpen;
	private boolean fadeMusic;
	private Image mapImage;
	private final AnotherManager myGame;
    private Json json;
	private Stage stage;

	public MapTraverseScreen(final AnotherManager anotherManager) {
		myGame = anotherManager;
        json = new Json();
        stage = new Stage();
	}

	@Override
	public void dispose() {

	}

	public void fadeMusic() {
		fadeMusic = true;
	}

	@Override
	public void hide() {
		myGame.soundPlayer.stopMapMusic();
	}

	private void initEnemyLocations() {
        Player player = AnotherManager.getInstance().player;
        int counter = 0;
        while( true ) {
            FileHandle handle = Gdx.files.internal("data/enemies/enemy" + counter + ".dat");
            if( !handle.exists() )
                break;
            Enemy e = json.fromJson(Enemy.class, handle.readString());
            e.setDefeated(player.levelDone[e.getEnemyNumber()]);
            e.loadImage();
            if( e.getEnemyNumber() == 0 || AnotherManager.DEBUGMODE) {
            e.addPositionalButtonToMap(e.getLocationOnMap(), e.getImage(), e.getHealth(), stage, myGame.enemyManager);
            } else {
                int previous = e.getEnemyNumber() - 1;
                if( previous < 0 )
                    break;
                if( player.levelDone[previous] ) {
                    e.addPositionalButtonToMap(e.getLocationOnMap(), e.getImage(), e.getHealth(), stage, myGame.enemyManager);
                }
            }
            counter++;
        }
	}

	@Override
	public void pause() {

	}

	@Override
	public void render(final float delta) {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		stage.act(delta);
		stage.draw();

		if (fadeMusic) {
			myGame.soundPlayer.fadeOutMapMusic(delta);
		}
	}

	@Override
	public void resize(final int width, final int height) {
	}

	@Override
	public void resume() {

	}

	@Override
	public void show() {
        stage.clear();
		fadeMusic = false;
		enemyWindowOpen = false;

		stage.setCamera(myGame.camera);

		mapImage = new Image(myGame.assets.get("data/textures/map.png", Texture.class));
		mapImage.setBounds(0, 0, myGame.VIRTUAL_WIDTH, myGame.VIRTUAL_HEIGHT);

		stage.addActor(mapImage);
		stage.addAction(Actions.alpha(0));
		stage.addAction(Actions.fadeIn(1));

		initEnemyLocations();
        GUIButton guiButton = new GUIButton();
        guiButton.createLoadoutButton(stage, AnotherManager.VIRTUAL_WIDTH-100, 0);

		for (int x = 0; x < 5; x++) {
			final Image heartEmpty = new Image(myGame.assets.get("data/textures/heartempty.png", Texture.class));
			heartEmpty.setPosition(32 + (32 * x), myGame.VIRTUAL_HEIGHT - 64);
			stage.addActor(heartEmpty);
		}

		for (int x = 0; x < myGame.player.getLives(); x++) {
			final Image heart = new Image(myGame.assets.get("data/textures/heart.png", Texture.class));
			heart.setPosition(32 + (32 * x), myGame.VIRTUAL_HEIGHT - 64);
			stage.addActor(heart);
		}
		myGame.soundPlayer.playMapMusic();
		Gdx.input.setInputProcessor(stage);

        if( AnotherManager.DEBUGMODE ) {
            myGame.player.getInventory().addItem(new ItemMinorHealthPotion());
            myGame.player.getInventory().addItem(new ItemApprenticeRobe());
            myGame.player.getInventory().addItem(new ItemScholarRobe());
        }
	}
}
