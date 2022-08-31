public class Minefield {
    private final String[] input;

    public Minefield(String[] input) {
        this.input = input;
    }

    public String[] getHints() {
        String[] hints = new String[input.length];
        for (int row = 0; row < input.length; row++) {
            StringBuilder currentRow = new StringBuilder();
            for (int column = 0; column < input[row].length(); column++) {
                currentRow.append(getHint(row, column));
            }
            hints[row] = currentRow.toString();
        }
        return hints;
    }

    private String getHint(int row, int column) {
        if (hasMine(row, column)) {
            return "*";
        }
        int mineCount = 0;
        if (hasMine(row, column-1)) {
            mineCount = 1;
        }
        if (hasMine(row, column+1)) {
            mineCount = 1;
        }
        return String.valueOf(mineCount);
    }

    private boolean hasMine(int row, int column) {
        if (column < 0 || input[row].length() <= column) return false;
        return input[row].charAt(column) == '*';
    }
}
