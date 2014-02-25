package de.cosh.gemlords.Screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Json;

import de.cosh.gemlords.GUI.GUIWindow;
import de.cosh.gemlords.GemLord;
import de.cosh.gemlords.Characters.Enemy;
import de.cosh.gemlords.Characters.Player;
import de.cosh.gemlords.GUI.GUIButton;
import de.cosh.gemlords.Items.*;

/**
 * Created by cosh on 10.12.13.
 */
public class MapTraverseScreen implements Screen, InputProcessor {
	public boolean enemyWindowOpen;
	private boolean fadeMusic;
	private Image mapImage;
	private final GemLord myGame;
	private Json json;
	private Stage stage;

	public MapTraverseScreen(final GemLord gemLord) {
		myGame = gemLord;
		json = new Json();
	}

	@Override
	public void dispose() {

	}

	public void fadeMusic() {
		fadeMusic = true;
	}

	@Override
	public void hide() {
		GemLord.soundPlayer.stopMapMusic();
		enemyWindowOpen = false;
	}

	private void initEnemyLocations() {
		Player player = GemLord.getInstance().player;
		int counter = 0;
		while( true ) {
			FileHandle handle = Gdx.files.internal("data/enemies/enemy" + counter + ".dat");
			if( !handle.exists() )
				break;
			Enemy e = json.fromJson(Enemy.class, handle.readString());
			e.setDefeated(player.levelDoneEpisode1[e.getEnemyNumber()]);
			e.loadImage();
			if( e.getEnemyNumber() == 0 || GemLord.DEBUGMODE) {
				e.addPositionalButtonToMap(e.getLocationOnMap(), e.getImage(), e.getHealth(), stage, myGame.enemyManager);
                player.setPositionOnMap(e.getLocationOnMap());
			} else {
				int previous = e.getEnemyNumber() - 1;
				if( previous < 0 )
					break;
				if( player.levelDoneEpisode1[previous] ) {
					e.addPositionalButtonToMap(e.getLocationOnMap(), e.getImage(), e.getHealth(), stage, myGame.enemyManager);
                    player.setPositionOnMap(e.getLocationOnMap());
				}
			}
			counter++;
		}

        if( player.levelDoneEpisode1[15] ) {
            if( !player.hasFullVersion() ) {
                GUIWindow guiWindow = new GUIWindow(stage);
                guiWindow.createBuyFinalWindow();
            }
        }

		if( GemLord.DEBUGMODE ) {
			Enemy e = new Enemy();
			e.setLocationOnMap(0,  0);
			e.setHealth(999);
			e.setDropItemID(-1);
			e.addPositionalButtonToMap(e.getLocationOnMap(), new Image(new Texture("data/textures/enemy.png")), 999, stage, myGame.enemyManager);
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
			GemLord.soundPlayer.fadeOutMapMusic(delta);
		}
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
		fadeMusic = false;
		enemyWindowOpen = false;

		mapImage = new Image(GemLord.assets.get("data/textures/map.jpg", Texture.class));
		mapImage.setBounds(0, 0, GemLord.VIRTUAL_WIDTH, GemLord.VIRTUAL_HEIGHT);
		stage.addActor(mapImage);

		initEnemyLocations();

		GUIButton guiButton = new GUIButton();
		guiButton.createLoadoutButton(stage, GemLord.VIRTUAL_WIDTH-100, 0);
		GemLord.soundPlayer.playMapMusic();

        InputMultiplexer plex = new InputMultiplexer();
        plex.addProcessor(stage);
        plex.addProcessor(this);
		Gdx.input.setInputProcessor(plex);

		stage.addAction(Actions.alpha(0));
		stage.addAction(Actions.fadeIn(1));

		if( GemLord.DEBUGMODE && !GemLord.DEBUGITEMSADDED) {
            GemLord.DEBUGITEMSADDED = true;

            BaseItem item1, item2, item3, item4, item5,
                    item6, item7, item8, item9, item10, item11,
                    item12, item13;
            item1 = new ItemMinorHealthPotion();
            item2 = new ItemApprenticeRobe();
            item3 = new ItemScholarRobe();
            item4 = new ItemTotem();
            item5 = new ItemDevRobe();
            item6 = new ItemPocketWatch();
            item7 = new ItemTerribleShield();
            item8 = new ItemDagger();
            item9 = new ItemBow();
            item10 = new ItemAmulet();
            item11 = new ItemRing();
            item12 = new ItemMageRobe();
            item13 = new ItemBetterShield();

			myGame.player.getInventory().addItem(item1);
			myGame.player.getInventory().addItem(item2);
			myGame.player.getInventory().addItem(item3);
			myGame.player.getInventory().addItem(item4);
			myGame.player.getInventory().addItem(item5);
			myGame.player.getInventory().addItem(item6);
            myGame.player.getInventory().addItem(item7);
            myGame.player.getInventory().addItem(item8);
            myGame.player.getInventory().addItem(item9);
            myGame.player.getInventory().addItem(item10);
            myGame.player.getInventory().addItem(item11);
            myGame.player.getInventory().addItem(item12);
            myGame.player.getInventory().addItem(item13);

            myGame.player.getActionBar().addToActionBar(item1);
            myGame.player.getActionBar().addToActionBar(item3);
            myGame.player.getActionBar().addToActionBar(item4);
            //myGame.player.getActionBar().addToActionBar(item5);
            //myGame.player.getActionBar().addToActionBar(item6);
            myGame.player.getActionBar().addToActionBar(item9);
            myGame.player.getActionBar().addToActionBar(item7);
            myGame.player.getActionBar().addToActionBar(item8);
		}
	}

    @Override
    public boolean keyDown(int keycode) {
        if( keycode == Input.Keys.ESCAPE || keycode == Input.Keys.BACK )
            GemLord.getInstance().setScreen(GemLord.getInstance().menuScreen);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
