import java.util.Map;

public class Checker {
    private Map<String, Cell> field;
    private int fieldDimension;
    private final String crossWinPattern;
    private final String noughtWinPattern;

    public Checker(Map<String, Cell> field, int fieldDimension) {
        this.field = field;
        this.fieldDimension = fieldDimension;

        // define win patterns (e.g. "XXX" and "000" for a 3x3 field)
        String crossPatternResult = "";
        String noughtPatternResult = "";
        for (int i = 1; i <= fieldDimension; i++) {
            crossPatternResult += "X";
            noughtPatternResult += "0";
        }
        crossWinPattern = crossPatternResult;
        noughtWinPattern = noughtPatternResult;
    }
    public int checkFieldForWinner() {
        int result = -1;
        result = checkRows();
        if (result == -1) {
            result = checkColumns();
            if (result == -1) {
                result = checkDiagonals();
            }
        }
        return result;
    }

    private int checkRows() {
        String currentPattern = "";
        for (int y = fieldDimension; y >= 1; y--) {
            for (char x = 'A'; x < 'A' + fieldDimension; x++) {
                String cellName = String.valueOf(x) + y;
                currentPattern += field.get(cellName).getValue();
            }
            if (currentPattern.equals(crossWinPattern)) {
                return 1;
            } else if (currentPattern.equals(noughtWinPattern)) {
                return 2;
            } else {
                currentPattern = "";
            }
        }
        return -1;
    }

    private int checkColumns() {
        String currentPattern = "";
        for (char x = 'A'; x < 'A' + fieldDimension; x++) {
            for (int y = 1; y <= fieldDimension; y++) {
                String cellName = String.valueOf(x) + y;
                currentPattern += field.get(cellName).getValue();
            }
            if (currentPattern.equals(crossWinPattern)) {
                return 1;
            } else if (currentPattern.equals(noughtWinPattern)) {
                return 2;
            } else {
                currentPattern = "";
            }
        }
        return -1;
    }

    private int checkDiagonals() {
        String currentPattern = "";
        for (int i = 0; i < fieldDimension; i++) {
            String cellName = Character.toString((char)('A' + i)) + (i + 1);
            currentPattern += field.get(cellName).getValue();
        }
        if (currentPattern.equals(crossWinPattern)) {
            return 1;
        } else if (currentPattern.equals(noughtWinPattern)) {
            return 2;
        } else {
            currentPattern = "";
        }

        for (int i = 0; i < fieldDimension; i++) {
            String cellName = Character.toString((char)('A' + i)) + (fieldDimension - i);
            currentPattern += field.get(cellName).getValue();
        }
        if (currentPattern.equals(crossWinPattern)) {
            return 1;
        } else if (currentPattern.equals(noughtWinPattern)) {
            return 2;
        }
        return -1;
    }
}
