package org.company.trip.fare.calculator.util;

import com.opencsv.bean.AbstractBeanField;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Converts to local date time
 */
public class LocalDateConverter extends AbstractBeanField {
    @Override
    protected LocalDateTime convert(String s) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return LocalDateTime.parse(s, formatter);
    }
}
