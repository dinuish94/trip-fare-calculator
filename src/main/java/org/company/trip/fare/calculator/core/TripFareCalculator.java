package org.company.trip.fare.calculator.core;

import static org.company.trip.fare.calculator.core.FareManager.getFareManager;

public class TripFareCalculator {

    public static Double calculateTripFare(String start) {
        return calculateTripFare(start, null);
    }

    public static Double calculateTripFare(String start, String stop) {
        if (stop != null) {
            return getFareManager().getFare(start, stop);
        } else {
            return getFareManager().getMaximumFareForTrip(start);
        }
    }
}
