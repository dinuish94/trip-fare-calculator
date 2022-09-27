package org.company.trip.fare.calculator.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Calculates duration between given two times
 */
public class DurationCalculator {

    public static long calculateDuration(LocalDateTime startTime, LocalDateTime endTime) {
        return ChronoUnit.SECONDS.between(startTime, endTime);
    }
}
