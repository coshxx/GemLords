package de.cosh.anothermanager.Characters;

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
}
