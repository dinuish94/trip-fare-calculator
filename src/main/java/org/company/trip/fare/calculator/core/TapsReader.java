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
 * TapsReader reads a given CSV file preconfigured in the properties file
 * and maps it to a List of Tap objects
 */
@Component
public class TapsReader {

    private static final Logger LOG = LoggerFactory.getLogger(TripFareProcessor.class);

    private ConfigProperties configProperties;

    public TapsReader(ConfigProperties configProperties) {
        this.configProperties = configProperties;
    }

    public List<Tap> readTaps() {
        LOG.debug(format("Reading tap data from file %s", configProperties.getInput()));
        CsvFileReader<Tap> reader = new CsvFileReader<>();
        return reader.read(configProperties.getInput(), Tap.class);
    }
}
