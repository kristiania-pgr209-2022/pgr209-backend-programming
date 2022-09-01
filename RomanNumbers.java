public class RomanNumbers {
    public static String toRomanNumber(int number) {
        StringBuilder result = new StringBuilder();
        number = convertDigit(result, number, 1000, "M");
        number = convertDigit(result, number, 900, "CM");
        number = convertDigit(result, number, 500, "D");
        number = convertDigit(result, number, 400, "CD");
        number = convertDigit(result, number, 100, "C");
        number = convertDigit(result, number, 90, "XC");
        number = convertDigit(result, number, 50, "L");
        number = convertDigit(result, number, 40, "XL");
        number = convertDigit(result, number, 10, "X");
        number = convertDigit(result, number, 9, "IX");
        number = convertDigit(result, number, 5, "V");
        number = convertDigit(result, number, 4, "IV");
        convertDigit(result, number, 1, "I");
        return result.toString();
    }

    private static int convertDigit(StringBuilder result, int number, int digitValue, String digitSymbol) {
        while (number >= digitValue) {
            result.append(digitSymbol);
            number -= digitValue;
        }
        return number;
    }
}
