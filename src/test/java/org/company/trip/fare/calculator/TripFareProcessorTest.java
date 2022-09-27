package org.company.trip.fare.calculator;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.company.trip.fare.calculator.constant.ConfigProperties;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(args = {"src/test/resources/taps.csv"})
@TestPropertySource(locations = "classpath:test-config.properties")
public class TripFareProcessorTest {

    private static final String EXPECTED_OUTPUT = "src/test/resources/trips.csv";

    @Autowired
    private ConfigProperties properties;

    @Test
    public void contextLoads() {

    }

    @Test
    public void calculateFares() throws Exception {
        List<String> records = readOutput(properties.getOutput());
        List<String> expected = readOutput(EXPECTED_OUTPUT);
        Assert.assertEquals(expected, records);
    }

    private List<String> readOutput(String filePath) throws IOException, CsvValidationException {
        List<String> records = new ArrayList<>();
        boolean headerRead = false;
        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                if (headerRead) {
                    String commaSeparatedString = String.join(",", Arrays.asList(values));
                    records.add(commaSeparatedString);
                } else {
                    headerRead = true;
                }
            }
        }
        return records;
    }
}

