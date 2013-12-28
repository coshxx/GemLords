package de.cosh.anothermanager;

/**
 * Created by cosh on 27.12.13.
 */
public class MatchFinder {

    private int MAX_SIZE_X, MAX_SIZE_Y;
    private Cell[][] board;

    public boolean findMatches(Cell[][] board, boolean mark) {
        boolean foundSome = false;
        for (int x = 0; x < MAX_SIZE_X; x++) {
            for (int y = 0; y < MAX_SIZE_Y; y++) {
                int result = howManyMatchesToTheRight(x, y);

                if (result >= 3) {
                    for (int d = 0; d < result; d++) {
                        if (mark) {
                            board[x + d][y].markForRemoval();
                        }
                        foundSome = true;
                    }
                }
                result = howManyMatchesToTheTop(x, y);
                if (result >= 3) {
                    for (int d = 0; d < result; d++) {
                        if (mark) {
                            board[x][y + d].markForRemoval();
                        }
                        foundSome = true;
                    }
                }
            }
        }
        return foundSome;
    }

    private int howManyMatchesToTheTop(int x, int y) {
        Gem.GemType currentType = board[x][y].getOccupant().getGemType();
        if (currentType == Gem.GemType.TYPE_NONE)
            return 0;
        int searchPos = y;
        int count = 1;

        while (true) {
            if (searchPos + 1 >= MAX_SIZE_Y)
                break;
            if (board[x][searchPos + 1].getOccupant().getGemType() == currentType) {
                count++;
                searchPos++;
            } else {
                break;
            }
        }
        return count;
    }

    private int howManyMatchesToTheRight(int x, int y) {
        Gem.GemType currentType = board[x][y].getOccupant().getGemType();
        int searchPos = x;
        int count = 1;

        while (true) {
            if (searchPos + 1 >= MAX_SIZE_X)
                break;
            if (board[searchPos + 1][y].getOccupant().getGemType() == currentType) {
                count++;
                searchPos++;
            } else {
                break;
            }
        }
        return count;
    }

    public void init(Cell[][] board, int max_size_x, int max_size_y) {
        this.MAX_SIZE_X = max_size_x;
        this.MAX_SIZE_Y = max_size_y;
        this.board = board;
    }
}
