package de.cosh.gemlords.Abilities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import de.cosh.gemlords.Characters.BaseCharacter;
import de.cosh.gemlords.Characters.Damage;
import de.cosh.gemlords.GemLord;
import de.cosh.gemlords.SwapGame.*;

import java.util.ArrayList;

/**
 * Created by cosh on 15.01.14.
 */
public class AbilityLightningRod extends BaseAbility {
    private ArrayList<LightningBolt> lightningsBolts;
    private int shot;
    private int chosenGem;

    public AbilityLightningRod() {
        TextureAtlas atlas = GemLord.assets.get("data/textures/pack.atlas", TextureAtlas.class);
        abilityImageLocation = "data/textures/lightningrod.png";
        abilityImage = new Image(atlas.findRegion("lightningrod"));
        setAbilityDamage(0);
        lightningsBolts = new ArrayList<LightningBolt>();
        shot = 0;
    }

    @Override
    public boolean fire(final BaseCharacter target) {
        Cell cells[][] = GemLord.getInstance().gameScreen.getBoard().getCells();
        Group foreGround = GemLord.getInstance().gameScreen.getBoard().getGemGroup();
        Vector2 start = new Vector2(getImage().getX() + getImage().getWidth(), getImage().getY() + getImage().getHeight());
        for (int x = 0; x < Board.MAX_SIZE_X; x++) {
            for (int y = 0; y < Board.MAX_SIZE_Y; y++) {
                Gem gem = cells[x][y].getGem();
                if (gem.getGemType() == GemType.values()[chosenGem]) {
                        /*
                        StarEffect effect = new StarEffect(GemLord.getInstance());
                        effect.spawnStars(gem.getX(), gem.getY(), group);
                        */
                    Vector2 end = new Vector2(cells[x][y].getX() + cells[x][y].getWidth() / 2, cells[x][y].getY() + cells[x][y].getHeight() / 2);
                    LightningBolt bolt = new LightningBolt(start, end);
                    lightningsBolts.add(bolt);
                        /*
                        cells[x][y].setEmpty(true);
                        gem.remove();
                        gem.unmarkRemoval();
                        gem.setToNone();
                        damageCount++;

                        GemLord.getInstance().gameScreen.getBoard().getRespawnRequest().addOne(x);
                        */
                }
            }
        }
        GemLord.getInstance().gameScreen.getBoard().getGemHandler().respawn(foreGround);
        return false;
    }

    public boolean needsDraw() {
        return true;
    }

    public void turn() {
        if (getCurrentCooldown() >= 0) {
            int cd = getCurrentCooldown();
            cd--;
            setCooldown(cd);
        }
    }


    public void update(float delta) {
        passedTime += delta;
        if (passedTime > 0.1f) {
            passedTime = 0;
            if( shot == 0 ) {
                GemLord.soundPlayer.playLightningRod();
                chosenGem = MathUtils.random(0, 5);
            }
            shot++;
            lightningsBolts.clear();
            fire(GemLord.getInstance().player);
        }

        if (shot == 10) {
            shot = 0;
            needsUpdate = false;
            lightningsBolts.clear();

            Cell cells[][] = GemLord.getInstance().gameScreen.getBoard().getCells();
            Group group = GemLord.getInstance().gameScreen.getBoard().getEffectGroup();

            Damage damageCount = new Damage();
            for (int x = 0; x < Board.MAX_SIZE_X; x++) {
                for (int y = 0; y < Board.MAX_SIZE_Y; y++) {
                    Gem gem = cells[x][y].getGem();
                    if (gem.getGemType() == GemType.values()[chosenGem]) {
                        StarEffect effect = new StarEffect(GemLord.getInstance());
                        effect.spawnStars(gem.getX(), gem.getY(), group);
                        cells[x][y].setEmpty(true);
                        gem.remove();
                        gem.unmarkRemoval();
                        gem.setToNone();
                        GemLord.getInstance().gameScreen.getBoard().getRespawnRequest().addOne(x);
                        damageCount.damage++;
                    }
                }
            }

            if (MathUtils.randomBoolean()) {
                damageCount.damage += damageCount.damage;
                damageCount.isCrit = true;
            }
            GemLord.getInstance().player.damage(damageCount);
            Group gemGroup = GemLord.getInstance().gameScreen.getBoard().getGemGroup();
            setCooldown(5);
            needsUpdate = false;
            GemLord.getInstance().gameScreen.getBoard().getGemHandler().respawn(gemGroup);
            owner.setRequestMovementUpdate(true);
        }
    }



    public void drawFire(SpriteBatch batch, float parentAlpha) {
        Color color = new Color(1, 1, 1, parentAlpha);
        switch (chosenGem ) {
            case 0:
                color = new Color(0, 0, 1, parentAlpha);
                break;
            case 1:
                color = new Color(0, 1, 0, parentAlpha);
                break;
            case 2:
                color = new Color(0.7f, 0, 1f, parentAlpha);
                break;
            case 3:
                color = new Color(1, 0, 0, parentAlpha);
                break;
            case 4:
                color = new Color(1, 1, 1, parentAlpha);
                break;
            case 5:
                color = new Color(1, 1, 0, parentAlpha);
                break;
        }

        batch.setColor(color);

        for (LightningBolt bolt : lightningsBolts) {
            bolt.draw(batch, parentAlpha);
        }

        batch.setColor(1, 1, 1, parentAlpha);
    }
}
