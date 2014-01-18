package de.cosh.anothermanager.SwapGame;

import java.util.Random;

import de.cosh.anothermanager.AnotherManager;

/**
 * Created by cosh on 13.01.14.
 */
public class GemFactory {
	private final AnotherManager myGame;
	private final Random random;

	public GemFactory(final AnotherManager myGame) {
		this.myGame = myGame;
		random = new Random();
	}

	public Gem newRandomGem() {
		final Gem g = new Gem(myGame, Gem.GemType.values()[random.nextInt(6)]);
		return g;
	}
}
