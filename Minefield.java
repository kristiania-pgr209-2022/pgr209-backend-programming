public class Minefield {
    private final String[] input;

    public Minefield(String[] input) {
        this.input = input;
    }

    public String[] getHints() {
        String[] hints = new String[input.length];
        for (int row = 0; row < input.length; row++) {
            String currentRow = "";
            for (int column = 0; column < input[row].length(); column++) {
                currentRow += getHint(row, column);
            }
            hints[row] = currentRow;
        }
        return hints;
    }

    private static String getHint(int row, int column) {
        return "0";
    }
}
