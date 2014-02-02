package de.cosh.anothermanager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by cosh on 10.12.13.
 */
class RectActor extends Actor {
	private final ShapeRenderer shapeRenderer;
	private final float x, y, width, height;

	public RectActor(final float x, final float y, final float width, final float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		shapeRenderer = new ShapeRenderer();
	}

	@Override
	public void draw(final SpriteBatch batch, final float parentAlpha) {
		batch.end();
		final Color color = getColor();
		shapeRenderer.setColor(color.r * parentAlpha, color.g * parentAlpha, color.b * parentAlpha, color.a
				* parentAlpha);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.rect(x, y, width, height);
		shapeRenderer.end();
		batch.begin();
	}
}
