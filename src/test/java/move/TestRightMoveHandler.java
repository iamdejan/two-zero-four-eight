package move;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.testng.Assert.assertTrue;

public class TestRightMoveHandler {

    private RightMoveHandler rightMoveHandler;

    @BeforeMethod
    public void setUp() {
        rightMoveHandler = new RightMoveHandler();
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
        rightMoveHandler.move(actualGrid);

        // Then
        final int[][] expectedGrid = new int[][]{
            {0, 0, 0, 2},
            {0, 0, 0, 2},
            {0, 0, 0, 2},
            {0, 0, 0, 4}
        };
        assertTrue(Arrays.deepEquals(actualGrid, expectedGrid));
    }

    @Test
    public void move_invalid_nothingChanged() {
        // Given
        final int[][] actualGrid = new int[][]{
            {0, 0, 0, 2},
            {0, 0, 0, 2},
            {0, 0, 0, 2},
            {0, 0, 0, 4}
        };

        // When
        rightMoveHandler.move(actualGrid);

        // Then
        final int[][] expectedGrid = new int[][]{
            {0, 0, 0, 2},
            {0, 0, 0, 2},
            {0, 0, 0, 2},
            {0, 0, 0, 4}
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
        rightMoveHandler.move(actualGrid);

        // Then
        final int[][] expectedGrid = new int[][]{
            {0, 0, 0, 4},
            {0, 0, 0, 4},
            {0, 0, 0, 0},
            {0, 0, 0, 2}
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
        rightMoveHandler.move(actualGrid);

        // Then
        final int[][] expectedGrid = new int[][]{
            {0, 0, 0, 4},
            {0, 0, 2, 4},
            {0, 0, 0, 4},
            {0, 0, 0, 2}
        };
        assertTrue(Arrays.deepEquals(actualGrid, expectedGrid));
    }
}
