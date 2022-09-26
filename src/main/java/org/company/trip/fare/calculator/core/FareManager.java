package org.company.trip.fare.calculator.core;

import org.company.trip.fare.calculator.exception.TripChargeCalculatorException;
import org.company.trip.fare.calculator.model.Fare;
import org.company.trip.fare.calculator.model.FareIdentifier;
import org.company.trip.fare.calculator.util.CsvFileReader;

import java.util.*;

import static org.company.trip.fare.calculator.model.FareIdentifier.createKey;

public class FareManager {

    private static final String fareDataFileName = "fares.csv";
    private static final FareManager fareManager = new FareManager();
    private final Map<FareIdentifier, Double> allFares = new HashMap<>();
    private final List<String> stops = new ArrayList<>();

    private FareManager() {
    }

    public static FareManager getFareManager() {
        return fareManager;
    }

    public void initializeFareData() throws TripChargeCalculatorException {
        List<Fare> fares = readFareDataFromFile();
        populateCacheWithFareData(fares);
    }

    public Double getFare(String stop1, String stop2) {
        if (stop1.equals(stop2)) {
            return 0d;
        } else if (allFares.containsKey(createKey(stop1, stop2))) {
            return allFares.get(createKey(stop1, stop2));
        } else {
            return allFares.getOrDefault(createKey(stop2, stop1), null);
        }
    }

    public Double getMaximumFareForTrip(String fromStopId) {
        Optional<Double> maxFare = stops.stream().map(stop -> getFare(fromStopId, stop)).max(Comparator.naturalOrder());
        return maxFare.get();
    }

    private List<Fare> readFareDataFromFile() {
        CsvFileReader<Fare> reader = new CsvFileReader<>();
        return reader.read(fareDataFileName, Fare.class);
    }

    private void populateCacheWithFareData(List<Fare> fares) {
        fares.forEach(fare -> {
            String fromStopId = fare.getStart();
            String toStopId = fare.getEnd();
            allFares.put(createKey(fromStopId, toStopId), fare.getCost());
            addStop(fromStopId);
            addStop(toStopId);
        });
    }

    private void addStop(String stop) {
        if (!stops.contains(stop)) {
            stops.add(stop);
        }
    }
}
