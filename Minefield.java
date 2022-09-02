public class Minefield {
    private final String[] input;

    public Minefield(String[] input) {
        this.input = input;
    }

    public String[] getHints() {
        String[] result = new String[input.length];
        for (int row = 0; row < result.length; row++) {
            String rowString = "";
            for (int column = 0; column < input[row].length(); column++) {
                if (hasMineInCell(row, column)) {
                    rowString += "*";
                } else {
                    rowString += countMinesAroundCell(row, column);
                }
            }
            result[row] = rowString;
        }
        return result;
    }

    private int countMinesAroundCell(int row, int column) {
        int mineCount = 0;
        for (int r = row - 1; r <= row + 1; r++) {
            for (int c = column - 1; c <= column + 1 ; c++) {
                if (hasMineInCell(r, c)) {
                    mineCount += 1;
                }
            }
        }
        return mineCount;
    }

    private boolean hasMineInCell(int row, int column) {
        if (row < 0 || input.length <= row) {
            return false;
        }
        if (column < 0 || input[row].length() <= column) {
            return false;
        }
        return input[row].charAt(column) == '*';
    }
}
