import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestLeapYear {

    @Test
    void everyFourthYearIsLeapYear() {
        assertTrue(isLeapYear(2024));
    }

    @Test
    void everyNoneFourthIsNotLeap() {
        assertFalse(isLeapYear(2022));
    }
    @Test
    void everyHundredYearIsNotLeapYear(){
        assertFalse(isLeapYear(2100));
    }

    private boolean isLeapYear(int year) {
        if (year % 100 == 0) {
            return false;
        }
        return year % 4 == 0;
    }

}
