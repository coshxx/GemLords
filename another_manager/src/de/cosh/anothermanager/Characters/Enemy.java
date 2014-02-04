package de.cosh.anothermanager.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

import de.cosh.anothermanager.Abilities.*;
import de.cosh.anothermanager.AnotherManager;
import de.cosh.anothermanager.GUI.GUIWindow;
import de.cosh.anothermanager.Items.*;

/**
 * Created by cosh on 10.01.14.
 */
public class Enemy extends BaseCharacter {
	private boolean isDefeated;
	private transient Image enemyImage;
	private transient TextureRegion pointTexture, pointDoneTexture;
	private transient ImageButton.ImageButtonStyle style, styleDone;

	private Image pointButton;
	private Image pointButtonDone;

	private Integer enemyNumber;
	private String enemyImageLocation;
	private final Array<BaseAbility> abilities;
	private Vector2 locationOnMap;

	private Integer dropItemID;

	public void setDropItemID(int id) {
		dropItemID = id;
	}

	public Enemy() {
		super();
		dropItemID = -1;
		this.isDefeated = false;
		pointTexture = new TextureRegion(AnotherManager.assets.get("data/textures/point.png", Texture.class));
		pointDoneTexture = new TextureRegion(AnotherManager.assets.get("data/textures/pointdone.png", Texture.class));

		style = new ImageButton.ImageButtonStyle();
		style.up = new TextureRegionDrawable(pointTexture);
		style.down = new TextureRegionDrawable(pointTexture);

		styleDone = new ImageButton.ImageButtonStyle();
		styleDone.up = new TextureRegionDrawable(pointDoneTexture);
		styleDone.down = new TextureRegionDrawable(pointDoneTexture);

		pointButton = new Image(style.up);
		pointButtonDone = new Image(styleDone.up);

		abilities = new Array<BaseAbility>();
/*
		final AbilityAttack abilityAttack = new AbilityAttack();
		abilityAttack.setAbilityDamage(10);
		abilityAttack.setCooldown(2);

		final AbilityFireball abilityFireball = new AbilityFireball();
		abilityFireball.setAbilityDamage(12);
		abilityFireball.setCooldown(4);

		final AbilityPoison abilityPoison = new AbilityPoison();
		abilityPoison.setAbilityDamage(0);
		abilityPoison.setCooldown(5);
*/
        /*
        final AbilityPetrify abilityPetrify = new AbilityPetrify();
        abilityPetrify.setAbilityDamage(0);
        abilityPetrify.setCooldown(3);
        */
/*
		abilities.add(abilityAttack);
		abilities.add(abilityFireball);
		abilities.add(abilityPoison);
*/
        //abilities.add(abilityPetrify);

		locationOnMap = new Vector2(0, 0);
	}

	public void addPositionalButtonToMap(final Vector2 mapPos, final Image enemyImage, final int enemyHP,
			final Stage stage, final EnemyManager enemyManager) {
		this.enemyImage = enemyImage;
		final Enemy e = this;
		pointButton.setBounds(mapPos.x, mapPos.y, 60, 60);
		pointButtonDone.setBounds(mapPos.x, mapPos.y, 60, 60);
		setHealth(enemyHP);
		pointButton.addListener(new ClickListener() {

			@Override
			public void clicked(final InputEvent event, final float x, final float y) {
				AnotherManager.soundPlayer.playChallenge();
				final GUIWindow guiWindow = new GUIWindow(stage);
				guiWindow.showMapEnemyWindow(enemyHP, enemyImage);
				enemyManager.setSelectedEnemy(e);
			}
		});
		stage.addActor(isDefeated ? pointButtonDone : pointButton);
	}

	@Override
	public void addToBoard(final Group foreGround) {
		super.addToBoard(foreGround);
		init(getHealth());
		//enemyImage.setPosition(AnotherManager.VIRTUAL_WIDTH / 2 - (enemyImage.getWidth() / 2), AnotherManager.VIRTUAL_HEIGHT - 150);
		enemyImage.setBounds(AnotherManager.VIRTUAL_WIDTH / 2 - (75), AnotherManager.VIRTUAL_HEIGHT - 150, 150, 150);
		setHealthBarPosition(0, AnotherManager.VIRTUAL_HEIGHT - (250 + 50), AnotherManager.VIRTUAL_WIDTH, 50);

		foreGround.addActor(enemyImage);
		foreGround.addActor(getHealthBar());

		for (int i = 0; i < abilities.size; i++) {
			final Ability current = abilities.get(i);
			current.getImage().setBounds(enemyImage.getX() + (i * 55), AnotherManager.VIRTUAL_HEIGHT - 230, 70, 70);
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
		//enemyImage.setPosition(x, y);
		enemyImage.setBounds(x, y, 140, 140);
	}

	public void turn(Player player) {
        player.clearLastTurnReceivedDamage();
        super.turn();
		for (int i = 0; i < abilities.size; i++) {
			final Ability current = abilities.get(i);
			if (!current.fire(player)) {
				current.turn();
			}
		}
	}

	public void setEnemyNumber(int n) {
		enemyNumber = n;
	}

	public int getEnemyNumber() {
		return enemyNumber;
	}

	public void loadImage() {
		enemyImage = new Image(AnotherManager.assets.get(enemyImageLocation, Texture.class));
	}

	public void setLocationOnMap(float x, float y) {
		locationOnMap.set(x, y);
	}

	public Vector2 getLocationOnMap() { return locationOnMap; }

	public BaseItem getDroppedItem() {
		switch (dropItemID) {
		case -1:
			return null;
		case 0:
			return new ItemApprenticeRobe();
		case 1:
			return new ItemMinorHealthPotion();
		case 2:
			return new ItemScholarRobe();
		case 3:
			return new ItemTotem();
		case 4:
			return new ItemPocketWatch();
        case 5:
            return new ItemTerribleShield();
		default:
			return null;
		}
	}
}
