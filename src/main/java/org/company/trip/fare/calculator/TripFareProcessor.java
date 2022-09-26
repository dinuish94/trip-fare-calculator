package org.company.trip.fare.calculator;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.company.trip.fare.calculator.exception.TripChargeCalculatorException;
import org.company.trip.fare.calculator.model.Tap;
import org.company.trip.fare.calculator.model.Trip;
import org.company.trip.fare.calculator.util.ConfigReader;
import org.company.trip.fare.calculator.util.CsvFileWriter;

import java.util.List;

import static org.company.trip.fare.calculator.constant.ConfigProperties.PROPERTY_KEY_TRIPS_FILE;
import static org.company.trip.fare.calculator.core.FareManager.getFareManager;
import static org.company.trip.fare.calculator.core.TapsReader.readTaps;
import static org.company.trip.fare.calculator.core.TripMapper.getTripMapper;

public class TripFareProcessor {

    private static final Logger logger = LogManager.getLogger(TripFareProcessor.class);

    public static void main(String[] args) throws TripChargeCalculatorException {
        logger.info("Starting trip fare calculation...");
        initializeLogger();
        initializeFareData();
        List<Tap> taps = readTapData();
        List<Trip> trips = mapTapsToTrips(taps);
        writeTripData(trips);
        logger.info("Completed trip fare calculation...");
    }

    private static void initializeLogger() {
        BasicConfigurator.configure();
    }

    private static List<Trip> mapTapsToTrips(List<Tap> taps) {
        return getTripMapper().mapTapsToTrips(taps);
    }

    private static List<Tap> readTapData() throws TripChargeCalculatorException {
        return readTaps();
    }

    private static void writeTripData(List<Trip> trips) throws TripChargeCalculatorException {
        String tripFileName = ConfigReader.getConfigReader().getProperty(PROPERTY_KEY_TRIPS_FILE);
        new CsvFileWriter<Trip>().writeFile(tripFileName, trips);
    }

    private static void initializeFareData() throws TripChargeCalculatorException {
        logger.info("Initializing fare data...");
        getFareManager().initializeFareData();
    }
}
