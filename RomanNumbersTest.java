import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RomanNumbersTest {
    @Test
    void shouldReturnIfor1() {
        assertEquals("I", RomanNumbers.toRomanNumber(1));
    }

    @Test
    void shouldReturnIIfor2() {
        assertEquals("II", RomanNumbers.toRomanNumber(2));
    }

    @Test
    void shouldReturnIIIfor3() {
        assertEquals("III", RomanNumbers.toRomanNumber(3));
    }

    @Test
    void shouldReturnIVfor4() {
        assertEquals("IV", RomanNumbers.toRomanNumber(4));
    }

    @Test
    void shouldReturnVfor5() {
        assertEquals("V", RomanNumbers.toRomanNumber(5));
    }

    @Test
    void shouldReturnVIfor6() {
        assertEquals("VI", RomanNumbers.toRomanNumber(6));
    }

    @Test
    void shouldReturnIXfor9() {
        assertEquals("IX", RomanNumbers.toRomanNumber(9));
    }

    @Test
    void shouldReturnXXXVIIIfor38() {
        assertEquals("XXXVIII", RomanNumbers.toRomanNumber(38));
    }

    @Test
    void theNumberOfTheBeast() {
        assertEquals("DCLXVI", RomanNumbers.toRomanNumber(666));
    }

    @Test
    void shouldReturn444() {
        assertEquals("CDXLIV", RomanNumbers.toRomanNumber(444));
    }

    @Test
    void shouldReturn999() {
        assertEquals("CMXCIX", RomanNumbers.toRomanNumber(999));
    }

    @Test
    void shouldReturn3999() {
        assertEquals("MMMCMXCIX", RomanNumbers.toRomanNumber(3999));
    }

}
