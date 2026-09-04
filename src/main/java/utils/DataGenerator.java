package utils;

import java.util.Random;
import java.util.UUID;

/**
 * DataGenerator — Utility for generating dynamic, unique test data.
 * Ensures tests running in parallel never conflict due to shared static data.
 */
public class DataGenerator {

    private static final Random RANDOM = new Random();

    /**
     * Generates a unique email with timestamp and short UUID.
     * Format: [prefix]_[timestamp]_[shortUuid]@maildomain.com
     * Example: auto_login_1725450000123_a1b2@maildomain.com
     */
    public static String generateEmail(String prefix) {
        String shortId = UUID.randomUUID().toString().substring(0, 4);
        return String.format("auto_%s_%d_%s@maildomain.com", prefix, System.currentTimeMillis(), shortId);
    }

    /**
     * Generates a unique username.
     */
    public static String generateUsername(String prefix) {
        String shortId = UUID.randomUUID().toString().substring(0, 5);
        return String.format("%s_%s", prefix, shortId);
    }

    /**
     * Generates a secure random password with mixed characters.
     */
    public static String generatePassword() {
        return "Pass@" + (100000 + RANDOM.nextInt(900000));
    }

    /**
     * Generates a random number in a given range [min, max].
     */
    public static int getRandomNumber(int min, int max) {
        return RANDOM.nextInt((max - min) + 1) + min;
    }
}
