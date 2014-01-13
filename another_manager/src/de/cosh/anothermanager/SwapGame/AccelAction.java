package de.cosh.anothermanager.SwapGame;

import com.badlogic.gdx.scenes.scene2d.Action;

/**
 * Created by cosh on 13.01.14.
 */
public class AccelAction extends Action {
    private float yGoal, speed;
    private float yTranslated;
    private final float ACCEL_FACTOR = 850f;

    public AccelAction(float yGoal, float speed ) {
        this.yGoal = yGoal;
        this.speed = speed;
        yTranslated = 0f;
    }
    @Override
    public boolean act(float delta) {
        actor.translate(0, -(speed * delta));

        float current = actor.getY();
        if( current < yGoal ) {
            actor.setY(yGoal);
            return true;
        }
        speed += (delta * ACCEL_FACTOR);
        return false;
    }
}

