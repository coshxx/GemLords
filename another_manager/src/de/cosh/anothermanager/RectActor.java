package de.cosh.anothermanager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by cosh on 10.12.13.
 */
public class RectActor extends Actor {
    private float x, y, width, height;
    private ShapeRenderer shapeRenderer;

    public RectActor(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void draw (SpriteBatch batch, float parentAlpha) {
        batch.end();
        Color color = getColor();
        shapeRenderer.setColor(color.r * parentAlpha, color.g * parentAlpha, color.b * parentAlpha, color.a * parentAlpha);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.end();
        batch.begin();
    }
}
