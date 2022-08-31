public class Minefield {
    private final String[] input;

    public Minefield(String[] input) {
        this.input = input;
    }

    public String[] getHints() {
        String[] hints = new String[input.length];
        for (int row = 0; row < input.length; row++) {
            String currentRow = "";
            for (int j = 0; j < input[row].length(); j++) {
                currentRow += "0";
            }
            hints[row] = currentRow;
        }
        return hints;
    }
}
