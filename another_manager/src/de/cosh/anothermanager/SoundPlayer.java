package de.cosh.anothermanager;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by cosh on 11.12.13.
 */
public class SoundPlayer {
    private AnotherManager myGame;
    private Sound blub1, blub2;
    private Sound[] dings;
    private Sound error;
    private Sound awesome;
    private Sound loot;

    private Music mapMusic;
    private float mapMusicVolume;

    public SoundPlayer(AnotherManager game) {
        myGame = game;

    }

    public void PlayBlub1() {
        blub1.play();
    }
    public void PlayBlub2() {
        blub2.play();
    }

    public void playLootMusic() {
        loot.play();
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

        mapMusic = myGame.assets.get("data/music.ogg", Music.class);

        error = myGame.assets.get("data/error.ogg", Sound.class);

        loot = myGame.assets.get("data/loot.ogg", Sound.class);

        awesome = myGame.assets.get("data/awesome.ogg", Sound.class);
    }

    public void playMapMusic() {
        mapMusic.setLooping(true);
        mapMusicVolume = 1f;
        mapMusic.setVolume(mapMusicVolume);
        mapMusic.play();
    }

    public void stopMapMusic() {
        mapMusic.stop();
    }

    public void playDing(int hits_during_current_move) {
        if( hits_during_current_move > 4 )
            hits_during_current_move = 4;

        dings[hits_during_current_move].play();

        System.out.println("Playing " + hits_during_current_move);
    }

    public void playSwapError() {
        error.play();
    }

    public void playAwesome() {
        awesome.play();
    }

    public void fadeOutMapMusic(float delta) {
        mapMusicVolume -= delta*1.0f;
        if( mapMusicVolume <= 0 )
            mapMusicVolume = 0;
        mapMusic.setVolume(mapMusicVolume);
    }
}
