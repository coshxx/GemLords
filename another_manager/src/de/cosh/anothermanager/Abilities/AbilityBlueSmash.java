package de.cosh.anothermanager.Abilities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import de.cosh.anothermanager.GemLord;
import de.cosh.anothermanager.Characters.BaseCharacter;
import de.cosh.anothermanager.SwapGame.*;

/**
 * Created by cosh on 16.01.14.
 */
public class AbilityBlueSmash extends BaseAbility {
	public AbilityBlueSmash() {
		abilityImageLocation = "data/textures/abilitysmashblue.png";
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
            		if( gem.getGemType() == GemType.TYPE_BLUE ) {
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
