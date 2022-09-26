package org.company.trip.fare.calculator.core;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.company.trip.fare.calculator.model.Tap;
import org.company.trip.fare.calculator.util.ConfigReader;
import org.company.trip.fare.calculator.util.CsvFileReader;

import java.util.List;

import static java.lang.String.format;
import static org.company.trip.fare.calculator.constant.ConfigProperties.PROPERTY_KEY_TAPS_FILE;

public class TapsReader {

    private static final Logger logger = LogManager.getLogger(TapsReader.class);

    public static List<Tap> readTaps() {
        logger.debug(format("Reading tap data from file %s", PROPERTY_KEY_TAPS_FILE));
        CsvFileReader<Tap> reader = new CsvFileReader<>();
        return reader.read(ConfigReader.getConfigReader().getProperty(PROPERTY_KEY_TAPS_FILE), Tap.class);
    }
}
