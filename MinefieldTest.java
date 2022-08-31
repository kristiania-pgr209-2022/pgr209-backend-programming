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

    @Test
    void shouldShowHintsOnSameRowAsMine() {
        assertArrayEquals(new String[] { "01*10" },
                new Minefield(new String[] {"..*.."}).getHints());
    }

    @Test
    void shouldShowHintsOnSameColumnAsMine() {
        assertArrayEquals(new String[] { "0","1","*","1","0" },
                new Minefield(new String[] {".",".","*",".","."}).getHints());
    }

    @Test
    void shouldShowHintsAroundMine() {
        assertArrayEquals(new String[] { "111","1*1","111" },
                new Minefield(new String[] {"...",".*.","..."}).getHints());
    }

    @Test
    void shouldCountMinesAroundCell() {
        assertArrayEquals(new String[] {"***", "*8*", "***"},
                new Minefield(new String[] {"***", "*.*", "***"}).getHints());
    }
}
