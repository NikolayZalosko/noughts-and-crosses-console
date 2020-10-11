import java.util.*;

/**
 * player1 is always human
 * player1 always uses 'X'
 *
 * player2 can be either human or CPU
 * player2 always uses '0'
 */
public class Game {
    private Map<String, Cell> field = new HashMap<>();
    private int fieldDimension = 3;
    private Player player2;

    /**
     * -1 -> no results yet
     *  0 -> draw
     *  1 -> player1 won
     *  2 -> player2 won
     */
    private int result = -1;

    private GameService service = new GameService(field, fieldDimension);
    private Checker checker = new Checker(field, fieldDimension);
    private TurnGenerator turnGenerator = new TurnGenerator(field, fieldDimension);

    public Game() {
        service.fillField();
        player2 = service.choosePlayer2();
    }

    public Player getPlayer2() {
        return player2;
    }

    public void battleWithHuman() {
        int currentPlayer = 1;
        int turnNumber = 1;
        int maxTurns = (int) Math.pow(fieldDimension, 2);
        while (result == -1 && turnNumber <= maxTurns) {
            service.showCurrentField();
            System.out.println("player" + currentPlayer + " places " + "'"
                    + (currentPlayer == 1 ? "X" : "0") + "' at:");
            String turn = turnGenerator.humanTurn();

            field.get(turn).setValue(currentPlayer == 1 ? "X" : "0");
            currentPlayer = currentPlayer == 1 ? 2 : 1;
            turnNumber++;
            result = checker.checkFieldForWinner();
        }
        if (turnNumber > maxTurns) {
            if (result == -1) {
                result = 0;
            }
        }
    }

    public void battleWithCpu() {
        int currentPlayer = 1;
        int turnNumber = 1;
        int maxTurns = (int) Math.pow(fieldDimension, 2);
        while (result == -1 && turnNumber <= maxTurns) {
            String turn = null;
            if (currentPlayer == 1) {
                // human turn
                service.showCurrentField();
                System.out.println("player" + currentPlayer + " places 'X' at:");
                turn = turnGenerator.humanTurn();
            } else {
                // CPU turn generation
                turn = turnGenerator.cpuTurn();
            }

            field.get(turn).setValue(currentPlayer == 1 ? "X" : "0");
            currentPlayer = currentPlayer == 1 ? 2 : 1;
            turnNumber++;
            result = checker.checkFieldForWinner();
        }

        if (turnNumber > maxTurns) {
            if (result == -1) {
                result = 0;
            }
        }
    }

    public void showResults() {
        service.showResults(result);
    }

    enum Player {
        HUMAN, CPU
    }
}
