package de.cosh.anothermanager.Abilities;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import de.cosh.anothermanager.AnotherManager;
import de.cosh.anothermanager.Characters.BaseCharacter;
import de.cosh.anothermanager.SwapGame.Board;
import de.cosh.anothermanager.SwapGame.Cell;
import de.cosh.anothermanager.SwapGame.Gem;

/**
 * Created by cosh on 16.01.14.
 */
public class AbilityPetrify extends BaseAbility {
	public AbilityPetrify() {
		abilityImageLocation = "data/textures/abilitypetrify.png";
		abilityImage = new Image(AnotherManager.assets.get(abilityImageLocation, Texture.class));
	}

	@Override
	public boolean fire(final BaseCharacter target) {
		if (super.fire(target)) {
			abilityImage.addAction(Actions.sequence(Actions.scaleTo(2f, 2f, 0.15f), Actions.scaleTo(1f, 1f, 0.15f)));
			AnotherManager.soundPlayer.playPetrify();

            Cell cells[][] = AnotherManager.getInstance().gameScreen.getBoard().getCells();
            Group group = AnotherManager.getInstance().gameScreen.getBoard().getEffectGroup();

            int MAX_X = Board.MAX_SIZE_X;
            int MAX_Y = Board.MAX_SIZE_Y;
            Random r = new Random();

            Gem gem;
            do {
                int x = r.nextInt(MAX_X);
                int y = r.nextInt(MAX_Y);
                x = 4;
                y = 4;
                setCooldown(10);
                gem = cells[x][y].getGem();
            } while (gem.isDisabled() || gem.isTypeNone());

            gem.disable();
            gem.addAction(Actions.parallel(
                    Actions.moveBy(gem.getWidth()/2, gem.getHeight()/2, 0.5f),
                    Actions.scaleTo(0, 0, 0.5f)
            ));
            Image stoneImage = new Image(AnotherManager.assets.get(abilityImageLocation, Texture.class));
            stoneImage.setBounds(gem.getX()+gem.getWidth()/2, gem.getY() + gem.getHeight()/2, 80, 80);
             stoneImage.addAction(Actions.scaleTo(0, 0));
            stoneImage.addAction(Actions.parallel(
                    Actions.moveBy(-gem.getWidth() / 2, -gem.getHeight() / 2, 0.5f),
                    Actions.scaleTo(1, 1, 0.5f)
            ));
            group.addActor(stoneImage);
            return true;
		}
		return false;
	}
}
