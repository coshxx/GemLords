package de.cosh.anothermanager.SwapGame;

import de.cosh.anothermanager.AnotherManager;

import java.util.Random;

/**
 * Created by cosh on 13.01.14.
 */
public class GemFactory {
    private AnotherManager myGame;
    private Random random;
    public GemFactory(AnotherManager myGame) {
        this.myGame = myGame;
        random = new Random();
    }

    public Gem newRandomGem() {
        Gem g = new Gem(myGame, Gem.GemType.values()[random.nextInt(6)]);
        return g;
    }
}
