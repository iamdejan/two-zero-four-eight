package board;

import game.State;
import move.Move;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Board {

    private static final int EMPTY = 0;

    private final int maxNumber;
    private final Random random;
    private final int[][] grid;

    private State state;

    public Board(final int maxNumber, final int size) {
        this.maxNumber = maxNumber;
        this.random = new Random();
        grid = new int[size][size];

        state = State.PLAY;
    }

    public void addRandomNumbers(final int count) {
        for (int i = 1; i <= count; i++) {
            while(true) {
                int r = random.nextInt(grid.length);
                int c = random.nextInt(grid.length);
                if(grid[r][c] != EMPTY) {
                    grid[r][c] = 2;
                    break;
                }
            }
        }
    }

    public void handleMove(final Move move) {
        // TODO dejan: implement this
    }

    public State getState() {
        return state;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (int[] row : grid) {
            final List<String> arrayConvertedToList = Arrays.stream(row)
                .mapToObj(this::convertNumberForFormatting)
                .toList();
            sb.append(String.join(" ", arrayConvertedToList));
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    private String convertNumberForFormatting(int number) {
        if (number > 0) {
            return number + "";
        }
        return "_";
    }
}
