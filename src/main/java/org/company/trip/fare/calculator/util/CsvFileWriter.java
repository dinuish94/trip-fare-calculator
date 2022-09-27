package org.company.trip.fare.calculator.util;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.company.trip.fare.calculator.TripFareProcessor;
import org.company.trip.fare.calculator.exception.TripChargeCalculatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static java.lang.String.format;

/**
 * Writes to CSV file
 * @param <T>
 */
@Component
public class CsvFileWriter<T> {

    private static final Logger LOG = LoggerFactory.getLogger(TripFareProcessor.class);

    public void writeFile(String fileName, List<T> data) throws TripChargeCalculatorException {
        LOG.debug(format("Writing trip data to file %s", fileName));
        try (FileWriter writer = new FileWriter(fileName)) {
            StatefulBeanToCsv csvWriter = new StatefulBeanToCsvBuilder(writer)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withEscapechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                    .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                    .withOrderedResults(true)
                    .build();
            csvWriter.write(data);
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            throw new TripChargeCalculatorException(String.format("Failed to write to file: %s", fileName), e);
        }
    }
}
