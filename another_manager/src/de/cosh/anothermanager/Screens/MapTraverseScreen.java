package de.cosh.anothermanager.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Json;

import de.cosh.anothermanager.GemLord;
import de.cosh.anothermanager.Characters.Enemy;
import de.cosh.anothermanager.Characters.Player;
import de.cosh.anothermanager.GUI.GUIButton;
import de.cosh.anothermanager.Items.*;

/**
 * Created by cosh on 10.12.13.
 */
public class MapTraverseScreen implements Screen {
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
			e.setDefeated(player.levelDone[e.getEnemyNumber()]);
			e.loadImage();
			if( e.getEnemyNumber() == 0 || GemLord.DEBUGMODE) {
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

        Vector2 coords = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        coords = stage.screenToStageCoordinates(coords);
        System.out.println(coords.x + ", "+ coords.y);
		Table.drawDebug(stage);

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

		Gdx.input.setInputProcessor(stage);
		
		stage.addAction(Actions.alpha(0));
		stage.addAction(Actions.fadeIn(1));

		if( GemLord.DEBUGMODE && !GemLord.DEBUGITEMSADDED) {
            GemLord.DEBUGITEMSADDED = true;

            BaseItem item1, item2, item3, item4, item5,
                    item6, item7, item8, item9, item10, item11;
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
}
