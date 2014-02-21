package de.cosh.gemlords.Abilities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import de.cosh.gemlords.GemLord;

/**
 * Created by cosh on 14.02.14.
 */
public class LightningLine {
    private Vector2 start;
    private Vector2 end;

    private Sprite lightningSegment;

    public void init() {
        start = new Vector2();
        end = new Vector2();
        TextureAtlas atlas = GemLord.assets.get("data/textures/pack.atlas", TextureAtlas.class);
        lightningSegment = new Sprite(atlas.findRegion("lightningmid"));
    }

    public LightningLine(Vector2 a, Vector2 b, float thickness) {
        init();
        this.start = a;
        this.end = b;
    }

    public Vector2 getStart() { return start; }
    public Vector2 getEnd() { return end; }

    public void draw(SpriteBatch batch, float parentAlpha) {
        Vector2 oldEnd = new Vector2(end);
        Vector2 tangent = new Vector2(oldEnd.sub(start));
        float rotation = (float)Math.toDegrees(Math.atan2(tangent.y, tangent.x));

        batch.draw(lightningSegment, start.x, start.y, 0, 0,
                lightningSegment.getWidth(), lightningSegment.getHeight(), tangent.len(), 1f,
                rotation);
    }
}
