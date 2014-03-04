package de.cosh.gemlords.Items;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.cosh.gemlords.Characters.BaseCharacter;

/**
 * Created by cosh on 20.01.14.
 */
public abstract interface UseItem {

	public abstract void onUse();

	public abstract void drawCooldown(Batch batch, float parentAlpha);

	public abstract void turn();

	public abstract void resetCooldown();

	public abstract int preFirstTurnBuff(BaseCharacter wearer);
}
