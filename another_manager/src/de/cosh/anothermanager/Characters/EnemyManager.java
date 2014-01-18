package de.cosh.anothermanager.Characters;


/**
 * Created by cosh on 14.01.14.
 */
public class EnemyManager {
	private class EnemyFromFile {
		String pathToImage;

	}

	private Enemy selectedEnemy;

	public Enemy getSelectedEnemy() {
		return selectedEnemy;
	}

	public void setSelectedEnemy(final Enemy e) {
		selectedEnemy = e;
	}
}
