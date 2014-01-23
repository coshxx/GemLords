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
            	if( cells[x][y].getGem().isChecked() )
            		continue;
                int countMatchesRight = howManyMatchesRight(x, y);
                if( countMatchesRight >= 5 ) {
                	boolean convertedOne = false;
                	for( int d = x; d < x + countMatchesRight; d++) {
                		Gem rem = cells[d][y].getGem();
                		rem.checked();
                		if( rem.isMoving() && !convertedOne) {
                			convertedOne = true;
                			result.conversion = true;
                			rem.markForSuperSpecial();
                			result.howMany++;
                		} else rem.markGemForRemoval();
                	}
                } else if (countMatchesRight >= 4) {
                    boolean convertedOne = false;
                    for (int d = x; d < x + countMatchesRight; d++) {
                        Gem rem = cells[d][y].getGem();
                        rem.checked();
                        if (rem.isMoving() && !convertedOne) {
                            convertedOne = true;
                            result.conversion = true;
                            rem.markForSpecialConversion();
                            result.howMany++;
                        } else rem.markGemForRemoval();
                    }
                } else if (countMatchesRight >= 3) {
                    for (int d = x; d < x + countMatchesRight; d++) {
                        Gem rem = cells[d][y].getGem();
                        rem.checked();
                        rem.markGemForRemoval();
                        result.howMany++;
                    }
                }
                int countMatchesTop = howManyMatchesTop(x, y);

                if( countMatchesTop >= 5 ) {
                	boolean convertedOne = false;
                	for( int d = y; d < y + countMatchesTop; d++) {
                		Gem rem = cells[x][d].getGem();
                		rem.checked();
                		if( rem.isMoving() && !convertedOne) {
                			convertedOne = true;
                			result.conversion = true;
                			rem.markForSuperSpecial();
                			result.howMany++;
                		} else rem.markGemForRemoval();
                	}
                } else if (countMatchesTop >= 4) {
                    boolean convertedOne = false;
                    for (int d = y; d < y + countMatchesTop; d++) {
                        Gem rem = cells[x][d].getGem();
                        rem.checked();
                        if (rem.isMoving() && !convertedOne) {
                            convertedOne = true;
                            result.conversion = true;
                            rem.markForSpecialConversion();
                            result.howMany++;
                        } else rem.markGemForRemoval();
                    }
                } else if (countMatchesTop >= 3) {
                    for (int d = y; d < y + countMatchesTop; d++) {
                        Gem rem = cells[x][d].getGem();
                        rem.checked();
                        rem.markGemForRemoval();
                        result.howMany++;
                    }
                }
                cells[x][y].getGem().setMoving(Gem.MoveDirection.DIRECTION_NONE);
            }
        }
        for (int x = 0; x < Board.MAX_SIZE_X; x++) {
            for (int y = 0; y < Board.MAX_SIZE_Y; y++) {
            	cells[x][y].getGem().setChecked(false);
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
    	int count = 0;
    	for( int d = y; d < Board.MAX_SIZE_Y; d++ ) {
    		if( d+1 >= Board.MAX_SIZE_Y )
    			break;
    		Gem curGem = cells[x][d].getGem();
    		Gem nextGem = cells[x][d+1].getGem();
    		
    		if( curGem.equals(nextGem )) {
    			if( count == 0 ) {
    				// first pair counts as 2
    				count = 2;
    			} else count++;
    		} else break;
    	}
    	return count;
    }

    private int howManyMatchesRight(int x, int y) {
    	int count = 0;
    	for( int d = x; d < Board.MAX_SIZE_X; d++ ) {
    		if( d+1 >= Board.MAX_SIZE_X )
    			break;
    		Gem curGem = cells[d][y].getGem();
    		Gem nextGem = cells[d+1][y].getGem();
    		
    		if( curGem.equals(nextGem )) {
    			if( count == 0 ) {
    				// first pair counts as 2
    				count = 2;
    			} else count++;
    		} else break;
    	}
    	return count;
    }
}
