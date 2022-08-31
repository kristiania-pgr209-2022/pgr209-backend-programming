public class Minefield {
    private final String[] input;

    public Minefield(String[] input) {
        this.input = input;
    }

    public String[] getHints() {
        String[] hints = new String[input.length];
        for (int row = 0; row < input.length; row++) {
            hints[row] = "0";
        }
        return hints;
    }
}
