public class Cell {
    private String value = " ";
    private String xCoordinate;
    private int yCoordinate;

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public Cell(String xCoordinate, int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;

    }
}
