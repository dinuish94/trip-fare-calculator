package org.company.trip.fare.calculator.core;

import org.company.trip.fare.calculator.model.Fare;
import org.company.trip.fare.calculator.model.FareIdentifier;
import org.company.trip.fare.calculator.util.CsvFileReader;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.company.trip.fare.calculator.model.FareIdentifier.createKey;

/**
 * FareManager is responsible for initiating fare data
 * and determining the fare for given stops according to that data
 *
 */
@Component
public class FareManager {

    private static final String fareDataFilePath = "src/main/resources/fares.csv";
    private final Map<FareIdentifier, Double> allFares = new HashMap<>();
    private final List<String> stops = new ArrayList<>();
    private final CsvFileReader<Fare> reader;

    public FareManager(CsvFileReader<Fare> reader) {
        this.reader = reader;
    }

    public void initializeFareData() {
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
        return reader.read(fareDataFilePath, Fare.class);
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
