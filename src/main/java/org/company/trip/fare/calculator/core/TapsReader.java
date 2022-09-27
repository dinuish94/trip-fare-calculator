package org.company.trip.fare.calculator.core;

import org.company.trip.fare.calculator.TripFareProcessor;
import org.company.trip.fare.calculator.constant.ConfigProperties;
import org.company.trip.fare.calculator.model.Tap;
import org.company.trip.fare.calculator.util.CsvFileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.lang.String.format;

/**
 * TapsReader reads a given CSV file
 * and maps it to a List of Tap objects
 */
@Component
public class TapsReader {

    private static final Logger LOG = LoggerFactory.getLogger(TripFareProcessor.class);

    private ConfigProperties configProperties;

    private final CsvFileReader<Tap> csvFileReader;

    public TapsReader(ConfigProperties configProperties, CsvFileReader<Tap> csvFileReader) {
        this.configProperties = configProperties;
        this.csvFileReader = csvFileReader;
    }

    public List<Tap> readTaps(String tapFilePath) {
        LOG.debug(format("Reading tap data from file %s", tapFilePath));
        return csvFileReader.read(tapFilePath, Tap.class);
    }
}
