package de.cosh.gemlords.Abilities;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import de.cosh.gemlords.GemLord;
import de.cosh.gemlords.Characters.BaseCharacter;
import de.cosh.gemlords.SwapGame.*;

/**
 * Created by cosh on 16.01.14.
 */
public class AbilityThink extends BaseAbility {
    private Board board;
    private Cell[][] cells;

	public AbilityThink() {
		abilityImageLocation = "data/textures/abilitythink.png";
        TextureAtlas atlas = GemLord.assets.get("data/textures/pack.atlas", TextureAtlas.class);
		abilityImage = new Image(atlas.findRegion("abilitythink"));
        setAbilityDamage(0);
	}

	@Override
	public boolean fire(final BaseCharacter target) {
		if (super.fire(target)) {
            board = GemLord.getInstance().gameScreen.getBoard();
            cells = board.getCells();

            SwapCommand swapCommand = board.getMatchFinder().getFirstSwapPossibility();
            if( !swapCommand.commandFound)
                return true;

            board.getSwapController().swap(swapCommand.swapStart, swapCommand.x, swapCommand.y);
            abilityImage.addAction(Actions.sequence(Actions.scaleTo(2f, 2f, 0.15f), Actions.scaleTo(1f, 1f, 0.15f)));
            owner.setRequestMovementUpdate(true);
            return true;
		}
		return false;
	}
}
