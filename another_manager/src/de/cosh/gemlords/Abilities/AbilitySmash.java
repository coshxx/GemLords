package de.cosh.gemlords.Abilities;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import de.cosh.gemlords.Characters.Damage;
import de.cosh.gemlords.GemLord;
import de.cosh.gemlords.Characters.BaseCharacter;
import de.cosh.gemlords.SwapGame.Board;
import de.cosh.gemlords.SwapGame.Cell;
import de.cosh.gemlords.SwapGame.Gem;
import de.cosh.gemlords.SwapGame.GemType;
import de.cosh.gemlords.SwapGame.StarEffect;

import java.util.Random;

/**
 * Created by cosh on 16.01.14.
 */
public class AbilitySmash extends BaseAbility {
	public AbilitySmash() {
		abilityImageLocation = "abilitysmash";
        TextureAtlas atlas = GemLord.assets.get("data/textures/pack.atlas", TextureAtlas.class);
		abilityImage = new Image(atlas.findRegion(abilityImageLocation));
        setAbilityDamage(0);
	}

	@Override
	public boolean fire(final BaseCharacter target) {
		if (super.fire(target)) {
			abilityImage.addAction(Actions.sequence(Actions.scaleTo(2f, 2f, 0.15f), Actions.scaleTo(1f, 1f, 0.15f)));
			GemLord.soundPlayer.playSmash();

            Cell cells[][] = GemLord.getInstance().gameScreen.getBoard().getCells();
            Group group = GemLord.getInstance().gameScreen.getBoard().getEffectGroup();
            Group foreGround = GemLord.getInstance().gameScreen.getBoard().getGemGroup();
            
            int damageCount = 0;

            GemType randomType = GemType.values()[MathUtils.random(0, 5)];


            for( int x = 0; x < Board.MAX_SIZE_X; x++ ) {
            	for( int y = 0; y < Board.MAX_SIZE_Y; y++ ) {
            		Gem gem = cells[x][y].getGem();
            		if( gem.getGemType() == randomType) {
                    	StarEffect effect = new StarEffect(GemLord.getInstance());
        				effect.spawnStars(gem.getX(), gem.getY(), group);
        				cells[x][y].setEmpty(true);
        				gem.remove();
        				gem.unmarkRemoval();
        				gem.setToNone();
        				damageCount++;
        				GemLord.getInstance().gameScreen.getBoard().getRespawnRequest().addOne(x);
            		}
            	}
            }
            Damage dealDamage = new Damage();
            dealDamage.damage = damageCount;
            GemLord.getInstance().player.damage(dealDamage);
            GemLord.getInstance().gameScreen.getBoard().getGemHandler().respawn(foreGround);
            owner.setRequestMovementUpdate(true);
            return true;
		}
		return false;
	}
}
