package de.cosh.anothermanager.Abilities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import de.cosh.anothermanager.GemLord;
import de.cosh.anothermanager.Characters.BaseCharacter;
import de.cosh.anothermanager.SwapGame.Board;
import de.cosh.anothermanager.SwapGame.Cell;
import de.cosh.anothermanager.SwapGame.Gem;
import de.cosh.anothermanager.SwapGame.GemType;
import de.cosh.anothermanager.SwapGame.StarEffect;

/**
 * Created by cosh on 16.01.14.
 */
public class AbilityRedSmash extends BaseAbility {
	public AbilityRedSmash() {
		abilityImageLocation = "data/textures/abilitysmashred.png";
		abilityImage = new Image(GemLord.assets.get(abilityImageLocation, Texture.class));
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
            for( int x = 0; x < Board.MAX_SIZE_X; x++ ) {
            	for( int y = 0; y < Board.MAX_SIZE_Y; y++ ) {
            		Gem gem = cells[x][y].getGem();
            		if( gem.getGemType() == GemType.TYPE_RED ) {
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
            GemLord.getInstance().gameScreen.getBoard().getGemHandler().respawnAndApplyGravity(foreGround);
            return true;
		}
		return false;
	}
}
