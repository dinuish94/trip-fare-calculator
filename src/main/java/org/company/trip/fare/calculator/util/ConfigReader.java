package org.company.trip.fare.calculator.util;

import org.company.trip.fare.calculator.exception.TripChargeCalculatorException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class ConfigReader {

    // TODO: Improve this to be used in a generic manner
    private static final String CONFIG_FILE_NAME = "app.config";
    private static final Map<String, String> properties = new HashMap<>();
    private static ConfigReader reader;

    private ConfigReader() {
    }

    public static ConfigReader getConfigReader() {
        try {
            if (reader == null) {
                reader = new ConfigReader();
                loadProperties();
            }
        } catch (TripChargeCalculatorException e) {
            throw new RuntimeException(e);
        }
        return reader;
    }

    private static void loadProperties() throws TripChargeCalculatorException {
        Properties appProperties = new Properties();
        try {
            appProperties.load(ConfigReader.class.getClassLoader().getResourceAsStream(CONFIG_FILE_NAME));
            properties.putAll(appProperties.entrySet()
                    .stream()
                    .collect(Collectors.toMap(e -> e.getKey().toString(), e -> e.getValue().toString())));
        } catch (IOException e) {
            throw new TripChargeCalculatorException("Failed to load properties from " + CONFIG_FILE_NAME, e);
        }
    }

    public String getProperty(String propertyName) {
        return properties.get(propertyName);
    }
}
