package de.cosh.anothermanager.SwapGame;

import java.util.Random;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;

/**
 * Created by cosh on 13.01.14.
 */
public class AccelAction extends Action {
	private final float ACCEL_FACTOR = 1000f;
	private float speed;
	private float translateGoal;
	private float totalTranslated;
	private Random r;

	public AccelAction(float translateGoal, final float speed) {
		this.translateGoal = translateGoal;
		this.speed = speed;
		r = new Random();
		
	}

	@Override
	public boolean act(final float delta) {
		int dif = r.nextInt(250);
		float translate = -(speed * delta);
		actor.translate(0, translate);
		totalTranslated += translate;
		final float current = actor.getY();
		if (totalTranslated <= translateGoal) {
			actor.translate(0, (translateGoal - totalTranslated));
			return true;
		}
		speed += (delta * (ACCEL_FACTOR+dif));
		return false;
	}
}
