package de.cosh.anothermanager.Abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import de.cosh.anothermanager.Characters.BaseCharacter;
import de.cosh.anothermanager.Characters.Damage;
import de.cosh.anothermanager.GemLord;
import de.cosh.anothermanager.SwapGame.*;

import java.util.ArrayList;

/**
 * Created by cosh on 15.01.14.
 */
public class AbilityLightningRod extends BaseAbility {
    private ArrayList<LightningBolt> lightningsBolts;
    private int shot;

    public AbilityLightningRod() {
        abilityImageLocation = "data/textures/lightningrod.png";
        abilityImage = new Image(GemLord.assets.get(abilityImageLocation, Texture.class));
        setAbilityDamage(0);
        lightningsBolts = new ArrayList<LightningBolt>();
        shot = 0;
    }

    @Override
    public boolean fire(final BaseCharacter target) {
        Cell cells[][] = GemLord.getInstance().gameScreen.getBoard().getCells();

        Group foreGround = GemLord.getInstance().gameScreen.getBoard().getGemGroup();
        ShaderProgram shader = new ShaderProgram(Gdx.files.internal("data/shaders/lightningrod.vert"), Gdx.files.internal("data/shaders/lightningrod.frag"));
        shader.pedantic = false;
        if (!shader.isCompiled()) {
            System.out.println(shader.getLog());
        }
        GemLord.getInstance().gameScreen.getBatch().setShader(shader);

        Vector2 start = new Vector2(getImage().getX() + getImage().getWidth(), getImage().getY() + getImage().getHeight());
        for (int x = 0; x < Board.MAX_SIZE_X; x++) {
            for (int y = 0; y < Board.MAX_SIZE_Y; y++) {
                Gem gem = cells[x][y].getGem();
                if (gem.getGemType() == GemType.TYPE_BLUE) {
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
        GemLord.getInstance().gameScreen.getBoard().getGemHandler().respawnAndApplyGravity(foreGround);
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
        if( getCurrentCooldown() != -1 ) {
            needsUpdate = false;
            return;
        }
        passedTime += delta;
        if (passedTime > 0.1f) {
            passedTime = 0;
            if( shot == 0 ) {
                GemLord.soundPlayer.playLightningRod();
            }
            shot++;
            lightningsBolts.clear();
            fire(GemLord.getInstance().player);
        }

        if (shot == 5) {
            shot = 0;
            needsUpdate = false;
            lightningsBolts.clear();

            Cell cells[][] = GemLord.getInstance().gameScreen.getBoard().getCells();
            Group group = GemLord.getInstance().gameScreen.getBoard().getEffectGroup();

            Damage damageCount = new Damage();
            for (int x = 0; x < Board.MAX_SIZE_X; x++) {
                for (int y = 0; y < Board.MAX_SIZE_Y; y++) {
                    Gem gem = cells[x][y].getGem();
                    if (gem.getGemType() == GemType.TYPE_BLUE) {
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
            GemLord.getInstance().gameScreen.getBoard().getGemHandler().respawnAndApplyGravity(gemGroup);
            setCooldown(5);
            needsUpdate = false;
        }
    }

    public void drawFire(SpriteBatch batch, float parentAlpha) {
        for (LightningBolt bolt : lightningsBolts) {
            bolt.draw(batch, parentAlpha);
        }
    }
}
