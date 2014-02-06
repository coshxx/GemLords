package de.cosh.anothermanager.Abilities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import de.cosh.anothermanager.AnotherManager;
import de.cosh.anothermanager.Characters.BaseCharacter;
import de.cosh.anothermanager.SwapGame.*;

/**
 * Created by cosh on 16.01.14.
 */
public class AbilityThink extends BaseAbility {
    private Board board;
    private Cell[][] cells;

	public AbilityThink() {
		abilityImageLocation = "data/textures/abilitythink.png";
		abilityImage = new Image(AnotherManager.assets.get(abilityImageLocation, Texture.class));
        setAbilityDamage(0);
	}

	@Override
	public boolean fire(final BaseCharacter target) {
		if (super.fire(target)) {
            board = AnotherManager.getInstance().gameScreen.getBoard();
            cells = board.getCells();

            SwapCommand swapCommand = board.getMatchFinder().getFirstSwapPossibility();
            if( !swapCommand.commandFound)
                return true;

            board.getSwapController().swap(swapCommand.swapStart, swapCommand.x, swapCommand.y);
            abilityImage.addAction(Actions.sequence(Actions.scaleTo(2f, 2f, 0.15f), Actions.scaleTo(1f, 1f, 0.15f)));
            return true;
		}
		return false;
	}
}
