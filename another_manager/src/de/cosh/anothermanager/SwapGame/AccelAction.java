package de.cosh.anothermanager.SwapGame;

import java.util.Random;

import com.badlogic.gdx.scenes.scene2d.Action;

import de.cosh.anothermanager.AnotherManager;

/**
 * Created by cosh on 13.01.14.
 */
public class AccelAction extends Action {
	private final float ACCEL_FACTOR = 750f;
	private float speed;
	private final float yGoal;
	private Random r;

	public AccelAction(final float yGoal, final float speed) {
		this.yGoal = yGoal;
		this.speed = speed;
		r = new Random();
	}

	@Override
	public boolean act(final float delta) {
		int dif = r.nextInt(250);
		actor.translate(0, -(speed * delta));
		final float current = actor.getY();
		if (current < yGoal) {
			actor.setY(yGoal);
			return true;
		}
		speed += (delta * (ACCEL_FACTOR+dif));
		return false;
	}
}
