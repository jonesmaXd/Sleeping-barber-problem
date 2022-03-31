package utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * A simple utility class for preventing code duplication
 *
 * Note - It would be better practise to have two separate utility classes
 * for the random number and current time, but since the size of the project is rather small
 * there is only 1 class for all utilities, for simplicity.
 */
public class Utility {

    /**
     * Generates a random number in a defined range
     * @param max the maxiumum range
     * @param min the minimum range
     * @return the randomly generated number
     */
    public static int getRandomNumberInRange(int max, int min) {
        Random random = new Random();
        int randomNumber = random.nextInt(max - min) + min;
        return randomNumber;
    }

    /**
     * A string with the current time on format yyyy/MM/dd HH:mm:ss
     * @return the string with current time
     */
    public static String getCurrentTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
}
