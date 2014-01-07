package de.cosh.anothermanager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.Random;

/**
 * Created by cosh on 06.01.14.
 */
public class StarEffect {
    private AnotherManager myGame;
    private Random r;

    public StarEffect(AnotherManager myGame) {
        this.myGame = myGame;
        r = new Random();
    }

    public void spawnStars(float x, float y, Group foreGround) {
        for (int d = 0; d < 3; d++) {
            Image star = new Image(myGame.assets.get("data/star.png", Texture.class));
            boolean left = r.nextBoolean();
            float duration = 600 + r.nextInt(1400);
            float xAmount = r.nextInt(1000);
            float rot = r.nextInt(1000);
            duration /= 1000;
            if (left)
                xAmount = -xAmount;

            star.setPosition(x, y);
            star.addAction(Actions.sequence(
                    Actions.parallel(Actions.moveBy(xAmount, -800f, duration), Actions.rotateBy(rot, duration)
                    ),
                    Actions.removeActor()
            ));
            foreGround.addActor(star);
        }
    }
}
