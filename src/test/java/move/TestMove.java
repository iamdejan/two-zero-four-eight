package move;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class TestMove {

    @Test
    public void parse_valid_success() {
        // Given
        final String input = "w";

        // When
        final Move move = Move.parse(input);

        // Then
        assertEquals(move, Move.UP);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void parse_invalid_throwException() {
        // Given
        final String input = "h";

        // When
        final Move move = Move.parse(input);
    }
}
