package de.cosh.anothermanager.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
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
    private boolean isDefeated;
    private transient Image enemyImage;
    private transient ImageButton pointButton, pointButtonDone;
    private transient TextureRegion pointTexture, pointDoneTexture;
    private transient ImageButton.ImageButtonStyle style, styleDone;


    private Integer enemyNumber;
    private String enemyImageLocation;
    private final Array<Ability> abilities;
    private Vector2 locationOnMap;

    public Enemy() {
        super();

        this.isDefeated = false;

        pointTexture = new TextureRegion(AnotherManager.assets.get("data/point.png", Texture.class));
        pointDoneTexture = new TextureRegion(AnotherManager.assets.get("data/pointdone.png", Texture.class));

        style = new ImageButton.ImageButtonStyle();
        style.up = new TextureRegionDrawable(pointTexture);
        style.down = new TextureRegionDrawable(pointTexture);

        styleDone = new ImageButton.ImageButtonStyle();
        styleDone.up = new TextureRegionDrawable(pointDoneTexture);
        styleDone.down = new TextureRegionDrawable(pointDoneTexture);

        pointButton = new ImageButton(style.up, style.down);
        pointButtonDone = new ImageButton(styleDone.up, styleDone.down);

        abilities = new Array<Ability>();

        final AbilityAttack abilityAttack = new AbilityAttack();
        abilityAttack.setAbilityDamage(10);
        abilityAttack.setCooldown(2);

        final AbilityFireball abilityFireball = new AbilityFireball();
        abilityFireball.setAbilityDamage(12);
        abilityFireball.setCooldown(4);

        final AbilityPoison abilityPoison = new AbilityPoison();
        abilityPoison.setAbilityDamage(0);
        abilityPoison.setCooldown(5);

        abilities.add(abilityAttack);
        abilities.add(abilityFireball);
        abilities.add(abilityPoison);

        locationOnMap = new Vector2(0, 0);
    }

    public void addPositionalButtonToMap(final Vector2 mapPos, final Image enemyImage, final int enemyHP,
                                         final Stage stage, final EnemyManager enemyManager) {
        this.enemyImage = enemyImage;
        final Enemy e = this;
        pointButton.setPosition(mapPos.x, mapPos.y);
        pointButtonDone.setPosition(mapPos.x, mapPos.y);
        setHealth(enemyHP);
        pointButton.addListener(new ClickListener() {

            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                AnotherManager.soundPlayer.PlayBlub1();
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
        enemyImage.setPosition(AnotherManager.VIRTUAL_WIDTH / 2 - (enemyImage.getWidth() / 2), AnotherManager.VIRTUAL_HEIGHT - 150);
        setHealthBarPosition(0, AnotherManager.VIRTUAL_HEIGHT - (250 + 50), AnotherManager.VIRTUAL_WIDTH, 50);

        foreGround.addActor(enemyImage);
        foreGround.addActor(getHealthBar());

        for (int i = 0; i < abilities.size; i++) {
            final Ability current = abilities.get(i);
            current.getImage().setPosition(enemyImage.getX() + (i * 55), AnotherManager.VIRTUAL_HEIGHT - 200);
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

    public void turn(Player player) {
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

    public void init(int hp, String textureLocation) {
        super.init(hp);
        enemyImageLocation = textureLocation;
        enemyImage = new Image(AnotherManager.assets.get(enemyImageLocation, Texture.class));
    }

    public void loadImage() {
        enemyImage = new Image(AnotherManager.assets.get(enemyImageLocation, Texture.class));
    }

    public void setLocationOnMap(float x, float y) {
        locationOnMap.set(x, y);
    }

    public Vector2 getLocationOnMap() { return locationOnMap; }
}
