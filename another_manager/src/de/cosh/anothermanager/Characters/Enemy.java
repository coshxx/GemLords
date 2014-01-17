package de.cosh.anothermanager.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

import de.cosh.anothermanager.AnotherManager;
import de.cosh.anothermanager.Abilities.Ability;
import de.cosh.anothermanager.Abilities.AbilityAttack;
import de.cosh.anothermanager.Abilities.AbilityFireball;
import de.cosh.anothermanager.Abilities.AbilityPoison;
import de.cosh.anothermanager.GUI.GUIWindow;

/**
 * Created by cosh on 10.01.14.
 */
public class Enemy extends BaseCharacter {
	private final Array<Ability> abilities;
	private transient Image enemyImage;
	private boolean isDefeated;
	private transient AnotherManager myGame;
	private transient ImageButton pointButton, pointButtonDone;
	private transient TextureRegion pointTexture, pointDoneTexture;
	private transient ImageButton.ImageButtonStyle style, styleDone;

	public Enemy(final AnotherManager myGame, final Texture texture) {
		super(myGame, 20);
		this.myGame = myGame;
		this.enemyImage = new Image(texture);

		this.isDefeated = false;
		this.myGame = myGame;

		pointTexture = new TextureRegion(myGame.assets.get("data/point.png", Texture.class));
		pointDoneTexture = new TextureRegion(myGame.assets.get("data/pointdone.png", Texture.class));

		style = new ImageButton.ImageButtonStyle();
		style.up = new TextureRegionDrawable(pointTexture);
		style.down = new TextureRegionDrawable(pointTexture);

		styleDone = new ImageButton.ImageButtonStyle();
		styleDone.up = new TextureRegionDrawable(pointDoneTexture);
		styleDone.down = new TextureRegionDrawable(pointDoneTexture);

		pointButton = new ImageButton(style.up, style.down);
		pointButtonDone = new ImageButton(styleDone.up, styleDone.down);

		abilities = new Array<Ability>();

		final AbilityAttack abilityAttack = new AbilityAttack(myGame, 10, 2, false);
		final AbilityFireball abilityFireball = new AbilityFireball(myGame, 12, 4, false);
		final AbilityPoison abilityPoison = new AbilityPoison(myGame, 0, 5, true);

		abilities.add(abilityAttack);
		abilities.add(abilityFireball);
		abilities.add(abilityPoison);
	}

	public void addPositionalButtonToMap(final float x, final float y, final Image enemyImage, final int enemyHP,
			final Stage stage) {
		this.enemyImage = enemyImage;
		final Enemy e = this;
		pointButton.setPosition(x, y);
		pointButtonDone.setPosition(x, y);
		setHealth(enemyHP);
		pointButton.addListener(new ClickListener() {

			@Override
			public void clicked(final InputEvent event, final float x, final float y) {
				myGame.soundPlayer.PlayBlub1();
				final GUIWindow guiWindow = new GUIWindow(myGame, stage);
				guiWindow.showMapEnemyWindow(enemyHP, enemyImage);
				myGame.enemyManager.setSelectedEnemy(e);
			}
		});

		stage.addActor(isDefeated ? pointButtonDone : pointButton);
	}

	@Override
	public void addToBoard(final Group foreGround) {
		super.addToBoard(foreGround);
		init(getHealth());
		enemyImage.setPosition(myGame.VIRTUAL_WIDTH / 2 - (enemyImage.getWidth() / 2), myGame.VIRTUAL_HEIGHT - 150);
		setHealthBarPosition(0, myGame.VIRTUAL_HEIGHT - (250 + 50), myGame.VIRTUAL_WIDTH, 50);

		foreGround.addActor(enemyImage);
		foreGround.addActor(getHealthBar());

		for (int i = 0; i < abilities.size; i++) {
			final Ability current = abilities.get(i);
			current.getImage().setPosition(enemyImage.getX() + (i * 55), myGame.VIRTUAL_HEIGHT - 200);
			foreGround.addActor(current.getImage());
		}
	}

	public void draw(final SpriteBatch batch, final float parentAlpha) {
		for (int i = 0; i < abilities.size; i++) {
			final Ability current = abilities.get(i);
			current.drawCooldown(batch, parentAlpha);
		}

	}

	public Image getImage() {
		return enemyImage;
	}

	public boolean isDefeated() {
		return isDefeated;
	}

	public void setDefeated(final boolean defeated) {
		isDefeated = defeated;
	}

	public void setPosition(final float x, final float y) {
		enemyImage.setPosition(x, y);
	}

	@Override
	public void turn() {
		for (int i = 0; i < abilities.size; i++) {
			final Ability current = abilities.get(i);
			if (!current.fire(myGame.player)) {
				current.turn();
			}
		}
	}
}
