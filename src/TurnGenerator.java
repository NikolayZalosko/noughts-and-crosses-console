import java.util.Map;
import java.util.Scanner;

public class TurnGenerator {
    Map<String, Cell> field;
    int fieldDimension;
    private Scanner in = new Scanner(System.in);

    public TurnGenerator(Map<String, Cell> field, int fieldDimension) {
        this.field = field;
        this.fieldDimension = fieldDimension;
    }

    public String humanTurn() {
        String turn = null;
        do {
            turn = in.nextLine().toUpperCase();
        } while (!isTurnValid(turn));
        return turn;
    }

    private boolean isTurnValid(String turn) {
        if (!(turn.charAt(0) >= 'A' && turn.charAt(0) < 'A' + fieldDimension)
                || !(turn.charAt(1) >= '1' && turn.charAt(1) < '1' + fieldDimension)) {
            System.out.println("Incorrect position!");
            return false;
        } else if (!field.get(turn).getValue().equals(" ")) {
            System.out.println("This cell is already occupied");
            return false;
        }
        return true;
    }

    public String cpuTurn() {
        String turn = null;
        for (int y = fieldDimension; y >= 1; y--) {
            for (char x = 'A'; x < 'A' + fieldDimension; x++) {
                String cellName = String.valueOf(x) + y;
                if (field.get(cellName).getValue().equals(" ")) {
                    turn = cellName;
                    y = 0;
                    break;
                }
            }
        }
        return turn;
    }
}
