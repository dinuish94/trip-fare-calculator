package org.company.trip.fare.calculator.core;

import org.springframework.stereotype.Component;

/**
 * Trip fare calculator is responsible
 * for determining how the fare should be calculated
 */
@Component
public class TripFareCalculator {

    private final FareManager fareManager;

    public TripFareCalculator(FareManager fareManager) {
        this.fareManager = fareManager;
    }

    public Double calculateTripFare(String start) {
        return calculateTripFare(start, null);
    }

    public Double calculateTripFare(String start, String stop) {
        if (stop != null) {
            return fareManager.getFare(start, stop);
        } else {
            return fareManager.getMaximumFareForTrip(start);
        }
    }
}
