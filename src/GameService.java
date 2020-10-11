import java.util.Map;
import java.util.Scanner;

public class GameService {
    Map<String, Cell> field;
    int fieldDimension;
    private Scanner in = new Scanner(System.in);

    public GameService(Map<String, Cell> field, int fieldDimension) {
        this.field = field;
        this.fieldDimension = fieldDimension;
    }

    public void fillField() {
        for (int y = 1; y <= fieldDimension; y++) {
            for (char x = 'A'; x < 'A' + fieldDimension; x++) {
                String cellName = String.valueOf(x) + y;
                field.put(cellName, new Cell());
            }
        }
    }

    public Game.Player choosePlayer2() {
        System.out.println("Welcome to noughts-and-crosses game.\n" +
                "Who will be your enemy? (H - human / C - cpu)");
        Game.Player player2 = null;
        while (player2 == null) {
            String enemy = in.nextLine().toUpperCase();
            if (enemy.equals("H")) {
                player2 = Game.Player.HUMAN;
            } else if (enemy.equals("C")) {
                player2 = Game.Player.CPU;
            } else {
                System.out.println("No such option!");
            }
        }
        return player2;
    }

    public void showCurrentField() {
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

    public void showResults(int result) {
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
}
