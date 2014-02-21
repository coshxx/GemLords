package de.cosh.gemlords;

/**
 * Created by cosh on 07.02.14.
 */
public class AfterActionReport {
    public Integer totalDamageReceived;
    public Integer totalDamgeDealt;

    public Integer longestCombo;

    public Integer highestDamageReceivedInOneTurn;
    public Integer highestDamageDealtInOneTurn;

    public Integer playerTotalHealed;
    public Integer enemyTotalHealed;

    public AfterActionReport() {
        reset();
    }

    public void reset() {
        totalDamageReceived = 0;
        totalDamgeDealt = 0;

        longestCombo = 0;

        highestDamageDealtInOneTurn = 0;
        highestDamageReceivedInOneTurn = 0;

        playerTotalHealed = 0;
        enemyTotalHealed = 0;
    }

    public void setHighestDamageReceivedInOneTurn(int dmg) {
        if( dmg > highestDamageReceivedInOneTurn )
            highestDamageReceivedInOneTurn = dmg;
    }

    public void setHighestDamageDealtInOneTurn(int dmg) {
        if( dmg > highestDamageDealtInOneTurn )
            highestDamageDealtInOneTurn = dmg;
    }

    public void setLongestCombo(int length) {
        if( length > longestCombo )
            longestCombo = length;
    }
}
