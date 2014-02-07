package de.cosh.anothermanager.CustomActions;

import com.badlogic.gdx.scenes.scene2d.Action;
import de.cosh.anothermanager.AnotherManager;

/**
 * Created by cosh on 07.02.14.
 */
public class PlayGotItemSoundAction extends Action {
    @Override
    public boolean act(float v) {
        AnotherManager.soundPlayer.playGotItem();
        return true;
    }
}
