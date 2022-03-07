package move;

public enum Move {

    UP("w"),
    DOWN("s"),
    LEFT("a"),
    RIGHT("d"),
    UNSUPPORTED("");

    public String keyboardInput;

    Move(String keyboardInput) {
        this.keyboardInput = keyboardInput;
    }

    public static Move parse(final String input) {
        for(Move move: values()) {
            if(move.keyboardInput.equalsIgnoreCase(input)) {
                return move;
            }
        }

        throw new UnsupportedOperationException();
    }
}
