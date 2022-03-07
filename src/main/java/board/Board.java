package board;

import game.State;
import move.Move;

import java.util.Arrays;
import java.util.LinkedList;
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
            while (true) {
                int r = random.nextInt(grid.length);
                int c = random.nextInt(grid.length);
                if (grid[r][c] != EMPTY) {
                    grid[r][c] = 2;
                    break;
                }
            }
        }
    }

    public void handleMove(final Move move) {
        // TODO dejan: refactor into strategy pattern
        switch (move) {
            case UP -> {
                for (int c = 0; c < grid.length; c++) {
                    final LinkedList<Integer> queue = new LinkedList<>();
                    for (int r = 0; r < grid.length; r++) {
                        if (grid[c][r] == EMPTY) {
                            continue;
                        }

                        if (queue.isEmpty() || queue.getLast() != grid[c][r]) {
                            queue.add(grid[c][r]);
                        } else {
                            queue.add(queue.removeLast() + grid[c][r]);
                        }
                    }
                    while (queue.size() < grid.length) {
                        queue.addLast(EMPTY);
                    }

                    for (int r = 0; r < grid.length; r++) {
                        grid[r][c] = queue.removeFirst();
                    }
                }
            }
            case DOWN -> {
                for (int c = 0; c < grid.length; c++) {
                    final LinkedList<Integer> queue = new LinkedList<>();
                    for (int r = grid.length - 1; r >= 0; r--) {
                        if (grid[c][r] == EMPTY) {
                            continue;
                        }

                        if (queue.isEmpty() || queue.getLast() != grid[c][r]) {
                            queue.add(grid[c][r]);
                        } else {
                            queue.add(queue.removeLast() + grid[c][r]);
                        }
                    }
                    while (queue.size() < grid.length) {
                        queue.addLast(EMPTY);
                    }

                    for (int r = grid.length - 1; r >= 0; r--) {
                        grid[r][c] = queue.removeFirst();
                    }
                }
            }
            case LEFT -> {
                for (int r = 0; r < grid.length; r++) {
                    final LinkedList<Integer> queue = new LinkedList<>();
                    for (int c = 0; c < grid.length; c++) {
                        if (grid[c][r] == EMPTY) {
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
            }
            case RIGHT -> {
                for (int r = 0; r < grid.length; r++) {
                    final LinkedList<Integer> queue = new LinkedList<>();
                    for (int c = grid.length - 1; c >= 0; c--) {
                        if (grid[c][r] == EMPTY) {
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
            }
        }

        // TODO dejan: check win / lose condition
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
