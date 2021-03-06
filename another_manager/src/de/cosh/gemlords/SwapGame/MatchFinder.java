package de.cosh.gemlords.SwapGame;


import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        int count = 0;
        GemType typeFound = GemType.TYPE_NONE;

        for (int d = x; d < Board.MAX_SIZE_X; d++) {
            if (d + 1 >= Board.MAX_SIZE_X) {
                break;
            }
            final Gem curGem = cells[d][y].getGem();
            final Gem nextGem = cells[d + 1][y].getGem();

            if (curGem == null)
                return count;
            if (nextGem == null)
                return count;

            if (curGem.equals(nextGem)) {
                if (count == 0) {
                    // first pair counts as 2
                    // also note the type so the special gem doesn't get
                    // confused
                    count = 2;
                    typeFound = curGem.isSuperSpecialGem() ? nextGem.getGemType() : curGem.getGemType();
                } else {
                    final GemType otherType = curGem.isSuperSpecialGem() ? nextGem.getGemType() : curGem.getGemType();
                    if (otherType != typeFound) {
                        break;
                    }
                    count++;
                }
            } else {
                break;
            }
        }
        return count;
    }

    private int howManyMatchesTop(final int x, final int y) {
        int count = 0;
        GemType typeFound = GemType.TYPE_NONE;
        for (int d = y; d < Board.MAX_SIZE_Y; d++) {
            if (d + 1 >= Board.MAX_SIZE_Y) {
                break;
            }
            final Gem curGem = cells[x][d].getGem();
            final Gem nextGem = cells[x][d + 1].getGem();

            if (curGem == null)
                return count;
            if (nextGem == null)
                return count;

            if (curGem.equals(nextGem)) {
                if (count == 0) {
                    // first pair counts as 2
                    // also note the type so the special gem doesn't get
                    // confused
                    count = 2;
                    typeFound = curGem.isSuperSpecialGem() ? nextGem.getGemType() : curGem.getGemType();
                } else {
                    final GemType otherType = curGem.isSuperSpecialGem() ? nextGem.getGemType() : curGem.getGemType();
                    if (otherType != typeFound) {
                        break;
                    }
                    count++;
                }
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
                if (cells[x][y].getGem() == null)
                    continue;
                if (cells[x][y].getGem().isChecked()) {
                    continue;
                }
                final int countMatchesRight = howManyMatchesRight(x, y);
                if (countMatchesRight >= 5) {
                    boolean convertedOne = false;
                    for (int d = x; d < x + countMatchesRight; d++) {
                        final Gem rem = cells[d][y].getGem();
                        rem.setChecked(true);
                        if (rem.isMoving() && !convertedOne) {
                            if (rem.isSpecialHorizontalGem() || rem.isSpecialVerticalGem() || rem.isSuperSpecialGem()) {
                                rem.markGemForRemoval();
                                result.howMany++;
                            } else {
                                convertedOne = true;
                                result.conversion = true;
                                rem.markForSuperSpecial();
                                result.howMany++;
                            }
                        } else {
                            rem.markGemForRemoval();
                        }
                    }
                } else if (countMatchesRight >= 4) {
                    boolean convertedOne = false;
                    for (int d = x; d < x + countMatchesRight; d++) {
                        final Gem rem = cells[d][y].getGem();
                        rem.setChecked(true);
                        if (rem.isMoving() && !convertedOne) {
                            if (rem.isSpecialHorizontalGem() || rem.isSpecialVerticalGem() || rem.isSuperSpecialGem()) {
                                rem.markGemForRemoval();
                                result.howMany++;
                            } else {
                                convertedOne = true;
                                result.conversion = true;
                                rem.markForSpecialConversion();
                                result.howMany++;
                            }
                        } else {
                            rem.markGemForRemoval();
                        }
                    }
                } else if (countMatchesRight >= 3) {
                    for (int d = x; d < x + countMatchesRight; d++) {
                        final Gem rem = cells[d][y].getGem();
                        rem.setChecked(true);
                        rem.markGemForRemoval();
                        result.howMany++;
                    }
                }

                final int countMatchesTop = howManyMatchesTop(x, y);
                for (int xt = 0; xt < Board.MAX_SIZE_X; xt++) {
                    for (int yt = 0; yt < Board.MAX_SIZE_Y; yt++) {
                        if (cells[xt][yt].getGem() == null)
                            continue;
                        cells[xt][yt].getGem().setChecked(false);
                    }
                }

                if (countMatchesTop >= 5) {
                    boolean convertedOne = false;
                    for (int d = y; d < y + countMatchesTop; d++) {
                        final Gem rem = cells[x][d].getGem();
                        rem.setChecked(true);
                        if (rem.isMoving() && !convertedOne) {
                            if (rem.isSpecialHorizontalGem() || rem.isSpecialVerticalGem() || rem.isSuperSpecialGem()) {
                                rem.markGemForRemoval();
                                result.howMany++;
                            } else {
                                convertedOne = true;
                                result.conversion = true;
                                rem.markForSuperSpecial();
                                result.howMany++;
                            }
                        } else {
                            rem.markGemForRemoval();
                        }
                    }
                } else if (countMatchesTop >= 4) {
                    boolean convertedOne = false;
                    for (int d = y; d < y + countMatchesTop; d++) {
                        final Gem rem = cells[x][d].getGem();
                        rem.setChecked(true);
                        if (rem.isMoving() && !convertedOne) {
                            if (rem.isSpecialHorizontalGem() || rem.isSpecialVerticalGem() || rem.isSuperSpecialGem()) {
                                rem.markGemForRemoval();
                                result.howMany++;
                            } else {
                                convertedOne = true;
                                result.conversion = true;
                                rem.markForSpecialConversion();
                                result.howMany++;
                            }
                        } else {
                            rem.markGemForRemoval();
                        }
                    }
                } else if (countMatchesTop >= 3) {
                    for (int d = y; d < y + countMatchesTop; d++) {
                        final Gem rem = cells[x][d].getGem();
                        rem.setChecked(true);
                        rem.markGemForRemoval();
                        result.howMany++;
                    }
                }
                cells[x][y].getGem().setMoving(Gem.MoveDirection.DIRECTION_NONE);
            }
        }
        for (int x = 0; x < Board.MAX_SIZE_X; x++) {
            for (int y = 0; y < Board.MAX_SIZE_Y; y++) {
                if (cells[x][y].getGem() == null)
                    continue;
                cells[x][y].getGem().setChecked(false);
            }
        }
        return result;
    }

    public void findMatches() {

    }

    public void pseudoSwap(int x, int y, int x1, int y1) {
        Gem current = cells[x][y].getGem();
        Gem next = cells[x1][y1].getGem();

        cells[x][y].setGem(next);
        cells[x1][y1].setGem(current);
    }

    public SwapCommand getFirstSwapPossibility() {
        List<SwapCommand> swapCommands = new ArrayList<SwapCommand>();

        SwapCommand command = new SwapCommand();
        for (int x = 0; x < Board.MAX_SIZE_X; x++) {
            for (int y = 0; y < Board.MAX_SIZE_Y; y++) {
                if (y + 1 >= Board.MAX_SIZE_Y)
                    continue;

                pseudoSwap(x, y, x, y + 1);

                command = findFirstMatch();

                if (command.commandFound) {
                    command.x = 0;
                    command.y = 1;
                    command.swapStart = new GridPoint2(x, y);
                    swapCommands.add(command);
                }

                pseudoSwap(x, y, x, y + 1);

                if (x + 1 >= Board.MAX_SIZE_X)
                    continue;

                pseudoSwap(x, y, x + 1, y);

                command = findFirstMatch();

                if (command.commandFound) {
                    command.x = 1;
                    command.y = 0;
                    command.swapStart = new GridPoint2(x, y);
                    swapCommands.add(command);
                }

                pseudoSwap(x, y, x + 1, y);

            }
        }
        if (swapCommands.size() > 0) {
            Collections.shuffle(swapCommands);
            return swapCommands.get(0);
        } else return new SwapCommand();
    }

    private SwapCommand findFirstMatch() {
        SwapCommand swapCommand = new SwapCommand();
        for (int x = 0; x < Board.MAX_SIZE_X; x++) {
            for (int y = 0; y < Board.MAX_SIZE_Y; y++) {
                final int countMatchesRight = howManyMatchesRight(x, y);
                if (countMatchesRight >= 3) {
                    swapCommand.commandFound = true;
                }

                final int countMatchesTop = howManyMatchesTop(x, y);
                if (countMatchesTop >= 3) {
                    swapCommand.commandFound = true;
                }
            }
        }
        return swapCommand;
    }
}
