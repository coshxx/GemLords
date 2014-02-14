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

    public void draw(SpriteBatch batch, float parentAlpha) {
        Vector2 tangent = new Vector2(end.sub(start));
        float rotation = (float)Math.toDegrees(Math.atan2(tangent.y, tangent.x));

        float imageThickness = 4;
        float thicknessScale = thickness / imageThickness;

        Vector2 capOrigin = new Vector2(halfCircle.getWidth(), halfCircle.getHeight() / 2f);
        Vector2 middleOrigin = new Vector2(0, lightningSegment.getHeight() / 2f);
        Vector2 middleScale = new Vector2(tangent.len(), thicknessScale);
        batch.draw(lightningSegment, start.x, start.y, middleOrigin.x, middleOrigin.y,
                lightningSegment.getWidth(), lightningSegment.getHeight(), middleScale.x, middleScale.y,
                rotation);
    }
}
