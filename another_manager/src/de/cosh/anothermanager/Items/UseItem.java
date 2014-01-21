package de.cosh.anothermanager.Items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by cosh on 20.01.14.
 */
public abstract interface UseItem {

    public abstract void onUse();

    public abstract void drawCooldown(SpriteBatch batch, float parentAlpha);

    public abstract void turn();

    public abstract void resetCooldown();
}
