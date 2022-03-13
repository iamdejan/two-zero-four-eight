package move;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.testng.Assert.assertTrue;

public class TestUpMoveHandler {

    private UpMoveHandler upMoveHandler;

    @BeforeMethod
    public void setUp() {
        upMoveHandler = new UpMoveHandler();
    }

    @Test
    public void move_valid_success() {
        // Given
        final int[][] actualGrid = new int[][]{
            {0, 0, 0, 2},
            {0, 0, 2, 0},
            {0, 2, 0, 0},
            {0, 2, 2, 0}
        };

        // When
        upMoveHandler.move(actualGrid);

        // Then
        final int[][] expectedGrid = new int[][]{
            {0, 4, 4, 2},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
        };
        assertTrue(Arrays.deepEquals(actualGrid, expectedGrid));
    }

    @Test
    public void move_invalid_nothingChanged() {
        // Given
        final int[][] actualGrid = new int[][]{
            {2, 2, 2, 2},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
        };

        // When
        upMoveHandler.move(actualGrid);

        // Then
        final int[][] expectedGrid = new int[][]{
            {2, 2, 2, 2},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
        };
        assertTrue(Arrays.deepEquals(actualGrid, expectedGrid));
    }

    @Test
    public void move_threeInAColumn_success() {
        // Given
        final int[][] actualGrid = new int[][]{
            {2, 0, 0, 2},
            {0, 0, 2, 0},
            {0, 2, 2, 0},
            {0, 2, 2, 0}
        };

        // When
        upMoveHandler.move(actualGrid);

        // Then
        final int[][] expectedGrid = new int[][]{
            {2, 4, 4, 2},
            {0, 0, 2, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
        };
        assertTrue(Arrays.deepEquals(actualGrid, expectedGrid));
    }
}
