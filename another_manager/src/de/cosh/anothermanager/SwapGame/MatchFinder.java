package de.cosh.anothermanager.SwapGame;

/**
 * Created by cosh on 13.01.14.
 */
public class MatchFinder {
    private Cell[][] cells;

    public MatchFinder(Cell[][] cells) {
        this.cells = cells;
    }

    public MatchResult markAllMatchingGems() {
        MatchResult result = new MatchResult();
        for (int x = 0; x < Board.MAX_SIZE_X; x++) {
            for (int y = 0; y < Board.MAX_SIZE_Y; y++) {

                int countMatchesRight = howManyMatchesRight(x, y);
                if (countMatchesRight >= 4) {
                    boolean convertedOne = false;
                    for (int d = x; d < x + countMatchesRight; d++) {
                        Gem rem = cells[d][y].getGem();
                        if (rem.isMoving() && !convertedOne) {
                            convertedOne = true;
                            result.conversion = true;
                            rem.markForSpecialConversion();
                        } else rem.markGemForRemoval();
                    }
                } else if (countMatchesRight >= 3) {
                    for (int d = x; d < x + countMatchesRight; d++) {
                        Gem rem = cells[d][y].getGem();
                        rem.markGemForRemoval();
                        result.howMany++;
                    }
                }
                int countMatchesTop = howManyMatchesTop(x, y);

                if (countMatchesTop >= 4) {
                    boolean convertedOne = false;
                    for (int d = y; d < y + countMatchesTop; d++) {
                        Gem rem = cells[x][d].getGem();
                        if (rem.isMoving() && !convertedOne) {
                            convertedOne = true;
                            result.conversion = true;
                            rem.markForSpecialConversion();
                        } else rem.markGemForRemoval();
                    }
                } else if (countMatchesTop >= 3) {
                    for (int d = y; d < y + countMatchesTop; d++) {
                        Gem rem = cells[x][d].getGem();
                        rem.markGemForRemoval();
                        result.howMany++;
                    }
                }
                cells[x][y].getGem().setMoving(false);
            }
        }
        return result;
    }

    public boolean hasMatches() {
        boolean hasHits = false;
        for (int x = 0; x < Board.MAX_SIZE_X; x++) {
            for (int y = 0; y < Board.MAX_SIZE_Y; y++) {
                int countMatchesRight = howManyMatchesRight(x, y);
                if (countMatchesRight >= 3)
                    hasHits = true;

                int countMatchesTop = howManyMatchesTop(x, y);
                if (countMatchesTop >= 3)
                    hasHits = true;
            }
        }
        return hasHits;
    }

    private int howManyMatchesTop(int x, int y) {

        int count = 1;
        Gem checkGem = cells[x][y].getGem();
        if (checkGem.isTypeNone())
            return 0;
        for (int d = y + 1; d < Board.MAX_SIZE_Y; d++) {
            Gem nextGem = cells[x][d].getGem();
            if (nextGem.isTypeNone())
                break;
            if (checkGem.equals(nextGem))
                count++;
            else break;
        }
        return count;
    }

    private int howManyMatchesRight(int x, int y) {
        int count = 1;

        Gem checkGem = cells[x][y].getGem();
        if (checkGem.isTypeNone())
            return 0;
        for (int d = x + 1; d < Board.MAX_SIZE_X; d++) {
            Gem nextGem = cells[d][y].getGem();
            if (nextGem.isTypeNone())
                break;
            if (checkGem.equals(nextGem))
                count++;
            else break;
        }
        return count;
    }
}
