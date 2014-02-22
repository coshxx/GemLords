package de.cosh.gemlords.CustomActions;

import com.badlogic.gdx.scenes.scene2d.Action;
import de.cosh.gemlords.GemLord;

/**
 * Created by cosh on 07.02.14.
 */
public class EndSplashScreenAction extends Action {
    @Override
    public boolean act(float v) {
        GemLord.soundPlayer.touchSounds();
        GemLord.soundPlayer.stopSplash();
        GemLord.getInstance().setScreen(GemLord.getInstance().menuScreen);
        return true;
    }
}
