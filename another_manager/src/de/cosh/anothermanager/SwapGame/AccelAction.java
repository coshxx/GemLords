package de.cosh.anothermanager.SwapGame;

import com.badlogic.gdx.scenes.scene2d.Action;
import de.cosh.anothermanager.AnotherManager;

/**
 * Created by cosh on 13.01.14.
 */
public class AccelAction extends Action {
	private final float ACCEL_FACTOR = 1000f;
	private float speed;
	private final float yGoal;

	public AccelAction(final float yGoal, final float speed) {
		this.yGoal = yGoal;
		this.speed = speed;
	}

	@Override
	public boolean act(final float delta) {
		actor.translate(0, -(speed * delta));

		final float current = actor.getY();
		if (current < yGoal) {
			actor.setY(yGoal);
			return true;
		}
		speed += (delta * ACCEL_FACTOR);
		return false;
	}
}
