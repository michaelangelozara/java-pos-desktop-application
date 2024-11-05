package org.POS.backend.string_checker;

public class StringChecker {

    public static boolean isNumericOnly(String input) {
        // Check if the input is empty or null, if so return false
        if (input == null || input.isEmpty()) {
            return false;
        }

        boolean hasDecimalPoint = false;

        for (char c : input.toCharArray()) {
            // Check if the character is a period
            if (c == '.') {
                // If there's already a decimal point, return false
                if (hasDecimalPoint) {
                    return false;
                }
                // Set the flag to true to indicate a decimal point has been encountered
                hasDecimalPoint = true;
            }
            // If the character is not a digit and not a period, return false
            else if (!Character.isDigit(c)) {
                return false;
            }
        }

        // If all characters are digits, return true
        return true;
    }
}
