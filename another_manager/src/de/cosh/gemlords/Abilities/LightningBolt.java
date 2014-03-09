package de.cosh.gemlords.Abilities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Created by cosh on 17.02.14.
 */
public class LightningBolt {
    private Vector2 start;
    private Vector2 end;
    private ArrayList<LightningLine> lightningLines;

    public LightningBolt(Vector2 start, Vector2 end) {
        this.start = start;
        this.end = end;
        lightningLines = new ArrayList<LightningLine>();
        lightningLines.add(new LightningLine(start, end, 1f));
        float maxPerpOffset = 200f;
        float curOffset = maxPerpOffset;

        int subDivisions = 5;

        for (int i = 0; i < subDivisions; i++) {
            int currentDivisions = lightningLines.size();
            for (int j = 0; j < currentDivisions; j++) {
                Vector2 segmentStart = lightningLines.get(0).getStart();
                Vector2 segmentEnd = lightningLines.get(0).getEnd();
                lightningLines.remove(0);

                Vector2 tempStart = new Vector2(segmentStart);
                Vector2 middlePoint = new Vector2(tempStart.add(segmentEnd)).div(2);

                Vector2 tempMid = new Vector2(middlePoint);
                Vector2 dirVec = new Vector2(tempMid.sub(segmentStart)).nor();

                if (MathUtils.randomBoolean())
                    dirVec.rotate(90f);
                else dirVec.rotate(-90f);

                dirVec.scl(curOffset);
                curOffset /= 2;
                middlePoint.add(dirVec);


                LightningLine l1 = new LightningLine(new Vector2(segmentStart), new Vector2(middlePoint), 1f);
                LightningLine l2 = new LightningLine(new Vector2(middlePoint), new Vector2(segmentEnd), 1f);

                lightningLines.add(l1);
                lightningLines.add(l2);
            }
        }

    }

    public void draw(Batch batch, float parentAlpha) {
        for (LightningLine l : lightningLines)
            l.draw(batch, parentAlpha);
    }

    public Vector2 getStart() {
        return start;
    }

    public Vector2 getEnd() {
        return end;
    }
}
