package de.cosh.anothermanager.Abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Timer;
import de.cosh.anothermanager.Characters.BaseCharacter;
import de.cosh.anothermanager.GemLord;
import de.cosh.anothermanager.SwapGame.*;

import java.util.ArrayList;

/**
 * Created by cosh on 15.01.14.
 */
public class AbilityLightningRod extends BaseAbility {
    private ArrayList<LightningBolt> lightningsBolts;

	public AbilityLightningRod() {
		abilityImageLocation = "data/textures/lightningrod.png";
		abilityImage = new Image(GemLord.assets.get(abilityImageLocation, Texture.class));
        setAbilityDamage(0);
        lightningsBolts = new ArrayList<LightningBolt>();
	}

	@Override
	public boolean fire(final BaseCharacter target) {
		if (super.fire(target)) {
            abilityImage.addAction(Actions.sequence(Actions.scaleTo(2f, 2f, 0.15f), Actions.scaleTo(1f, 1f, 0.15f)));
            GemLord.soundPlayer.playSmash();

            Cell cells[][] = GemLord.getInstance().gameScreen.getBoard().getCells();
            Group group = GemLord.getInstance().gameScreen.getBoard().getEffectGroup();
            Group foreGround = GemLord.getInstance().gameScreen.getBoard().getGemGroup();
            ShaderProgram shader = new ShaderProgram(Gdx.files.internal("data/shaders/lightningrod.vert"), Gdx.files.internal("data/shaders/lightningrod.frag"));
            shader.pedantic = false;
            if( !shader.isCompiled() ) {
                System.out.println(shader.getLog());
            }
            GemLord.getInstance().gameScreen.getBatch().setShader(shader);

            Vector2 start = new Vector2(getImage().getX() + getImage().getWidth(), getImage().getY() + getImage().getHeight());

            int damageCount = 0;
            for( int x = 0; x < Board.MAX_SIZE_X; x++ ) {
                for( int y = 0; y < Board.MAX_SIZE_Y; y++ ) {
                    Gem gem = cells[x][y].getGem();
                    if( gem.getGemType() == GemType.TYPE_BLUE ) {
                        StarEffect effect = new StarEffect(GemLord.getInstance());
                        effect.spawnStars(gem.getX(), gem.getY(), group);
                        Vector2 end = new Vector2(cells[x][y].getX() + cells[x][y].getWidth()/2, cells[x][y].getY() + cells[x][y].getHeight()/2);
                        LightningBolt bolt = new LightningBolt(start, end);
                        lightningsBolts.add(bolt);
                        isFiring = true;
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
            return true;
		}
		return false;
	}

    public boolean needsDraw() {
        if (isFiring )
            return true;
        else return false;
    }

    public void update(float delta) {
        super.update(delta);
    }

    public void drawFire(SpriteBatch batch, float parentAlpha) {
        for( LightningBolt bolt : lightningsBolts ) {
            bolt.draw(batch, parentAlpha);
        }
    }
}
