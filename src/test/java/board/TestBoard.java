package board;

import move.Move;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class TestBoard {

    @BeforeMethod
    public void setUp() {
        Board.reset();
    }

    @Test
    public void getInstance_valid_returnBoardInstance() {
        // Given
        final int maxNumber = 2;

        // When
        final Board board = Board.getInstance(maxNumber);

        // Then
        assertNotNull(board);
        assertEquals(board.getState(), State.PLAY);
    }

    @Test
    public void toString_valid_returnString() {
        // Given
        final int maxNumber = 2;
        final Board board = Board.getInstance(maxNumber);

        // When
        final String formattedBoard = board.toString();

        // Then
        assertNotNull(formattedBoard);
    }

    @Test
    public void addRandomNumbers_valid_success() {
        // Given
        final int maxNumber = 4;
        final Board board = Board.getInstance(maxNumber);
        final int count = 2;

        // When
        board.addRandomNumbers(count);

        // Then
        assertEquals(board.emptyCells, Board.DEFAULT_SIZE * Board.DEFAULT_SIZE - count);
    }

    @Test
    public void handleMove_valid_success() {
        // Given
        final int maxNumber = 2048;
        final Board board = Board.getInstance(maxNumber);
        final int count = 2;
        board.addRandomNumbers(count);

        // When
        board.handleMove(Move.UP);

        // Then
        assertTrue(board.emptyCells < Board.DEFAULT_SIZE * Board.DEFAULT_SIZE);
        assertEquals(board.getState(), State.PLAY);
        assertNotNull(board.toString());
    }

    @Test
    public void checkWinCondition_validWinCondition_stateChangedToWin() {
        // Given
        final int maxNumber = 2;
        final Board board = Board.getInstance(maxNumber);
        final int count = 2;
        board.addRandomNumbers(count);

        // When
        board.checkWinCondition();

        // Then
        assertEquals(board.getState(), State.WIN);
    }

    @Test
    public void checkLoseCondition_validLoseCondition_stateChangedToLose() {
        // Given
        final int maxNumber = 2048;
        final Board board = Board.getInstance(maxNumber);
        board.emptyCells = 0;

        // When
        board.checkLoseCondition();

        // Then
        assertEquals(board.getState(), State.LOSE);
    }
}
