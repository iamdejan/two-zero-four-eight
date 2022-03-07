package board;

import java.util.Optional;

public class BoardProvider {

    private static final int DEFAULT_SIZE = 4;

    private static Optional<Board> boardOptional = Optional.empty();

    public static void initialize(final int maxNumber) {
        if (boardOptional.isEmpty()) {
            boardOptional = Optional.of(new Board(maxNumber, DEFAULT_SIZE));
        }
    }

    public static Board getInstance() {
        return boardOptional.orElseThrow();
    }
}
