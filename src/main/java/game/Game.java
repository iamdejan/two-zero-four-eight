package game;

public interface Game {

    String WIN_MESSAGE = "Congratulations! You WIN!";
    String LOSE_MESSAGE = "Sorry, you lose. Try again next time.";
    int RANDOM_NUMBERS_COUNT_PER_TURN = 2;

    void initialize();

    void runGameLoop();
}
