package de.cosh.anothermanager;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.Random;

/**
 * Created by cosh on 11.12.13.
 */
public class SoundPlayer {
	private Sound abilityAttack;
	private Sound abilityPoison;
	private Sound awesome;
	private Sound bang;
	private Sound blub1, blub2;
	private Sound convert;
	private Sound ding;
	private Sound error;
	private Sound fireball_start;
	private Sound loot;
	private Sound gulp;
	private Sound challenge;
	private Sound clawSound;
	private Sound totem;
	private Sound slideIn;
    private Sound petrify;
    private Sound pocketwatch;

	private Sound impressive, godlike, unstoppable;

	private Music mapMusic;
	private Music loadoutMusic;
	private Music menuMusic;
	private Music gameMusic0;
	private float mapMusicVolume;
	private Sound victorySound, loseSound;
	private Sound woosh;
	private Sound smash;

	public SoundPlayer(final AnotherManager game) {
	}

	public void fadeOutMapMusic(final float delta) {
		mapMusicVolume -= delta * 1.0f;
		if (mapMusicVolume <= 0) {
			mapMusicVolume = 0;
		}
		mapMusic.setVolume(mapMusicVolume);
	}

	public void playAbilityAttack() {
		abilityAttack.play(0.4f);
	}

	public void playTotem() {
		totem.play();
	}

	public void playImpressive() {
		impressive.play(0.3f);
	}

	public void playUnstoppable() {
		unstoppable.play(0.3f);
	}

	public void playGodlike() {
		godlike.play(0.3f);
	}

	public void playAwesome() {
		awesome.play();
	}

	public void playBang() {
		bang.play();
	}

	public void PlayBlub1() {
		blub1.play();
	}

	public void PlayBlub2() {
		blub2.play();
	}

	public void playConvert() {
		convert.play();
	}
	public void playSlideIn() {
		slideIn.play(0.05f);
	}

	public void playDing(final int hits_during_current_move) {
		final float pitch = hits_during_current_move / 10f;
		ding.play(1f, 1f + pitch, 1f);
	}

	public void playFireballStart() {
		fireball_start.play();
	}

	public void playLootMusic() {
		loot.play();
	}

	public void playLoseSound() {
		loseSound.play();
	}

	public void playMapMusic() {
		mapMusic.setVolume(0.2f);
		mapMusic.play();
	}

	public void playPoisonSound() {
		abilityPoison.play(0.3f);
	}

	public void playSwapError() {
		error.play();
	}

    public void playPetrify() {
        petrify.play();
    }

	public void playVictorySound() {
		victorySound.play();
	}

	public void playWoosh() {
		woosh.play();
	}

	public void stopLootMusic() {
		loot.stop();
	}

	public void stopMapMusic() {
		mapMusic.stop();
	}

	public void playLoadoutMusic() {
		loadoutMusic.setVolume(0.2f);
		loadoutMusic.play();
	}

	public void playGameMusic() {
        Random r = new Random();
        if( r.nextInt(2) == 1 )
		    gameMusic0.play();
        else menuMusic.play();
	}

	public void stopGameMusic() {
		gameMusic0.stop();
        menuMusic.stop();
	}

	public void touchSounds() {
		AssetManager assets = AnotherManager.assets;
		blub1 = assets.get("data/sounds/blub1.ogg", Sound.class);
		blub2 = assets.get("data/sounds/blub2.ogg", Sound.class);

		bang = assets.get("data/sounds/bang.ogg", Sound.class);

		mapMusic = assets.get("data/sounds/music.ogg", Music.class);
		woosh = assets.get("data/sounds/woosh.ogg", Sound.class);
		convert = assets.get("data/sounds/convert.ogg", Sound.class);
		ding = assets.get("data/sounds/ding.ogg", Sound.class);
		victorySound = assets.get("data/sounds/victory.ogg", Sound.class);
		loseSound = assets.get("data/sounds/boo.ogg", Sound.class);
		abilityAttack = assets.get("data/sounds/abilityattack_fire.ogg", Sound.class);
		fireball_start = assets.get("data/sounds/abilityfireball_fire.ogg", Sound.class);
		abilityPoison = assets.get("data/sounds/abilitypoison_fire.ogg", Sound.class);
		totem = assets.get("data/sounds/totem.ogg", Sound.class);
		clawSound = assets.get("data/sounds/abilityclaw.ogg", Sound.class);
		loadoutMusic = assets.get("data/sounds/loadoutmusic.ogg", Music.class);
		error = assets.get("data/sounds/error.ogg", Sound.class);
		slideIn = assets.get("data/sounds/slidein.ogg", Sound.class);
        petrify = assets.get("data/sounds/abilitypetrify.ogg");
		impressive = assets.get("data/sounds/impressive.ogg", Sound.class);
		godlike = assets.get("data/sounds/godlike.ogg", Sound.class);
		unstoppable = assets.get("data/sounds/unstoppable.ogg", Sound.class);
		pocketwatch = assets.get("data/sounds/pocketwatch.ogg", Sound.class);
		menuMusic = assets.get("data/sounds/menumusic.ogg", Music.class);
		gulp = assets.get("data/sounds/gulp.ogg", Sound.class);
		loot = assets.get("data/sounds/loot.ogg", Sound.class);
		challenge = assets.get("data/sounds/challenge.ogg", Sound.class);
		gameMusic0 = assets.get("data/sounds/music0.ogg", Music.class);
		awesome = assets.get("data/sounds/awesome.ogg", Sound.class);
		smash = assets.get("data/sounds/smash.ogg", Sound.class);
	}

	public void stopLoadoutMusic() {
		loadoutMusic.stop();
	}
	public void playMenuMusic() {
		menuMusic.setLooping(true);
		menuMusic.setVolume(0.3f);
		menuMusic.play();
	}
	
	public void playSmash() {
		smash.play();
	}
	
	public void playPocketwatch() {
		pocketwatch.play();
	}

	public void playGulp() {
		gulp.play();
	}

	public void playChallenge() {
		challenge.play();
	}

	public void playClawSound() {
		clawSound.play();
	}

	public void stopMenuMusic() {
		menuMusic.stop();
	}
}
