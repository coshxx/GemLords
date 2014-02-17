package de.cosh.anothermanager.Abilities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import de.cosh.anothermanager.GemLord;
import javafx.scene.effect.Light;

import java.util.*;

/**
 * Created by cosh on 14.02.14.
 */
public class LightningLine {
    private Vector2 start;
    private Vector2 end;
    private float thickness;

    private Sprite lightningSegment;
    private Sprite halfCircle;

    public void init() {
        start = new Vector2();
        end = new Vector2();
        lightningSegment = new Sprite(GemLord.assets.get("data/textures/lightningmid.png", Texture.class));
        halfCircle = new Sprite(GemLord.assets.get("data/textures/lightningbot.png", Texture.class));
    }

    public LightningLine(Vector2 a, Vector2 b, float thickness) {
        init();
        this.start = a;
        this.end = b;
        this.thickness = thickness;
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
