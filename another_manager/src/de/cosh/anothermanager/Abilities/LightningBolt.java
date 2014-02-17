package de.cosh.anothermanager.Abilities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import javafx.scene.effect.Light;

import java.util.ArrayList;

/**
 * Created by cosh on 17.02.14.
 */
public class LightningBolt {
    private Vector2 start;
    private Vector2 end;

    private ArrayList<LightningLine> lightningLines;

    public LightningBolt(Vector2 start, Vector2 end) {
        lightningLines = new ArrayList<LightningLine>();

        lightningLines.add(new LightningLine(start, end, 1f));
        float maxPerpOffset = 200f;
        float curOffset = maxPerpOffset;

        int subDivisions = 2;
        for( int i = 0; i < subDivisions; i++ ) {
            curOffset = maxPerpOffset;
            int startSize = lightningLines.size();
            for( int j = 0; j < startSize; j++ ) {
                Vector2 startPoint = new Vector2(lightningLines.get(j).getStart());
                Vector2 endPoint = new Vector2(lightningLines.get(j).getEnd());
                lightningLines.remove(j);

                Vector2 midPoint = new Vector2(endPoint.sub(startPoint)).div(2f);
                Vector2 dirVec = new Vector2(endPoint.sub(startPoint)).nor();

                if( MathUtils.randomBoolean() )
                    dirVec.rotate(90f);
                else dirVec.rotate(-90f);

                dirVec.scl(curOffset);
                midPoint.add(dirVec);
                curOffset /= 2f;

                lightningLines.add(new LightningLine(startPoint, midPoint, 1f));
                lightningLines.add(new LightningLine(midPoint, endPoint, 1f));
            }
        }

    }

    public void draw(SpriteBatch batch, float parentAlpha) {
        for( LightningLine l : lightningLines )
            l.draw(batch, parentAlpha);
    }
}
