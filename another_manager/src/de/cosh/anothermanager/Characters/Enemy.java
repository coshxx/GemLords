package de.cosh.anothermanager.Characters;

import com.badlogic.gdx.Gdx;
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
import de.cosh.anothermanager.Abilities.Ability;
import de.cosh.anothermanager.Abilities.AbilityLightningRod;
import de.cosh.anothermanager.Abilities.AbilityPetrify;
import de.cosh.anothermanager.Abilities.BaseAbility;
import de.cosh.anothermanager.GemLord;
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
    private String enemyName;

    private int lastTurnDamageReceived;

    public void setDropItemID(int id) {
        dropItemID = id;
    }

    public Enemy() {
        super();
        dropItemID = -1;
        this.isDefeated = false;
        pointTexture = new TextureRegion(GemLord.assets.get("data/textures/point.png", Texture.class));
        pointDoneTexture = new TextureRegion(GemLord.assets.get("data/textures/pointdone.png", Texture.class));

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

        final AbilityLightningRod abilityLightningRod = new AbilityLightningRod();
        abilityLightningRod.setAbilityDamage(0);

/*
		abilities.add(abilityAttack);
		abilities.add(abilityFireball);
		abilities.add(abilityPoison);
*/
        abilities.add(abilityLightningRod);
        locationOnMap = new Vector2(0, 0);
    }

    public void addPositionalButtonToMap(final Vector2 mapPos, final Image enemyImage, final int enemyHP,
                                         final Stage stage, final EnemyManager enemyManager) {
        this.enemyImage = enemyImage;
        final Enemy e = this;
        pointButton.setBounds(mapPos.x, mapPos.y, 30, 30);
        pointButtonDone.setBounds(mapPos.x, mapPos.y, 30, 30);
        setHealth(enemyHP);
        pointButton.addListener(new ClickListener() {

            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                GemLord.soundPlayer.playChallenge();
                final GUIWindow guiWindow = new GUIWindow(stage);
                guiWindow.showMapEnemyWindow(enemyHP, enemyImage, enemyName);
                enemyManager.setSelectedEnemy(e);
            }
        });
        stage.addActor(isDefeated ? pointButtonDone : pointButton);
    }

    @Override
    public void addToBoard(final Group foreGround) {
        super.addToBoard(foreGround);

        //enemyImage.setPosition(GemLord.VIRTUAL_WIDTH / 2 - (enemyImage.getWidth() / 2), GemLord.VIRTUAL_HEIGHT - 150);
        enemyImage.setBounds(GemLord.VIRTUAL_WIDTH / 2 - (75), GemLord.VIRTUAL_HEIGHT - 150, 150, 150);
        setHealthBarPosition(0, GemLord.VIRTUAL_HEIGHT - (230 + 50), GemLord.VIRTUAL_WIDTH, 50);

        foreGround.addActor(enemyImage);
        foreGround.addActor(getHealthBar());

        // don't ask
        float startX = (enemyImage.getX() + enemyImage.getWidth()/2) - ((abilities.size * 105)/2) + 25;

        for (int i = 0; i < abilities.size; i++) {
            final Ability current = abilities.get(i);
            current.getImage().setBounds(startX + (i * 105), GemLord.VIRTUAL_HEIGHT - 230, 70, 70);
            foreGround.addActor(current.getImage());
        }

    }

    public void draw(final SpriteBatch batch, final float parentAlpha) {
        for (int i = 0; i < abilities.size; i++) {
            final BaseAbility current = abilities.get(i);
            current.drawCooldown(batch, parentAlpha);

            if( current.needsDraw() )
                current.drawFire(batch, parentAlpha);
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
        GemLord.getInstance().afterActionReport.setHighestDamageReceivedInOneTurn(player.getLastTurnDamageReceived());
        System.out.println("Player last turn received damage: " + player.getLastTurnDamageReceived());
        player.clearLastTurnReceivedDamage();
        super.turn();
        for (int i = 0; i < abilities.size; i++) {
            final BaseAbility current = abilities.get(i);
            current.setOwner(this);
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
        enemyImage = new Image(new Texture(Gdx.files.internal(enemyImageLocation)));
    }

    public void setLocationOnMap(float x, float y) {
        locationOnMap.set(x, y);
    }

    @Override
    public void damage(Damage damage) {
        GemLord.getInstance().afterActionReport.totalDamgeDealt += damage.damage;
        getHealthBar().hit(damage);
        lastTurnDamageReceived += damage.damage;
    }

    public int getLastTurnDamageReceived() {
        return lastTurnDamageReceived;
    }

    public void clearLastTurnReceivedDamage() {
        lastTurnDamageReceived = 0;
    }

    public void increaseHealth(int hp) {
        getHealthBar().increaseHealth(hp);
        GemLord.getInstance().afterActionReport.enemyTotalHealed += hp;
    }


    public Vector2 getLocationOnMap() {
        return locationOnMap;
    }

    public BaseItem getDroppedItem() {
        return BaseItem.getNewItemByID(dropItemID);
    }
}
