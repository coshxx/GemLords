package de.cosh.gemlords.Abilities;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import de.cosh.gemlords.GemLord;
import de.cosh.gemlords.Characters.BaseCharacter;
import de.cosh.gemlords.SwapGame.Board;
import de.cosh.gemlords.SwapGame.Cell;
import de.cosh.gemlords.SwapGame.Gem;

/**
 * Created by cosh on 16.01.14.
 */
public class AbilityPetrify extends BaseAbility {
	public AbilityPetrify() {
		abilityImageLocation = "abilitypetrify";
        TextureAtlas atlas = GemLord.assets.get("data/textures/pack.atlas", TextureAtlas.class);
        abilityImage = new Image(atlas.findRegion(abilityImageLocation));
        setAbilityDamage(0);
	}

	@Override
	public boolean fire(final BaseCharacter target) {
		if (super.fire(target)) {
			abilityImage.addAction(Actions.sequence(Actions.scaleTo(2f, 2f, 0.15f), Actions.scaleTo(1f, 1f, 0.15f)));
			GemLord.soundPlayer.playPetrify();

            Cell cells[][] = GemLord.getInstance().gameScreen.getBoard().getCells();
            Group group = GemLord.getInstance().gameScreen.getBoard().getEffectGroup();

            int MAX_X = Board.MAX_SIZE_X;
            int MAX_Y = Board.MAX_SIZE_Y;
            Random r = new Random();

            Gem gem;
            do {
                int x = r.nextInt(MAX_X);
                int y = r.nextInt(MAX_Y);
                gem = cells[x][y].getGem();
            } while (gem.isTypeNone());


            TextureAtlas atlas = GemLord.assets.get("data/textures/pack.atlas", TextureAtlas.class);
            Image stoneImage = new Image(atlas.findRegion(abilityImageLocation));
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
