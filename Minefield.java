public class Minefield {
    private final String[] input;

    public Minefield(String[] input) {

        this.input = input;
    }

    public String[] getHints() {
        String[] result = new String[input.length];
        for (int i = 0; i < input.length; i++) {
            result[i] = "0";
        }
        return result;
    }
}
