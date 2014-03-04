package de.cosh.gemlords.SwapGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import de.cosh.gemlords.GemLord;

/**
 * Created by cosh on 14.01.14.
 */
public class ParticleActor extends Actor {
	private final ParticleEffect bombEffect;
	private final ParticleEffectPool bombEffectPool;
	private final Array<ParticleEffectPool.PooledEffect> effects;

	public ParticleActor() {
		effects = new Array<ParticleEffectPool.PooledEffect>();
		bombEffect = new ParticleEffect();
        //TODO
		bombEffect.load(Gdx.files.internal("data/particles/test.p"), Gdx.files.internal("data/particles"));
		bombEffect.start();
		bombEffectPool = new ParticleEffectPool(bombEffect, 1, 2);
	}

    public void newParticleEffect(float x, float y) {
        ParticleEffectPool.PooledEffect effect = bombEffectPool.obtain();
        effect.setPosition(x, y);
        effects.add(effect);
    }

	@Override
	public void act(final float delta) {
		for (int i = effects.size - 1; i >= 0; i--) {
			final ParticleEffectPool.PooledEffect effect = effects.get(i);
			effect.update(delta);
			if (effect.isComplete()) {
				effect.free();
				effects.removeIndex(i);
			}
		}
	}

	@Override
	public void draw(Batch batch, final float parentAlpha) {
		for (int i = effects.size - 1; i >= 0; i--) {
			final ParticleEffectPool.PooledEffect effect = effects.get(i);
			effect.draw(batch);
		}
	}
}
