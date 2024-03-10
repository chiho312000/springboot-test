package com.example.demo.util;

import com.example.demo.exception.InternalServerErrorException;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class DateTimeUtil {

    public static ZoneId DEFAULT_ZONE = ZoneId.of("Asia/Hong_Kong");

    public static DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
            .parseDefaulting(ChronoField.NANO_OF_SECOND, 000).toFormatter().withLocale(Locale.UK)
            .withZone(DEFAULT_ZONE);

    public static Instant stringToInstant(String input) throws Exception {
        try {
            if (input.endsWith("Z")) {
                return Instant.parse(input);
            } else {
                return LocalDateTime.parse(input, dateTimeFormatter).atZone(DEFAULT_ZONE).toInstant();
            }
        } catch (Exception e) {
            throw new InternalServerErrorException("Wrong DateTime format");
        }

    }

    public static OffsetDateTime stringToOffsetDateTime(String input) throws Exception {
        try {
            return OffsetDateTime.parse(input, dateTimeFormatter);
        } catch (Exception e) {
            throw new InternalServerErrorException("Wrong DateTime format");
        }
    }

    public static Instant stringToInstant2(String input) {
        return LocalDateTime.parse(input, dateTimeFormatter).atZone(DEFAULT_ZONE).toInstant();

    }

    public static String instantToIsoString(Instant input) {
        return dateTimeFormatter.format(input);
    }

    public static String offsetDateTimeToIsoString(OffsetDateTime dateTime) {
        return dateTimeFormatter.format(dateTime);
    }

    public static String offsetDateTimeToIsoString(OffsetDateTime dateTime, String format) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern(format)
                .parseDefaulting(ChronoField.NANO_OF_SECOND, 000).toFormatter().withLocale(Locale.UK)
                .withZone(DEFAULT_ZONE);
        return formatter.format(dateTime);
    }

    public String dateTimeStringToIsoString(String input) throws Exception {
        return dateTimeFormatter.format(stringToInstant(input));
    }

    public static LocalDate getLocalDateOfInstant(Instant date) {
        return date.atZone(DEFAULT_ZONE).truncatedTo(ChronoUnit.DAYS).toLocalDate();
    }

    public static OffsetDateTime getOffsetDateTimeOfInstant(Instant date) {
        return date.atZone(DEFAULT_ZONE).toOffsetDateTime();
    }

    public static LocalDateTime getLocalDateTimeOfInstant(Instant date) {
        return date.atZone(DEFAULT_ZONE).toLocalDateTime();
    }

    public static long getDaysBetween(Instant startInstant, Instant endInstant) {
        return ChronoUnit.DAYS.between(getLocalDateOfInstant(startInstant), getLocalDateOfInstant(endInstant));
    }

    public static long getDaysBetween(OffsetDateTime startDate, OffsetDateTime endDate) {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }
}
