package de.cosh.gemlords.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
import de.cosh.gemlords.Abilities.AbilityLightningRod;
import de.cosh.gemlords.Abilities.BaseAbility;
import de.cosh.gemlords.GUI.GUIWindow;
import de.cosh.gemlords.GemLord;
import de.cosh.gemlords.Items.BaseItem;
import de.cosh.gemlords.SwapGame.Board;

/**
 * Created by cosh on 10.01.14.
 */
public class Enemy extends BaseCharacter {
    private final Array<BaseAbility> abilities;
    private boolean isDefeated;
    private transient Image enemyImage;
    private transient TextureRegion pointTexture, pointDoneTexture;
    private transient ImageButton.ImageButtonStyle style, styleDone;
    private Image pointButton;
    private Image pointButtonDone;
    private Integer enemyNumber;
    private String enemyImageLocation;
    private Vector2 locationOnMap;
    private Integer dropItemID;
    private String enemyName;
    private boolean allAbilitiesDone;
    private boolean boardNeedsMovementUpdate;

    private int lastTurnDamageReceived;

    public Enemy() {
        super();
        dropItemID = -1;
        this.isDefeated = false;
        TextureAtlas atlas = GemLord.assets.get("data/textures/pack.atlas", TextureAtlas.class);
        pointTexture = new TextureRegion(atlas.findRegion("point"));
        pointDoneTexture = new TextureRegion(atlas.findRegion("pointdone"));

        style = new ImageButton.ImageButtonStyle();
        style.up = new TextureRegionDrawable(pointTexture);
        style.down = new TextureRegionDrawable(pointTexture);

        styleDone = new ImageButton.ImageButtonStyle();
        styleDone.up = new TextureRegionDrawable(pointDoneTexture);
        styleDone.down = new TextureRegionDrawable(pointDoneTexture);

        pointButton = new Image(style.up);
        pointButtonDone = new Image(styleDone.up);

        abilities = new Array<BaseAbility>();
        final AbilityLightningRod abilityLightningRod = new AbilityLightningRod();

        abilityLightningRod.setAbilityDamage(0);
        abilityLightningRod.setOwner(this);
        abilities.add(abilityLightningRod);

        locationOnMap = new Vector2(0, 0);

        allAbilitiesDone = false;
    }

    public void setDropItemID(int id) {
        dropItemID = id;
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
        float startX = (enemyImage.getX() + enemyImage.getWidth() / 2) - ((abilities.size * 105) / 2) + 25;

        for (int i = 0; i < abilities.size; i++) {
            BaseAbility current = abilities.get(i);
            current.getImage().setBounds(startX + (i * 105), GemLord.VIRTUAL_HEIGHT - 230, 70, 70);
            foreGround.addActor(current.getImage());
            current.setOwner(this);
        }

    }

    public void draw(final SpriteBatch batch, float parentAlpha) {
        for (int i = 0; i < abilities.size; i++) {
            final BaseAbility current = abilities.get(i);
            current.drawCooldown(batch, parentAlpha);

            if (current.needsDraw())
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
        for (int i = 0; i < abilities.size; i++) {
            BaseAbility ability = abilities.get(i);
            ability.setNeedsUpdate(true);
            ability.turn();
        }
        super.turn();
        allAbilitiesDone = false;
    }

    public void update(float delta) {
        if (!(GemLord.getInstance().gameScreen.getBoard().getBoardState() == Board.BoardState.STATE_IDLE)) {
            return;
        }
        boolean allDone = true;

        for (int i = 0; i < abilities.size; i++) {
            BaseAbility ability = abilities.get(i);
            if (ability.getCurrentCooldown() <= 0) {
                if (ability.needsUpdate()) {
                    ability.update(delta);
                    allDone = false;
                    break;
                }
            }
        }

        if (allDone) {
            allAbilitiesDone = true;
        }
    }

    public int getEnemyNumber() {
        return enemyNumber;
    }

    public void setEnemyNumber(int n) {
        enemyNumber = n;
    }

    public void loadImage() {
        TextureAtlas atlas = GemLord.assets.get("data/textures/pack.atlas", TextureAtlas.class);
        enemyImage = new Image(new TextureRegion(atlas.findRegion(enemyImageLocation)));
    }

    public void setLocationOnMap(float x, float y) {
        locationOnMap.set(x, y);
    }

    @Override
    public void damage(Damage damage) {
        for( int i = 0; i < abilities.size; i++ ) {
            BaseAbility ability = abilities.get(i);

            if( ability.tryDodge() ) {
                return;
            }

            ability.tryReduce(damage);
        }

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

    public boolean allAbilitiesDone() {
        return allAbilitiesDone;
    }

    public boolean requestBoardMovementUpdate() {
        return boardNeedsMovementUpdate;
    }

    public void setRequestMovementUpdate(boolean b) {
        boardNeedsMovementUpdate = b;
    }

    public void tryIncreaseDamage(Damage damage) {
        for( int i = 0; i < abilities.size; i++ ) {
            BaseAbility current = abilities.get(i);
            damage.damage += current.tryIncreaseDamage();
        }
    }
}
