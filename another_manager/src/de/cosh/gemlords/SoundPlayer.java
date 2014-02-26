package de.cosh.gemlords;

import com.badlogic.gdx.Gdx;
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
	private Sound convert;
	private Sound ding;
    private Sound lightningRod;
	private Sound fireball_start;
    private Sound critical;
    private Sound scribble;

	private Sound gulp;
	private Sound challenge;
	private Sound clawSound;
	private Sound totem;
	private Sound slideIn;
    private Sound petrify;
    private Sound turnSwitch;
    private Sound pocketwatch;
    private Sound block;
    private Sound bow;
	private Sound impressive, godlike, unstoppable;
    private Sound abilityBite;
    private Sound gotItem;
    private Sound victorySound, loseSound;
    private Sound woosh;
    private Sound smash;

    private Music sequenceMusic;
    private Music lootMusic;
	private Music mapMusic;
	private Music loadoutMusic;
	private Music menuMusic;
	private Music gameMusic0;
    private Music gameMusic1;
    private Music gameMusic2;
    private Music gameMusic3;
    private Music creditsMusic;
	private float mapMusicVolume;


    private Music splash;

	public SoundPlayer(final GemLord game) {
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

    public void playBow() {
        bow.play();
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

    public void playGotItem() {
        gotItem.play();
    }

	public void playGodlike() {
		godlike.play(0.3f);
	}

	public void playAwesome() {
		awesome.play();
	}

    public void playLightningRod() {
        lightningRod.play();
    }

	public void playBang() {
		bang.play();
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
		lootMusic.play();
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
		lootMusic.stop();
	}

	public void stopMapMusic() {
		mapMusic.stop();
	}

	public void playLoadoutMusic() {
		loadoutMusic.setVolume(0.2f);
		loadoutMusic.play();
	}

    public void playCreditsMusic() {
        creditsMusic.play();
    }

	public void playGameMusic() {
        Random r = new Random();
        int choice = r.nextInt(6);
        if( choice == 0 )
		    gameMusic0.play();
        else if( choice == 1 ) {
            menuMusic.play();
        } else if (choice == 2 ) {
            gameMusic1.play();
        }
        else if (choice == 3 ) {
            gameMusic2.play();
        }
        else if (choice == 4 ) {
            creditsMusic.play();
        }
        else if (choice == 5 ) {
            gameMusic3.play();
        }
	}

    public void playIntroMusic() {
        gameMusic3.play();
    }

	public void stopGameMusic() {
		gameMusic0.stop();
		gameMusic1.stop();
		gameMusic2.stop();
        gameMusic3.stop();
        menuMusic.stop();
        creditsMusic.stop();
	}

    public void playCritical() {
        critical.play();
    }

    public void playBlock() {
        block.play();
    }

    public void playSequence() {
        sequenceMusic.play();
    }

    public void stopSequence() {
        sequenceMusic.stop();
        scribble.stop();
    }

	public void touchSounds() {
		AssetManager assets = GemLord.assets;
        bow = assets.get("data/sounds/bow.ogg", Sound.class);
        turnSwitch = assets.get("data/sounds/turn.ogg", Sound.class);
		bang = assets.get("data/sounds/bang.ogg", Sound.class);
        block = assets.get("data/sounds/block.ogg", Sound.class);
        critical = assets.get("data/sounds/critical.ogg", Sound.class);
		mapMusic = assets.get("data/sounds/music.ogg", Music.class);
		woosh = assets.get("data/sounds/woosh.ogg", Sound.class);
		convert = assets.get("data/sounds/convert.ogg", Sound.class);
		ding = assets.get("data/sounds/ding.ogg", Sound.class);
		victorySound = assets.get("data/sounds/victory.ogg", Sound.class);
		loseSound = assets.get("data/sounds/boo.ogg", Sound.class);
		abilityAttack = assets.get("data/sounds/abilityattack_fire.ogg", Sound.class);
        sequenceMusic = assets.get("data/sounds/sequence.ogg", Music.class);
		fireball_start = assets.get("data/sounds/abilityfireball_fire.ogg", Sound.class);
		abilityPoison = assets.get("data/sounds/abilitypoison_fire.ogg", Sound.class);
		totem = assets.get("data/sounds/totem.ogg", Sound.class);
		clawSound = assets.get("data/sounds/abilityclaw.ogg", Sound.class);
		loadoutMusic = assets.get("data/sounds/loadoutmusic.ogg", Music.class);
        lightningRod = assets.get("data/sounds/lightningrod.ogg", Sound.class);
		slideIn = assets.get("data/sounds/slidein.ogg", Sound.class);
        petrify = assets.get("data/sounds/abilitypetrify.ogg");
		pocketwatch = assets.get("data/sounds/pocketwatch.ogg", Sound.class);
		menuMusic = assets.get("data/sounds/menumusic.ogg", Music.class);
		gulp = assets.get("data/sounds/gulp.ogg", Sound.class);
		lootMusic = assets.get("data/sounds/loot.ogg", Music.class);
		challenge = assets.get("data/sounds/challenge.ogg", Sound.class);
		gameMusic0 = assets.get("data/sounds/music0.ogg", Music.class);
		gameMusic1 = assets.get("data/sounds/music3.ogg", Music.class);
		gameMusic2 = assets.get("data/sounds/music4.ogg", Music.class);
		gameMusic3 = assets.get("data/sounds/music5.ogg", Music.class);
		creditsMusic = assets.get("data/sounds/creditsmusic.ogg", Music.class);
        scribble = assets.get("data/sounds/scribble.ogg", Sound.class);
		awesome = assets.get("data/sounds/awesome.ogg", Sound.class);
		smash = assets.get("data/sounds/smash.ogg", Sound.class);
        abilityBite = assets.get("data/sounds/bite.ogg", Sound.class);
        gotItem = assets.get("data/sounds/gotitem.ogg", Sound.class);

        menuMusic.setLooping(true);
        gameMusic0.setLooping(true);
        gameMusic1.setLooping(true);
        gameMusic2.setLooping(true);
        creditsMusic.setLooping(true);
	}

    public void playScribble() {
        scribble.play(0.3f);
    }

	public void stopLoadoutMusic() {
		loadoutMusic.stop();
	}
	public void playMenuMusic() {

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

    public void playAbilityBite() {
        abilityBite.play();
    }

    public void playTurnSwitch() {
        turnSwitch.play();
    }

    public void playSplash() {
        splash = Gdx.audio.newMusic(Gdx.files.internal("data/sounds/splash.ogg"));
        splash.play();
    }

    public void stopSplash() {
        splash.stop();
    }

}
