package io.github.gchape.ebookshop.utils;

import java.security.SecureRandom;
import java.util.List;

public class UniqueUtils {
    private static final List<String> ADJECTIVES = List.of("Cool", "Epic", "Fast", "Mysterious", "Brave", "Witty", "Clever", "Loyal", "Swift", "Fierce");
    private static final List<String> NOUNS = List.of("Tiger", "Falcon", "Wizard", "Knight", "Panda", "Shadow", "Fox", "Dragon", "Rider", "Ninja");
    private static final String seed = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private final static SecureRandom secureRandom = new SecureRandom();

    private UniqueUtils() {
    }

    public static String username() {
        var idx1 = secureRandom.nextInt(ADJECTIVES.size() - 1);
        var idx2 = secureRandom.nextInt(NOUNS.size() - 1);
        var num = secureRandom.nextInt(1000);

        return ADJECTIVES.get(idx1) + NOUNS.get(idx2) + num;
    }

    public static String string(int length) {
        StringBuilder randomString = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(seed.length());
            randomString.append(seed.charAt(randomIndex));
        }

        return randomString.toString();
    }

    public static double price() {
        double randomPrice = secureRandom.nextDouble(10.00, 100.00);
        return Double.parseDouble(String.format("%.2f", randomPrice));
    }

    public static String ISBN13() {
        String prefix = "978";
        String groupIdentifier = String.format("%03d", secureRandom.nextInt(1000));
        String publisherIdentifier = String.format("%04d", secureRandom.nextInt(10000));
        String titleIdentifier = String.format("%03d", secureRandom.nextInt(1000));

        String first12Digits = prefix + groupIdentifier + publisherIdentifier + titleIdentifier;
        int checksum = calculateISBN13CheckDigit(first12Digits);
        return first12Digits + checksum;
    }

    private static int calculateISBN13CheckDigit(String first12Digits) {
        int sum = 0;
        for (int i = 0; i < first12Digits.length(); i++) {
            int digit = Character.getNumericValue(first12Digits.charAt(i));
            if (i % 2 == 0) {
                sum += digit;
            } else {
                sum += digit * 3;
            }
        }
        int remainder = sum % 10;
        return (remainder == 0) ? 0 : 10 - remainder;
    }
}
