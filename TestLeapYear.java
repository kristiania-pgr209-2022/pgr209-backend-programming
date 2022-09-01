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

    @Test
    void everyFourHundredYearIsLeapYear(){
        assertTrue(isLeapYear(2000));
    }

    private boolean isLeapYear(int year) {
        if (year % 400 == 0) return true;
        if (year % 4 != 0) return false;
        if (year % 100 == 0) return false;
        return true;
    }

}
