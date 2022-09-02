import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;


public class MinesweeperTest {
    @Test
    void shouldDisplayEmptyMinefield() {
        assertArrayEquals(
                new String[]{"0"},
                new Minefield(new String[]{"."}).getHints()
        );
    }

    @Test
    void shouldDisplayCorrectNumberOfRows() {
        assertArrayEquals(
                new String[]{"0", "0", "0"},
                new Minefield(new String[]{".", ".", "."}).getHints()
        );
    }

    @Test
    void shouldDisplayCorrectNumberOfColumns() {
        assertArrayEquals(
                new String[]{"0000"},
                new Minefield(new String[]{"...."}).getHints()
        );
    }

    @Test
    void shouldDisplayMines() {
        assertArrayEquals(
                new String[]{"*"},
                new Minefield(new String[]{"*"}).getHints()
        );
    }

    @Test
    void shouldDetectMinesOnTheSameRow() {
        assertArrayEquals(
                new String[]{"01*10"},
                new Minefield(new String[]{"..*.."}).getHints()
        );
    }

    @Test
    void shouldDetectMinesOnTheSameColumn() {
        assertArrayEquals(
                new String[]{"0", "1", "*", "1", "0"},
                new Minefield(new String[]{".", ".", "*", ".", "."}).getHints()
        );
    }

    @Test
    void shouldDetectMinesAroundCell() {
        assertArrayEquals(
                new String[]{"111", "1*1", "111"},
                new Minefield(new String[]{"...", ".*.", "..."}).getHints()
        );
    }

    @Test
    void shouldCountMinesAroundCell() {
        assertArrayEquals(
                new String[]{"***", "*8*", "***"},
                new Minefield(new String[]{"***", "*.*", "***"}).getHints()
        );
    }

    @Test
    void shouldDisplayMinefield() {
        assertArrayEquals(
                new String[]{
                        "02**2*100",
                        "02*432100",
                        "0112*1000",
                        "000222000",
                        "0001*1000",
                        "000123210",
                        "00001**20",
                        "111013*20",
                        "1*1001110"
                },
                new Minefield(new String[]{
                        "..**.*...",
                        "..*......",
                        "....*....",
                        ".........",
                        "....*....",
                        ".........",
                        ".....**..",
                        "......*..",
                        ".*......."
                }).getHints()
        );
    }
}
