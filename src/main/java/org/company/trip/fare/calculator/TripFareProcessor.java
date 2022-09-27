package org.company.trip.fare.calculator;

import org.company.trip.fare.calculator.constant.ConfigProperties;
import org.company.trip.fare.calculator.core.FareManager;
import org.company.trip.fare.calculator.core.TapsReader;
import org.company.trip.fare.calculator.core.TripMapper;
import org.company.trip.fare.calculator.exception.TripChargeCalculatorException;
import org.company.trip.fare.calculator.model.Tap;
import org.company.trip.fare.calculator.model.Trip;
import org.company.trip.fare.calculator.util.CsvFileWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.List;

@SpringBootApplication
@EnableConfigurationProperties
public class TripFareProcessor implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(TripFareProcessor.class);
    private final ConfigProperties properties;
    private final TapsReader tapsReader;
    private final FareManager fareManager;
    private final CsvFileWriter<Trip> tripWriter;
    private final TripMapper tripMapper;

    public TripFareProcessor(ConfigProperties properties, TapsReader tapsReader, FareManager fareManager, CsvFileWriter<Trip> tripWriter, TripMapper tripMapper) {
        this.properties = properties;
        this.tapsReader = tapsReader;
        this.fareManager = fareManager;
        this.tripWriter = tripWriter;
        this.tripMapper = tripMapper;
    }

    public static void main(String[] args) {
        LOG.info("Starting trip fare calculation...");
        SpringApplication.run(TripFareProcessor.class, args);
        LOG.info("Completed trip fare calculation...");
    }

    @Override
    public void run(String... args) throws TripChargeCalculatorException {
        String tapFilePath = args[0];
        initializeFareData();
        List<Tap> taps = readTapData(tapFilePath);
        List<Trip> trips = mapTapsToTrips(taps);
        writeTripData(trips);
    }

    private List<Trip> mapTapsToTrips(List<Tap> taps) {
        return tripMapper.mapTapsToTrips(taps);
    }

    private List<Tap> readTapData(String tapFilePath) throws TripChargeCalculatorException {
        return tapsReader.readTaps(tapFilePath);
    }

    private void writeTripData(List<Trip> trips) throws TripChargeCalculatorException {
        tripWriter.writeFile(properties.getOutput(), trips);
    }

    private void initializeFareData() throws TripChargeCalculatorException {
        LOG.info("Initializing fare data...");
        fareManager.initializeFareData();
    }
}
