package de.cosh.gemlords.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Group;
import de.cosh.gemlords.GemLord;
import de.cosh.gemlords.Items.BaseItem;

/**
 * Created by cosh on 10.12.13.
 */
public class Player extends BaseCharacter {
    public boolean[] levelDone;
    private Enemy lastEnemey;
    private int lives;
    private PlayerInventory playerInventory;
    private ActionBar actionBar;
    private int lastTurnDamageReceived;

    public Player(final GemLord myGame) {
        super();
        levelDone = new boolean[200];
        for (int x = 0; x < 200; x++) {
            levelDone[x] = false;
        }
        lives = 5;
        lastTurnDamageReceived = 0;
        playerInventory = new PlayerInventory();
        actionBar = new ActionBar();
    }

    public PlayerInventory getInventory() {
        return playerInventory;
    }

    @Override
    public void addToBoard(final Group foreGround) {
        super.addToBoard(foreGround);
        setHealthBarPosition(0, 0, GemLord.VIRTUAL_WIDTH, 50);
        foreGround.addActor(getHealthBar());
        actionBar.addToBoard(foreGround);
    }

    public void decreaseLife() {
        lives--;
    }


    public void draw(final SpriteBatch batch, final float parentAlpha) {
        for (int i = 0; i < getDebuffs().size; i++) {
            getDebuffs().get(i).drawCooldown(batch, parentAlpha);
        }
        for (int i = 0; i < getBuffs().size; i++) {
            getBuffs().get(i).drawCooldown(batch, parentAlpha);
        }
        for (int i = 0; i < playerInventory.getAllItems().size(); i++) {
            BaseItem item = playerInventory.getAllItems().get(i);
            if (item.isAddedToActionBar()) {
                if (item.getItemSlotType() == BaseItem.ItemSlotType.POTION ||
                        item.getItemSlotType() == BaseItem.ItemSlotType.BOW_ACTIVE ||
                        item.getItemSlotType() == BaseItem.ItemSlotType.WATCH_ACTIVE ||
                        item.getItemSlotType() == BaseItem.ItemSlotType.TOTEM_ACTIVE)
                    item.drawCooldown(batch, parentAlpha);
            }
        }
    }

    @Override
    public void turn() {
        Enemy enemy = GemLord.getInstance().gameScreen.getBoard().getEnemy();
        GemLord.getInstance().afterActionReport.setHighestDamageDealtInOneTurn(enemy.getLastTurnDamageReceived());
        System.out.println("Enemy dmg received last turn: " + enemy.getLastTurnDamageReceived());
        enemy.clearLastTurnReceivedDamage();
        super.turn();
        for (int i = 0; i < playerInventory.getAllItems().size(); i++) {
            BaseItem currentItem = playerInventory.getAllItems().get(i);
            if (currentItem.isAddedToActionBar()) {
                if (currentItem.getItemSlotType() == BaseItem.ItemSlotType.POTION ||
                        currentItem.getItemSlotType() == BaseItem.ItemSlotType.BOW_ACTIVE) {
                    currentItem.turn();
                }
            }
        }
    }

    public Enemy getLastEnemy() {
        return lastEnemey;
    }

    public void setLastEnemy(final Enemy e) {
        lastEnemey = e;
    }

    public int getLives() {
        return lives;
    }

    @Override
    public void damage(Damage damage) {
        int finalDamage = damage.damage;
        for (BaseItem i : getInventory().getAllItems()) {
            if (!i.isAddedToActionBar())
                continue;
            if (i.getItemSlotType() == BaseItem.ItemSlotType.SHIELD ||
                    i.getItemSlotType() == BaseItem.ItemSlotType.AMULET_PASSIVE) {
                finalDamage = i.tryToReduceDamage(damage.damage);
                damage.damage = finalDamage;
            }
        }
        super.damage(damage);
        lastTurnDamageReceived += finalDamage;
        GemLord.getInstance().afterActionReport.totalDamageReceived += finalDamage;
    }

    public int getLastTurnDamageReceived() {
        return lastTurnDamageReceived;
    }

    public void levelDone(int i) {
        levelDone[i] = true;
    }

    public int getItemBuffsHP() {
        int count = 0;
        for (BaseItem i : playerInventory.getAllItems()) {
            if (i.isAddedToActionBar()) {
                if (i.getItemSlotType() == BaseItem.ItemSlotType.ROBE_ARMOR) {
                    count += i.preFirstTurnBuff(this);
                }
            }
        }
        return count;
    }

    public void increaseHealth(int hp) {
        getHealthBar().increaseHealth(hp);
        GemLord.getInstance().afterActionReport.playerTotalHealed += hp;
    }

    public void preGameIncreaseHealth(int hp) {
        getHealthBar().increaseHealth(hp);
    }

    public ActionBar getActionBar() {
        return actionBar;
    }

    public void clearLastTurnReceivedDamage() {
        lastTurnDamageReceived = 0;
    }

    public int getItemDamageBuffs(Damage damage) {
        int damageIncrease = 0;

        // first the weapons
        for (BaseItem item : playerInventory.getAllItems()) {
            if (item.isAddedToActionBar()) {
                if (item.getItemSlotType() == BaseItem.ItemSlotType.WEAPON_PASSIVE) {
                    damageIncrease += item.getAdditionalDamage(damage);
                }
            }
        }

        // then crits
        int critIncrease = 0;
        for (BaseItem item : playerInventory.getAllItems()) {
            if (item.isAddedToActionBar()) {
                critIncrease += item.getCritChanceIncrease();
            }
        }
        if (MathUtils.random(1, 100) <= critIncrease) {
            damageIncrease = damageIncrease + damage.damage;
            damage.isCrit = true;
            GemLord.soundPlayer.playCritical();
        }
        return damageIncrease;
    }

    public void loadPreferences() {
        Preferences prefs = Gdx.app.getPreferences("GemLords.pref");

        playerInventory.getAllItems().clear();
        getActionBar().clear();

            for (int i = 0; i < levelDone.length; i++) {
            levelDone[i] = prefs.getBoolean("Level" + i);
        }

        for (int i = 0; i < ActionBar.BARLENGTH; i++) {
            int itemID = prefs.getInteger("ABIS" + i);

            if (itemID == -1)
                continue;

            BaseItem actionBarItem = BaseItem.getNewItemByID(itemID);

            if( actionBarItem == null )
                continue;

            playerInventory.addItem(actionBarItem);
            getActionBar().addToActionBarAt(actionBarItem, i);
        }

        for (int i = 0; i < BaseItem.MAXIDS; i++) {
            if (prefs.getBoolean("Item ID: " + i)) {
                BaseItem item = BaseItem.getNewItemByID(i);
                playerInventory.addItem(item);
            }
        }
    }

    public void savePreferences() {
        Preferences prefs = Gdx.app.getPreferences("GemLords.pref");
        prefs.clear();

        prefs.putBoolean("Exist_v01", true);

        for (int i = 0; i < levelDone.length; i++) {
            if (levelDone[i]) {
                prefs.putBoolean("Level" + i, true);
            }
        }
        prefs.flush();

        for (int i = 0; i < ActionBar.BARLENGTH; i++) {
            BaseItem item = getActionBar().getItemInSlot(i);
            if (item == null)
                prefs.putInteger("ABIS" + i, -1);
            else {
                if( !item.isAddedToActionBar() ) {
                    prefs.putInteger("ABIS" + i, -1);
                } else {
                    prefs.putInteger("ABIS" + i, item.getID());
                }
            }
        }

        prefs.flush();

        for (BaseItem item : playerInventory.getAllNotAddedItems()) {
            prefs.putBoolean("Item ID: " + item.getID(), true);
        }

        prefs.flush();
    }

    public void clearPreferences() {
        for( int i = 0; i < 200; i++ ) {
            levelDone[i] = false;
        }
        getInventory().clearInventory();
        getActionBar().clear();

    }

    public boolean existsPreferences() {
        Preferences prefs = Gdx.app.getPreferences("GemLords.pref");
        return prefs.getBoolean("Exist_v01");
    }
}
