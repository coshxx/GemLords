package de.cosh.anothermanager.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

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
	private Image chestImage;
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
		stage.setViewport(width, height, false);
	}

	@Override
	public void resume() {

	}

	@Override
	public void show() {
		stage = new Stage();
		table = new Table();
		table.setFillParent(true);
		table.center().top();
		chestImage = new Image(AnotherManager.assets.get("data/textures/treasure.jpg", Texture.class));
		table.add(chestImage);
		table.row();
		
		AnotherManager.getInstance().soundPlayer.playLootMusic();
		
		final BaseItem i = enemyManager.getSelectedEnemy().getDroppedItem();
		if( i != null ) {
			i.addAction(Actions.alpha(0));
			i.addAction(Actions.fadeIn(5.0f));
			table.add(i).pad(50).top();
		}
		table.row();
		TextButton button = new TextButton("Return to map", skin);
		button.addListener(new ClickListener() {
			@Override
			public void clicked(final InputEvent event, final float x, final float y) {
				if (i != null)
					i.addAction(Actions.moveBy(-AnotherManager.VIRTUAL_WIDTH, 0, 0.25f));
				stage.addAction(Actions.sequence(Actions.fadeOut(0.5f), Actions.run(new Runnable() {
					@Override
					public void run() {
						AnotherManager.soundPlayer.stopLootMusic();
						myGame.player.levelDone(myGame.enemyManager.getSelectedEnemy().getEnemyNumber());
						if( i != null )
							myGame.player.getInventory().addItem(i);
						myGame.setScreen(myGame.mapTraverseScreen);
					}
				})));
			}
		});
		table.add(button).pad(100).size(200, 50);
		stage.addActor(table);
		Gdx.input.setInputProcessor(stage);
	}
}
