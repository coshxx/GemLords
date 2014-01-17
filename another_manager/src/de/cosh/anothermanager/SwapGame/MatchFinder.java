package de.cosh.anothermanager.SwapGame;

/**
 * Created by cosh on 13.01.14.
 */
public class MatchFinder {
	private final Cell[][] cells;

	public MatchFinder(final Cell[][] cells) {
		this.cells = cells;
	}

	public boolean hasMatches() {
		boolean hasHits = false;
		for (int x = 0; x < Board.MAX_SIZE_X; x++) {
			for (int y = 0; y < Board.MAX_SIZE_Y; y++) {
				final int countMatchesRight = howManyMatchesRight(x, y);
				if (countMatchesRight >= 3) {
					hasHits = true;
				}

				final int countMatchesTop = howManyMatchesTop(x, y);
				if (countMatchesTop >= 3) {
					hasHits = true;
				}
			}
		}
		return hasHits;
	}

	private int howManyMatchesRight(final int x, final int y) {
		int count = 1;

		final Gem checkGem = cells[x][y].getGem();
		if (checkGem.isTypeNone()) {
			return 0;
		}
		for (int d = x + 1; d < Board.MAX_SIZE_X; d++) {
			final Gem nextGem = cells[d][y].getGem();
			if (nextGem.isTypeNone()) {
				break;
			}
			if (checkGem.equals(nextGem)) {
				count++;
			} else {
				break;
			}
		}
		return count;
	}

	private int howManyMatchesTop(final int x, final int y) {

		int count = 1;
		final Gem checkGem = cells[x][y].getGem();
		if (checkGem.isTypeNone()) {
			return 0;
		}
		for (int d = y + 1; d < Board.MAX_SIZE_Y; d++) {
			final Gem nextGem = cells[x][d].getGem();
			if (nextGem.isTypeNone()) {
				break;
			}
			if (checkGem.equals(nextGem)) {
				count++;
			} else {
				break;
			}
		}
		return count;
	}

	public MatchResult markAllMatchingGems() {
		final MatchResult result = new MatchResult();
		for (int x = 0; x < Board.MAX_SIZE_X; x++) {
			for (int y = 0; y < Board.MAX_SIZE_Y; y++) {

				final int countMatchesRight = howManyMatchesRight(x, y);
				if (countMatchesRight >= 4) {
					boolean convertedOne = false;
					for (int d = x; d < x + countMatchesRight; d++) {
						final Gem rem = cells[d][y].getGem();
						if (rem.isMoving() && !convertedOne) {
							convertedOne = true;
							result.conversion = true;
							rem.markForSpecialConversion();
						} else {
							rem.markGemForRemoval();
						}
					}
				} else if (countMatchesRight >= 3) {
					for (int d = x; d < x + countMatchesRight; d++) {
						final Gem rem = cells[d][y].getGem();
						rem.markGemForRemoval();
						result.howMany++;
					}
				}
				final int countMatchesTop = howManyMatchesTop(x, y);

				if (countMatchesTop >= 4) {
					boolean convertedOne = false;
					for (int d = y; d < y + countMatchesTop; d++) {
						final Gem rem = cells[x][d].getGem();
						if (rem.isMoving() && !convertedOne) {
							convertedOne = true;
							result.conversion = true;
							rem.markForSpecialConversion();
						} else {
							rem.markGemForRemoval();
						}
					}
				} else if (countMatchesTop >= 3) {
					for (int d = y; d < y + countMatchesTop; d++) {
						final Gem rem = cells[x][d].getGem();
						rem.markGemForRemoval();
						result.howMany++;
					}
				}
				cells[x][y].getGem().setMoving(false);
			}
		}
		return result;
	}
}
