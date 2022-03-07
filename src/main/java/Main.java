import game.ConsoleGame;
import game.Game;

// visit https://2048game.com/ again for testing
public class Main {
    public static void main(String[] args) {
        final Game game = new ConsoleGame();
        game.initialize();
        game.runGameLoop();
    }
}
