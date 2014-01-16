package de.cosh.anothermanager.Characters;

import de.cosh.anothermanager.Abilities.Ability;

/**
 * Created by cosh on 14.01.14.
 */
public class EnemyManager {
    private Enemy selectedEnemy;

    public void setSelectedEnemy(Enemy e) {
        selectedEnemy = e;
    }

    public Enemy getSelectedEnemy() {
        return selectedEnemy;
    }

    private class EnemyFromFile {
        String pathToImage;

    }
}
