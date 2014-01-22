package de.cosh.anothermanager;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

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
    
    private Sound impressive, godlike, unstoppable;
    
	private Music mapMusic;
    private Music loadoutMusic;
    private Music gameMusic0;
	private float mapMusicVolume;
	private final AnotherManager myGame;

	private Sound victorySound, loseSound;
	private Sound woosh;

	public SoundPlayer(final AnotherManager game) {
		myGame = game;

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
		mapMusic.setLooping(true);
		mapMusicVolume = 1f;
		mapMusic.setVolume(mapMusicVolume);
		mapMusic.play();
	}

	public void playPoisonSound() {
		abilityPoison.play(0.3f);
	}

	public void playSwapError() {
		error.play();
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
    	gameMusic0.play();
    }
    
    public void stopGameMusic() {
    	gameMusic0.stop();
    }

	public void touchSounds() {
		blub1 = myGame.assets.get("data/sounds/blub1.ogg", Sound.class);
		blub2 = myGame.assets.get("data/sounds/blub2.ogg", Sound.class);

		bang = myGame.assets.get("data/sounds/bang.ogg", Sound.class);

		mapMusic = myGame.assets.get("data/sounds/music.ogg", Music.class);
		woosh = myGame.assets.get("data/sounds/woosh.ogg", Sound.class);
		convert = myGame.assets.get("data/sounds/convert.ogg", Sound.class);
		ding = myGame.assets.get("data/sounds/ding.ogg", Sound.class);
		victorySound = myGame.assets.get("data/sounds/victory.ogg", Sound.class);
		loseSound = myGame.assets.get("data/sounds/boo.ogg", Sound.class);
		abilityAttack = myGame.assets.get("data/sounds/abilityattack_fire.ogg", Sound.class);
		fireball_start = myGame.assets.get("data/sounds/abilityfireball_fire.ogg", Sound.class);
		abilityPoison = myGame.assets.get("data/sounds/abilitypoison_fire.ogg", Sound.class);
		totem = myGame.assets.get("data/sounds/totem.ogg", Sound.class);
		clawSound = myGame.assets.get("data/sounds/abilityclaw.ogg", Sound.class);
        loadoutMusic = myGame.assets.get("data/sounds/loadoutmusic.ogg", Music.class);
		error = myGame.assets.get("data/sounds/error.ogg", Sound.class);
		slideIn = myGame.assets.get("data/sounds/slidein.ogg", Sound.class);
		
		impressive = myGame.assets.get("data/sounds/impressive.ogg", Sound.class);
		godlike = myGame.assets.get("data/sounds/godlike.ogg", Sound.class);
		unstoppable = myGame.assets.get("data/sounds/unstoppable.ogg", Sound.class);
		
		
        gulp = myGame.assets.get("data/sounds/gulp.ogg", Sound.class);
		loot = myGame.assets.get("data/sounds/loot.ogg", Sound.class);
		challenge = myGame.assets.get("data/sounds/challenge.ogg", Sound.class);
		gameMusic0 = myGame.assets.get("data/sounds/music0.ogg", Music.class);
		awesome = myGame.assets.get("data/sounds/awesome.ogg", Sound.class);
	}

    public void stopLoadoutMusic() {
        loadoutMusic.stop();
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
}
