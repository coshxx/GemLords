package de.cosh.anothermanager.Abilities;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import de.cosh.anothermanager.GemLord;

/**
 * Created by cosh on 13.02.14.
 */
public class AbilityLightningRodTimer extends Timer.Task {

    private int count = 0;
    private ShaderProgram shader;
    private float darkness = 0f;
    private Vector2 startPos;

    public AbilityLightningRodTimer(ShaderProgram shader, Vector2 startPos ) {
        this.shader = shader;
        this.startPos = startPos;
    }

    @Override
    public void run() {
        shader.begin();
        shader.setUniformf("u_darkness", darkness);
        shader.end();
        if( darkness < 0.5f )
            darkness += 0.05f;
        count++;
        if (count == 20) {
            GemLord.getInstance().gameScreen.getBatch().setShader(null);
        }
    }
}
