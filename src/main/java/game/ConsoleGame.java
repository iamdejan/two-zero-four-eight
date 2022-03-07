package game;

import board.Board;
import board.BoardProvider;
import move.Move;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class ConsoleGame implements Game {

    private final Scanner reader;
    private final Map<State, Runnable> behaviorMap;

    private Board board;
    private Optional<String> messageOptional;

    public ConsoleGame() {
        reader = new Scanner(System.in);
        messageOptional = Optional.empty();
        behaviorMap = createBehaviorMap();
    }

    @Override
    public void initialize() {
        initiateBoardWithMaxNumber();
        getBoard();
        board.addRandomNumbers(RANDOM_NUMBERS_COUNT_PER_TURN);
    }

    @Override
    public void runGameLoop() {
        while (true) {
            printBoard();
            if (gameIsFinished()) {
                break;
            }

            final Move move = inputMove();
            if (Move.UNSUPPORTED.equals(move)) {
                continue;
            }

            board.handleMove(move);

            final State state = board.getState();
            behaviorMap.get(state).run();
        }
    }

    private Map<State, Runnable> createBehaviorMap() {
        return new HashMap<>() {{
            put(State.WIN, () -> messageOptional = Optional.of(WIN_MESSAGE));
            put(State.LOSE, () -> messageOptional = Optional.of(LOSE_MESSAGE));
            put(State.PLAY, () -> board.addRandomNumbers(RANDOM_NUMBERS_COUNT_PER_TURN));
        }};
    }

    private void initiateBoardWithMaxNumber() {
        int maxNumber = askMaxNumber();
        BoardProvider.initialize(maxNumber);
    }

    private int askMaxNumber() {
        System.out.print("Enter maximum number to win: ");
        int maxNumber = reader.nextInt();
        reader.nextLine();
        return maxNumber;
    }

    private void getBoard() {
        board = BoardProvider.getInstance();
    }

    private void printBoard() {
        clearScreen();
        printAndResetMessage();
        System.out.println(board.toString());
    }

    private void clearScreen() {
        for (int i = 0; i <= 55; i++) {
            System.out.println();
        }
    }

    private void printAndResetMessage() {
        System.out.println(messageOptional.orElseGet(() -> ""));
        messageOptional = Optional.empty();
    }

    private boolean gameIsFinished() {
        return messageOptional.isPresent() &&
            (WIN_MESSAGE.equals(messageOptional.get()) || LOSE_MESSAGE.equals(messageOptional.get()));
    }

    private Move inputMove() {
        try {
            return askInput();
        } catch (UnsupportedOperationException e) {
            messageOptional = Optional.of("Unsupported move! You should input either W, A, S, or D!");
            return Move.UNSUPPORTED;
        }
    }

    private Move askInput() {
        System.out.print("Enter direction: ");
        final String input = reader.nextLine();
        return Move.parse(input);
    }
}
