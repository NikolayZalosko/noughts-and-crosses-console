import java.util.*;

/**
 * player1 is always human
 * player1 always uses 'X'
 *
 * player2 can be either human or CPU
 * player2 always uses '0'
 */
public class Game {
    private Map<String, Cell> field;
    private int fieldDimension = 3;
    private Player player2;

    /**
     * -1 -> no results yet
     *  0 -> draw
     *  1 -> player1 won
     *  2 -> player2 won
     */
    private int result = -1;

    private static Scanner in = new Scanner(System.in);

    public Game() {
        field = new HashMap<>();
        for (int y = 1; y <= fieldDimension; y++) {
            for (char x = 'A'; x < 'A' + fieldDimension; x++) {
                String cellName = String.valueOf(x) + y;
                field.put(cellName, new Cell(String.valueOf(x), y));
            }
        }
        System.out.println("Welcome to noughts-and-crosses game.\n" +
                "Who will be your enemy? (H - human / C - cpu)");

        // Choosing an enemy
        while (player2 == null) {
            String enemy = in.nextLine().toUpperCase();
            if (enemy.equals("H")) {
                player2 = Player.HUMAN;
            } else if (enemy.equals("C")) {
                player2 = Player.CPU;
            } else {
                System.out.println("No such option!");
            }
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        if (game.player2 == Player.HUMAN) {
            game.battleWithHuman();
        } else {
            game.battleWithCpu();
        }
        game.showResults();
    }

    private void battleWithHuman() {
        int currentPlayer = 1;
        int turnNumber = 1;
        int maxTurns = (int) Math.pow(fieldDimension, 2);
        while (result == -1 && turnNumber < maxTurns) {
            showCurrentField();
            System.out.println("player" + currentPlayer + " places " + "'"
                    + (currentPlayer == 1 ? "X" : "0") + "' at:");
            String turn = null;
            while (turn == null) {
                turn = in.nextLine().toUpperCase();
                if (!(turn.charAt(0) >= 'A' && turn.charAt(0) < 'A' + fieldDimension)
                        || !(turn.charAt(1) >= '1' && turn.charAt(1) < '1' + fieldDimension)) {
                    System.out.println("Incorrect position!");
                    turn = null;
                } else if (!field.get(turn).getValue().equals(" ")) {
                    System.out.println("This cell is already occupied");
                    turn = null;
                }
            }
            field.get(turn).setValue(currentPlayer == 1 ? "X" : "0");
            currentPlayer = currentPlayer == 1 ? 2 : 1;
            turnNumber++;
            updateResult();
        }
        if (turnNumber == maxTurns) {
            result = 0;
        }
    }

    private void battleWithCpu() {
        int currentPlayer = 1;
        int turnNumber = 1;
        int maxTurns = (int) Math.pow(fieldDimension, 2);
        while (result == -1 && turnNumber <= maxTurns) {
            if (currentPlayer == 1) {
                // human turn
                showCurrentField();
                System.out.println("player" + currentPlayer + " places 'X' at:");
                String turn = null;
                while (turn == null) {
                    turn = in.nextLine().toUpperCase();
                    // validating input
                    if (!(turn.charAt(0) >= 'A' && turn.charAt(0) < 'A' + fieldDimension)
                            || !(turn.charAt(1) >= '1' && turn.charAt(1) < '1' + fieldDimension)) {
                        System.out.println("Incorrect position!");
                        turn = null;
                    } else if (!field.get(turn).getValue().equals(" ")) {
                        System.out.println("This cell is already occupied");
                        turn = null;
                    }
                }
                field.get(turn).setValue("X");
            } else {
                // CPU turn generation
                for (int y = fieldDimension; y >= 1; y--) {
                    for (char x = 'A'; x < 'A' + fieldDimension; x++) {
                        String cellName = String.valueOf(x) + y;
                        if (field.get(cellName).getValue().equals(" ")) {
                            field.get(cellName).setValue("0");
                            y = 0;
                            break;
                        }
                    }
                }
            }
            currentPlayer = currentPlayer == 1 ? 2 : 1;
            turnNumber++;
            updateResult();
        }

        if (turnNumber > maxTurns) {
            if (result == -1) {
                result = 0;
            }
        }
    }

    private void updateResult() {
        // check the field for a winner
        StringBuilder crossWinPattern = new StringBuilder();
        StringBuilder noughtWinPattern = new StringBuilder();
        for (int i = 1; i <= fieldDimension; i++) {
            crossWinPattern.append("X");
            noughtWinPattern.append("0");
        }

        StringBuilder currentPattern = new StringBuilder();
        // iterate on rows
        for (int y = fieldDimension; y >= 1; y--) {
            for (char x = 'A'; x < 'A' + fieldDimension; x++) {
                String cellName = String.valueOf(x) + y;
                currentPattern.append(field.get(cellName).getValue());
            }
            if (currentPattern.toString().equals(crossWinPattern.toString())) {
                result = 1;
                return;
            } else if (currentPattern.toString().equals(noughtWinPattern.toString())) {
                result = 2;
                return;
            } else {
                currentPattern = new StringBuilder();
            }
        }
        currentPattern = new StringBuilder();


        // iterate on columns
        for (char x = 'A'; x < 'A' + fieldDimension; x++) {
            for (int y = 1; y <= fieldDimension; y++) {
                String cellName = String.valueOf(x) + y;
                currentPattern.append(field.get(cellName).getValue());
            }
            if (currentPattern.toString().equals(crossWinPattern.toString())) {
                result = 1;
                return;
            } else if (currentPattern.toString().equals(noughtWinPattern.toString())) {
                result = 2;
                return;
            } else {
                currentPattern = new StringBuilder();
            }
        }
        currentPattern = new StringBuilder();

        // iterate on diagonals
        for (int i = 0; i < fieldDimension; i++) {
            String cellName = Character.toString((char)('A' + i)) + (i + 1);
            currentPattern.append(field.get(cellName).getValue());
        }
        if (currentPattern.toString().equals(crossWinPattern.toString())) {
            result = 1;
            return;
        } else if (currentPattern.toString().equals(noughtWinPattern.toString())) {
            result = 2;
            return;
        } else {
            currentPattern = new StringBuilder();
        }
        for (int i = 0; i < fieldDimension; i++) {
            String cellName = Character.toString((char)('A' + i)) + (fieldDimension - i);
            currentPattern.append(field.get(cellName).getValue());
        }
        if (currentPattern.toString().equals(crossWinPattern.toString())) {
            result = 1;
        } else if (currentPattern.toString().equals(noughtWinPattern.toString())) {
            result = 2;
        }
    }

    private void showCurrentField() {
        for (int y = fieldDimension; y >= 1; y--) {
            System.out.print(y + " ");
            for (char x = 'A'; x < 'A' + fieldDimension; x++) {
                String cellName = String.valueOf(x) + y;
                System.out.print(field.get(cellName).getValue() + " ");
            }
            System.out.println();
        }
        System.out.print("  ");
        for (char x = 'A'; x < 'A' + fieldDimension; x++) {
            System.out.print(x + " ");
        }
        System.out.println();
    }



    private void showResults() {
        showCurrentField();
        switch (result) {
            case 0:
                System.out.println("Draw.");
                break;
            case 1:
                System.out.println("player1 has won!");
                break;
            case 2:
                System.out.println("player2 has won!");
                break;
            default:
                System.out.println("Sorry, something went wrong.");
        }
    }

    private enum Player {
        HUMAN, CPU
    }
}
