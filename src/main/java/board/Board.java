package board;

import game.State;
import move.Move;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public class Board {

    private static final int DEFAULT_SIZE = 4;
    private static final int EMPTY = 0;

    private static Optional<Board> boardOptional = Optional.empty();

    private final int maxNumber;
    private final Random random;
    private final int[][] grid;

    private State state;
    private int emptyCells;

    private Board(final int maxNumber, final int size) {
        this.maxNumber = maxNumber;
        this.random = new Random();
        grid = new int[size][size];

        state = State.PLAY;
        emptyCells = size * size;
    }

    public static Board getInstance(final int maxNumber) {
        if (boardOptional.isEmpty()) {
            boardOptional = Optional.of(new Board(maxNumber, DEFAULT_SIZE));
        }

        return boardOptional.orElseThrow();
    }

    public void addRandomNumbers(final int count) {
        if (allCellsAreFilled()) {
            return;
        }

        for (int i = 1; i <= count; i++) {
            while (true) {
                int r = random.nextInt(grid.length);
                int c = random.nextInt(grid.length);
                if (grid[r][c] == EMPTY) {
                    grid[r][c] = 2;
                    break;
                }
            }
        }
    }

    public void handleMove(final Move move) {
        // TODO dejan: refactor into strategy pattern
        switch (move) {
            case UP: {
                for (int c = 0; c < grid.length; c++) {
                    final LinkedList<Integer> queue = new LinkedList<>();
                    for (int r = 0; r < grid.length; r++) {
                        if (grid[r][c] == EMPTY) {
                            continue;
                        }

                        if (queue.isEmpty() || queue.getLast() != grid[r][c]) {
                            queue.add(grid[r][c]);
                        } else {
                            queue.add(queue.removeLast() + grid[r][c]);
                        }
                    }
                    while (queue.size() < grid.length) {
                        queue.addLast(EMPTY);
                    }

                    for (int r = 0; r < grid.length; r++) {
                        grid[r][c] = queue.removeFirst();
                    }
                }
                break;
            }
            case DOWN: {
                for (int c = 0; c < grid.length; c++) {
                    final LinkedList<Integer> queue = new LinkedList<>();
                    for (int r = grid.length - 1; r >= 0; r--) {
                        if (grid[r][c] == EMPTY) {
                            continue;
                        }

                        if (queue.isEmpty() || queue.getLast() != grid[r][c]) {
                            queue.add(grid[r][c]);
                        } else {
                            queue.add(queue.removeLast() + grid[r][c]);
                        }
                    }
                    while (queue.size() < grid.length) {
                        queue.addLast(EMPTY);
                    }

                    for (int r = grid.length - 1; r >= 0; r--) {
                        grid[r][c] = queue.removeFirst();
                    }
                }
                break;
            }
            case LEFT: {
                for (int r = 0; r < grid.length; r++) {
                    final LinkedList<Integer> queue = new LinkedList<>();
                    for (int c = 0; c < grid.length; c++) {
                        if (grid[r][c] == EMPTY) {
                            continue;
                        }

                        if (queue.isEmpty() || queue.getLast() != grid[r][c]) {
                            queue.add(grid[r][c]);
                        } else {
                            queue.add(queue.removeLast() + grid[r][c]);
                        }
                    }
                    while (queue.size() < grid.length) {
                        queue.addLast(EMPTY);
                    }

                    for (int c = 0; c < grid.length; c++) {
                        grid[r][c] = queue.removeFirst();
                    }
                }
                break;
            }
            case RIGHT: {
                for (int r = 0; r < grid.length; r++) {
                    final LinkedList<Integer> queue = new LinkedList<>();
                    for (int c = grid.length - 1; c >= 0; c--) {
                        if (grid[r][c] == EMPTY) {
                            continue;
                        }

                        if (queue.isEmpty() || queue.getLast() != grid[r][c]) {
                            queue.add(grid[r][c]);
                        } else {
                            queue.add(queue.removeLast() + grid[r][c]);
                        }
                    }
                    while (queue.size() < grid.length) {
                        queue.addLast(EMPTY);
                    }

                    for (int c = grid.length - 1; c >= 0; c--) {
                        grid[r][c] = queue.removeFirst();
                    }
                }
                break;
            }
            default: {
            }
        }

        countEmptyCells();
        checkWinCondition();
        checkLoseCondition();
    }

    private void checkLoseCondition() {
        if (loseConditionFulfilled()) {
            state = State.LOSE;
        }
    }

    private void checkWinCondition() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if (winConditionFulfilledInCell(i, j)) {
                    state = State.WIN;
                    break;
                }
            }
        }
    }

    private boolean winConditionFulfilledInCell(int i, int j) {
        return grid[i][j] >= maxNumber;
    }

    private void countEmptyCells() {
        emptyCells = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if (grid[i][j] == EMPTY) {
                    emptyCells++;
                }
            }
        }
    }

    private boolean loseConditionFulfilled() {
        return !isWinState() && allCellsAreFilled();
    }

    private boolean allCellsAreFilled() {
        return emptyCells == 0;
    }

    public boolean isWinState() {
        return State.WIN.equals(state);
    }

    public boolean isLoseState() {
        return State.LOSE.equals(state);
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
                .collect(Collectors.toList());
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
