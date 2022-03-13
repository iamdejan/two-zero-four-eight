package move;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.testng.Assert.assertTrue;

public class TestLeftMoveHandler {

    private LeftMoveHandler leftMoveHandler;

    @BeforeMethod
    public void setUp() {
        leftMoveHandler = new LeftMoveHandler();
    }

    @Test
    public void move_valid_success() {
        // Given
        final int[][] actualGrid = new int[][]{
            {0, 0, 0, 2},
            {2, 0, 0, 0},
            {0, 2, 0, 0},
            {0, 2, 2, 0}
        };

        // When
        leftMoveHandler.move(actualGrid);

        // Then
        final int[][] expectedGrid = new int[][]{
            {2, 0, 0, 0},
            {2, 0, 0, 0},
            {2, 0, 0, 0},
            {4, 0, 0, 0}
        };
        assertTrue(Arrays.deepEquals(actualGrid, expectedGrid));
    }

    @Test
    public void move_invalid_nothingChanged() {
        // Given
        final int[][] actualGrid = new int[][]{
            {2, 0, 0, 0},
            {2, 0, 0, 0},
            {2, 0, 0, 0},
            {4, 0, 0, 0}
        };

        // When
        leftMoveHandler.move(actualGrid);

        // Then
        final int[][] expectedGrid = new int[][]{
            {2, 0, 0, 0},
            {2, 0, 0, 0},
            {2, 0, 0, 0},
            {4, 0, 0, 0}
        };
        assertTrue(Arrays.deepEquals(actualGrid, expectedGrid));
    }

    @Test
    public void move_addedDespiteNotNeighbor_success() {
        // Given
        final int[][] actualGrid = new int[][]{
            {0, 2, 0, 2},
            {2, 0, 2, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 2}
        };

        // When
        leftMoveHandler.move(actualGrid);

        // Then
        final int[][] expectedGrid = new int[][]{
            {4, 0, 0, 0},
            {4, 0, 0, 0},
            {0, 0, 0, 0},
            {2, 0, 0, 0}
        };
        assertTrue(Arrays.deepEquals(actualGrid, expectedGrid));
    }

    @Test
    public void move_threeInARow_success() {
        // Given
        final int[][] actualGrid = new int[][]{
            {0, 2, 0, 2},
            {2, 2, 2, 0},
            {0, 2, 2, 0},
            {0, 0, 2, 0}
        };

        // When
        leftMoveHandler.move(actualGrid);

        // Then
        final int[][] expectedGrid = new int[][]{
            {4, 0, 0, 0},
            {4, 2, 0, 0},
            {4, 0, 0, 0},
            {2, 0, 0, 0}
        };
        assertTrue(Arrays.deepEquals(actualGrid, expectedGrid));
    }
}
