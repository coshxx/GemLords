package de.cosh.anothermanager.SwapGame;

import java.util.Random;

import com.badlogic.gdx.scenes.scene2d.Group;

/**
 * Created by cosh on 13.01.14.
 */
public class GemRespawner {
	private final Cell[][] cells;
	private final GemFactory gemFactory;
	public GemRespawner(final Cell[][] cells, final Random r, final GemFactory gemFactory) {
		this.cells = cells;
		this.gemFactory = gemFactory;
	}


}
