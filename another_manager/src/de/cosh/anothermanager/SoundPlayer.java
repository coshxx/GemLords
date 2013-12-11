package de.cosh.anothermanager;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by cosh on 11.12.13.
 */
public class SoundPlayer {
    private AnotherManager myGame;
    private Sound blub1, blub2;


    public SoundPlayer(AnotherManager game) {
        myGame = game;

    }

    public void PlayBlub1() {
        blub1.play();
    }
    public void PlayBlub2() {
        blub2.play();
    }

    public void touchSounds() {
        blub1 = myGame.assets.get("data/blub1.ogg", Sound.class);
        blub2 = myGame.assets.get("data/blub2.ogg", Sound.class);
    }
}
