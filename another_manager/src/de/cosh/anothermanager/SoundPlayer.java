package de.cosh.anothermanager;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by cosh on 11.12.13.
 */
public class SoundPlayer {
    private AnotherManager myGame;
    private Sound blub1, blub2;
    private Sound[] dings;


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

        dings = new Sound[5];

        dings[0] = myGame.assets.get("data/ding1.ogg", Sound.class);
        dings[1] = myGame.assets.get("data/ding2.ogg", Sound.class);
        dings[2] = myGame.assets.get("data/ding3.ogg", Sound.class);
        dings[3] = myGame.assets.get("data/ding4.ogg", Sound.class);
        dings[4] = myGame.assets.get("data/ding5.ogg", Sound.class);
    }

    public void PlayDing(int hits_during_current_move) {
        if( hits_during_current_move > 4 )
            hits_during_current_move = 4;

        dings[hits_during_current_move].play();

        System.out.println("Playing " + hits_during_current_move);
    }
}
