package board;

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
    protected static final int DEFAULT_SIZE = 4;

    private static Optional<Board> boardOptional = Optional.empty();

    private final int maxNumber;
    private final Random random;
    private final int[][] grid;
    private final Map<Move, MoveHandler> moveHandlerMap;

    protected int emptyCells;
    private State state;
    private boolean validMove;

    public static Board getInstance(final int maxNumber) {
        if (boardOptional.isEmpty()) {
            boardOptional = Optional.of(new Board(maxNumber, DEFAULT_SIZE));
        }

        return boardOptional.orElseThrow();
    }

    protected static void reset() {
        boardOptional = Optional.empty();
    }

    private Board(final int maxNumber, final int size) {
        this.maxNumber = maxNumber;
        this.random = new Random();
        this.grid = new int[size][size];
        this.moveHandlerMap = createMoveHandlerMap();

        this.state = State.PLAY;
        this.emptyCells = size * size;
        validMove = true;
    }

    private Map<Move, MoveHandler> createMoveHandlerMap() {
        return new HashMap<>() {{
            put(Move.UP, new UpMoveHandler());
            put(Move.DOWN, new DownMoveHandler());
            put(Move.LEFT, new LeftMoveHandler());
            put(Move.RIGHT, new RightMoveHandler());
        }};
    }

    public void addRandomNumbersIfValidMove(int count) {
        if (invalidMove()) {
            return;
        }

        addRandomNumbers(count);
    }

    private boolean invalidMove() {
        return !validMove;
    }

    private boolean allCellsAreFilled() {
        return emptyCells == 0;
    }

    public void addRandomNumbers(int count) {
        if (allCellsAreFilled()) {
            return;
        }

        while (count > 0) {
            int r = random.nextInt(grid.length);
            int c = random.nextInt(grid.length);
            if (isCellEmpty(r, c)) {
                grid[r][c] = 2;
                emptyCells--;
                count--;
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
        final int[][] originalGrid = deepCopyGrid(grid);
        getMoveHandler(move).move(grid);
        if (gridHasChanged(originalGrid, grid)) {
            validMove = false;
        }
    }

    private int[][] deepCopyGrid(final int[][] grid) {
        final int[][] result = new int[grid.length][grid.length];
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid.length; c++) {
                result[r][c] = grid[r][c];
            }
        }
        return result;
    }

    private MoveHandler getMoveHandler(final Move move) {
        return moveHandlerMap.get(move);
    }

    private boolean gridHasChanged(final int[][] originalGrid, final int[][] currentGrid) {
        return Arrays.deepEquals(originalGrid, currentGrid);
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

    protected void checkWinCondition() {
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid.length; c++) {
                if (winConditionFulfilledInCell(r, c)) {
                    state = State.WIN;
                    break;
                }
            }
        }
    }

    private boolean winConditionFulfilledInCell(final int r, final int c) {
        return grid[r][c] >= maxNumber;
    }

    protected void checkLoseCondition() {
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
