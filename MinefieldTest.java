import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class MinefieldTest {

    @Test
    void shouldShowEmptyMinefield() {
        assertArrayEquals(new String[]{"0"},
                new Minefield(new String[]{"."}).getHints());
    }

    @Test
    void shouldHaveCorrectNumberOfRows() {
        assertArrayEquals(new String[]{"0", "0"},
                new Minefield(new String[]{".", "."}).getHints());
    }

    @Test
    void shouldHaveCorrectNumberOfColumns() {
        assertArrayEquals(new String[]{"000"},
                new Minefield(new String[]{"..."}).getHints());
    }

    @Test
    void shouldDisplayMines() {
        assertArrayEquals(new String[] {"***", "***"},
                new Minefield(new String[]{"***", "***"}).getHints());
    }

}
