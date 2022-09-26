package org.company.trip.fare.calculator.core;

import static org.company.trip.fare.calculator.core.FareManager.getFareManager;

public class TripFareCalculator {

    public static Double calculateCompleteTripFare(String start) {
        return calculateCompleteTripFare(start, null);
    }

    public static Double calculateCompleteTripFare(String start, String stop) {
        if (stop != null) {
            return getFareManager().getFare(start, stop);
        } else {
            return getFareManager().getMaximumFareForTrip(start);
        }
    }
}
