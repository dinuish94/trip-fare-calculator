package org.company.trip.fare.calculator.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DurationCalculator {

    public static long calculateDuration(LocalDateTime startTime, LocalDateTime endTime) {
        return ChronoUnit.SECONDS.between(startTime, endTime);
    }
}
