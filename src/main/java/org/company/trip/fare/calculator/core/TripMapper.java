package org.company.trip.fare.calculator.core;

import org.company.trip.fare.calculator.TripFareProcessor;
import org.company.trip.fare.calculator.constant.TapType;
import org.company.trip.fare.calculator.constant.TripStatus;
import org.company.trip.fare.calculator.model.Tap;
import org.company.trip.fare.calculator.model.Trip;
import org.company.trip.fare.calculator.model.TripsStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static org.company.trip.fare.calculator.constant.TripStatus.CANCELLED;
import static org.company.trip.fare.calculator.constant.TripStatus.COMPLETED;
import static org.company.trip.fare.calculator.util.DurationCalculator.calculateDuration;

/**
 * Trip mapper transforms tap data to trips
 * Identifying a customer by the PAN, it will trace the trips of each customer
 *
 */
@Component
public class TripMapper {

    private static final Logger LOG = LoggerFactory.getLogger(TripFareProcessor.class);
    private static final Map<String, TripsStack> tripsByCustomer = new HashMap<>();
    private final List<Trip> trips = new ArrayList<>();
    private final TripFareCalculator tripFareCalculator;

    public TripMapper(TripFareCalculator tripFareCalculator) {
        this.tripFareCalculator = tripFareCalculator;
    }

    public List<Trip> mapTapsToTrips(List<Tap> taps) {
        LOG.debug("Mapping taps to trips...");
        taps.forEach(tap -> {
            LOG.debug(format("Processing tap: %s", tap));
            String customer = tap.getPan();
            if (tripsByCustomer.containsKey(customer)) {
                processCustomerTrip(tap, customer);
            } else {
                createNewRecordForCustomer(tap, customer);
            }
        });
        processIncompleteTrips();
        return trips;
    }

    private void mapCompleteTrip(Tap start, Tap end) {
        Double fare = tripFareCalculator.calculateTripFare(start.getStopId(), end.getStopId());
        long duration = calculateDuration(start.getDateTimeUTC(), end.getDateTimeUTC());
        Trip trip = mapTripData(start, end, fare, duration, getTripStatus(start, end));
        trips.add(trip);
    }

    private void mapIncompleteTrip(Tap start) {
        Double fare = tripFareCalculator.calculateTripFare(start.getStopId());
        Trip trip = mapTripData(start, null, fare, 0, TripStatus.INCOMPLETE);
        trips.add(trip);
    }

    private TripStatus getTripStatus(Tap start, Tap end) {
        if (start.getStopId().equals(end.getStopId())) {
            return CANCELLED;
        } else {
            return COMPLETED;
        }
    }

    private void processIncompleteTrips() {
        tripsByCustomer.forEach((customer, trips) -> {
            while (!trips.isEmpty()) {
                Tap tap = trips.pop();
                if (TapType.ON.equals(tap.getTapType())) {
                    mapIncompleteTrip(tap);
                }
            }
        });
    }

    private void processCustomerTrip(Tap tap, String customer) {
        LOG.debug(format("Processing tap: %s to customer: %s", tap, customer));
        TripsStack trips = tripsByCustomer.get(customer);
        if (TapType.ON.equals(tap.getTapType())) {
            LOG.debug(format("Pushing tap: %s to stack", tap));
            trips.push(tap);
        } else {
            LOG.debug(format("Popping tap: %s from stack to complete trip", tap));
            Tap start = trips.pop();
            mapCompleteTrip(start, tap);
        }
    }

    private void createNewRecordForCustomer(Tap tap, String customer) {
        TripsStack tripsStack = new TripsStack();
        tripsStack.push(tap);
        tripsByCustomer.put(customer, tripsStack);
    }

    private Trip mapTripData(Tap start, Tap end, Double fare, long duration, TripStatus status) {
        return Trip.builder()
                .busId(start.getBusId())
                .pan(start.getPan())
                .chargeAmount(fare)
                .durationSecs(duration)
                .started(start.getDateTimeUTC().toString())
                .finished(end != null ? end.getDateTimeUTC().toString() : null)
                .fromStopId(start.getStopId())
                .toStopId(end != null ? end.getStopId() : null)
                .companyId(start.getCompanyId())
                .status(status)
                .build();
    }

}
