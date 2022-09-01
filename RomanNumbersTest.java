import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RomanNumbersTest {
    @Test
    void shouldReturnIfor1() {
        assertEquals("I", toRomanNumber(1));
    }

    private String toRomanNumber(int i) {
        return "I";
    }
}
