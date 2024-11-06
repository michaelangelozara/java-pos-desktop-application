package org.POS.backend.string_checker;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.Iterator;

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

    public static String getImageTypeFromBase64(String base64Image) {
        if(base64Image == null || base64Image.isEmpty())
            return "No Image";

        try {
            // Decode the Base64 string to bytes
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);

            // Create an ImageInputStream from the byte array
            try (ImageInputStream imageInputStream = ImageIO.createImageInputStream(new ByteArrayInputStream(imageBytes))) {

                // Get an iterator for image readers
                Iterator<ImageReader> readers = ImageIO.getImageReaders(imageInputStream);

                // If readers are available, get the format name
                if (readers.hasNext()) {
                    ImageReader reader = readers.next();
                    String formatName = reader.getFormatName();
                    reader.dispose();
                    return formatName.toUpperCase(); // JPEG, PNG, etc.
                } else {
                    return "UNKNOWN"; // If no readers were found, type is unknown
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }
    }
}
