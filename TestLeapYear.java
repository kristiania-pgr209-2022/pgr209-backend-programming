import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestLeapYear {

    @Test
    void everyForthYearIsLeapYear() {
        assertTrue(isLeapYear(2024));
    }

    private boolean isLeapYear(int i) {
        return false;
    }

}
