package de.cosh.anothermanager.SwapGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

/**
 * Created by cosh on 14.01.14.
 */
public class ParticleActor extends Actor {
    private ParticleEffectPool bombEffectPool;
    private Array<ParticleEffectPool.PooledEffect> effects;
    private ParticleEffect bombEffect;

    public ParticleActor(float x, float y) {
        effects = new Array<ParticleEffectPool.PooledEffect>();
        bombEffect = new ParticleEffect();
        bombEffect.load(Gdx.files.internal("data/test.p"), Gdx.files.internal("data"));
        bombEffect.start();
        bombEffectPool = new ParticleEffectPool(bombEffect, 1, 2);
        ParticleEffectPool.PooledEffect effect = bombEffectPool.obtain();
        effect.setPosition(x, y);
        effects.add(effect);
    }

    @Override
    public void act(float delta) {
        for (int i = effects.size - 1; i >= 0; i--) {
            ParticleEffectPool.PooledEffect effect = effects.get(i);
            effect.update(delta);
            if (effect.isComplete()) {
                effect.free();
                effects.removeIndex(i);
            }
        }
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        for (int i = effects.size - 1; i >= 0; i--) {
            ParticleEffectPool.PooledEffect effect = effects.get(i);
            effect.draw(batch);
        }
    }
}
