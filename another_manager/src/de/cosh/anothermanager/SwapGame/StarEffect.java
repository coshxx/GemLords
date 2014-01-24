package de.cosh.anothermanager.SwapGame;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import de.cosh.anothermanager.AnotherManager;

/**
 * Created by cosh on 06.01.14.
 */
public class StarEffect {
	private final AnotherManager myGame;
	private final Random r;

	public StarEffect(final AnotherManager myGame) {
		this.myGame = myGame;
		r = new Random();
	}

	public void spawnStars(final float x, final float y, final Group foreGround) {
		for (int d = 0; d < 5; d++) {
			final Image star = new Image(AnotherManager.assets.get("data/textures/star.png", Texture.class));
			final boolean left = r.nextBoolean();
			float duration = 800 + r.nextInt(1400);
			float xAmount = r.nextInt(1000);
			final float rot = r.nextInt(1000);
			duration /= 1000;
			if (left) {
				xAmount = -xAmount;
			}

			star.setPosition(x, y);
			star.addAction(Actions.sequence(
					Actions.parallel(Actions.moveBy(xAmount, -700f, duration), Actions.rotateBy(rot, duration),
							Actions.fadeOut(2f)), Actions.removeActor()));
			foreGround.addActor(star);
		}
	}
}
