package io.github.gchape.ebookshop.utils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class DateUtils {
    private DateUtils() {
    }

    public static LocalDate parseLoosely(String date) {
        String[] parts = date.split("-");

        if (parts.length == 3) {
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[2]);
            return LocalDate.of(year, month, day);
        }

        if (parts.length == 2) {
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            return LocalDate.of(year, month, 1);
        }

        if (parts.length == 1) {
            int year = Integer.parseInt(parts[0]);
            return LocalDate.of(year, 1, 1);
        }

        throw new DateTimeParseException("Invalid date format", date, 0);
    }
}
