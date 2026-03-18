package com.doctor.dr.usercredential.service.passwordgenerator;

import java.security.SecureRandom;

public final class SecurePasswordGenerator {

    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL = "!@#$%^&*()-_=+[]{}|;:,.<>?";

    private static final String ALL = UPPER + LOWER + DIGITS + SPECIAL;

    private static final SecureRandom RANDOM = new SecureRandom();

    // Prevent instantiation
    private SecurePasswordGenerator() {
    }

    /**
     * Generates a secure random password.
     *
     * @param length total length of password (must be >= 4)
     * @return generated password string
     */
    public static String generate(int length) {
        if (length < 4) {
            throw new IllegalArgumentException("Password length must be at least 4");
        }

        StringBuilder password = new StringBuilder(length);

        // Ensure at least one character from each category
        password.append(randomChar(UPPER));
        password.append(randomChar(LOWER));
        password.append(randomChar(DIGITS));
        password.append(randomChar(SPECIAL));

        // Fill remaining characters
        for (int i = 4; i < length; i++) {
            password.append(randomChar(ALL));
        }

        // Shuffle (Fisher-Yates)
        shuffle(password);

        return password.toString();
    }

    private static char randomChar(String charset) {
        return charset.charAt(RANDOM.nextInt(charset.length()));
    }

    private static void shuffle(StringBuilder sb) {
        for (int i = sb.length() - 1; i > 0; i--) {
            int j = RANDOM.nextInt(i + 1);

            char temp = sb.charAt(i);
            sb.setCharAt(i, sb.charAt(j));
            sb.setCharAt(j, temp);
        }
    }
}