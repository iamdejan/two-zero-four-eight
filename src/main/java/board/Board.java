package board;

import game.State;
import move.DownMoveHandler;
import move.LeftMoveHandler;
import move.Move;
import move.MoveHandler;
import move.RightMoveHandler;
import move.UpMoveHandler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public class Board {

    public static final int EMPTY_CELL_VALUE = 0;
    private static final int DEFAULT_SIZE = 4;

    private static Optional<Board> boardOptional = Optional.empty();

    private final int maxNumber;
    private final Random random;
    private final int[][] grid;
    private final Map<Move, MoveHandler> moveHandlerMap;

    private State state;
    private int emptyCells;

    private Board(final int maxNumber, final int size) {
        this.maxNumber = maxNumber;
        this.random = new Random();
        this.grid = new int[size][size];
        this.moveHandlerMap = createMoveHandlerMap();

        this.state = State.PLAY;
        this.emptyCells = size * size;
    }

    private Map<Move, MoveHandler> createMoveHandlerMap() {
        return new HashMap<>() {{
            put(Move.UP, new UpMoveHandler());
            put(Move.DOWN, new DownMoveHandler());
            put(Move.LEFT, new LeftMoveHandler());
            put(Move.RIGHT, new RightMoveHandler());
        }};
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
                if (isCellEmpty(r, c)) {
                    grid[r][c] = 2;
                    break;
                }
            }
        }
    }

    private boolean isCellEmpty(final int r, final int c) {
        return grid[r][c] == EMPTY_CELL_VALUE;
    }

    public State getState() {
        return state;
    }

    public void handleMove(final Move move) {
        move(move);

        countEmptyCells();
        checkWinCondition();
        checkLoseCondition();
    }

    private void move(final Move move) {
        getMoveHandler(move).move(grid);
    }

    private MoveHandler getMoveHandler(final Move move) {
        return moveHandlerMap.get(move);
    }

    private void countEmptyCells() {
        emptyCells = 0;
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid.length; c++) {
                if (isCellEmpty(r, c)) {
                    emptyCells++;
                }
            }
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

    private void checkLoseCondition() {
        if (loseConditionFulfilled()) {
            state = State.LOSE;
        }
    }

    private boolean loseConditionFulfilled() {
        return !isWinState() && allCellsAreFilled();
    }

    private boolean isWinState() {
        return State.WIN.equals(state);
    }

    private boolean allCellsAreFilled() {
        return emptyCells == 0;
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

    private String convertNumberForFormatting(final int number) {
        if (number > 0) {
            return number + "";
        }
        return "_";
    }
}
