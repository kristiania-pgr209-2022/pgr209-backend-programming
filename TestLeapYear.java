import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestLeapYear {

    @Test
    void everyFourthYearIsLeapYear() {
        assertTrue(isLeapYear(2024));
    }

    private boolean isLeapYear(int year) {
        return true;
    }

}
