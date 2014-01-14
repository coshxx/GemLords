package de.cosh.anothermanager;

import com.badlogic.gdx.Gdx;
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
    private Sound ding;
    private Sound error;
    private Sound bang;
    private Sound awesome;
    private Sound loot;
    private Sound victorySound, loseSound;
    private Sound woosh;
    private Sound convert;
    private Sound abilityAttack;

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
        bang = myGame.assets.get("data/bang.ogg", Sound.class);

        mapMusic = myGame.assets.get("data/music.ogg", Music.class);
        woosh = myGame.assets.get("data/woosh.ogg", Sound.class);
        convert = myGame.assets.get("data/convert.ogg", Sound.class);
        ding = myGame.assets.get("data/ding.ogg", Sound.class);
        victorySound = myGame.assets.get("data/victory.ogg", Sound.class);
        loseSound = myGame.assets.get("data/boo.ogg", Sound.class);
        abilityAttack = myGame.assets.get("data/abilityattack.ogg", Sound.class);

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
        /*
        if( hits_during_current_move > 4 )
            hits_during_current_move = 4;

        dings[hits_during_current_move].play();

        System.out.println("Playing " + hits_during_current_move);
        */
        float pitch = hits_during_current_move / 10f;
        ding.play(1f, 1f+pitch, 1f);
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

    public void playVictorySound() {
        victorySound.play();
    }

    public void playLoseSound() {
        loseSound.play();
    }

    public void stopLootMusic() {
        loot.stop();
    }

    public void playBang() {
        bang.play();
    }

    public void playWoosh() {
        woosh.play();
    }

    public void playConvert() {
        convert.play();
    }

    public void playAbilityAttack() {
        abilityAttack.play();
    }
}
