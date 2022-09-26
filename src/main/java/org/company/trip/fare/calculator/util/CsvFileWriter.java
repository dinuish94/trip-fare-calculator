package org.company.trip.fare.calculator.util;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.company.trip.fare.calculator.exception.TripChargeCalculatorException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static java.lang.String.format;

public class CsvFileWriter<T> {

    private static final Logger logger = LogManager.getLogger(CsvFileWriter.class);

    public void writeFile(String fileName, List<T> data) throws TripChargeCalculatorException {
        logger.debug(format("Writing trip data to file %s", fileName));
        try (FileWriter writer = new FileWriter(fileName)) {
            StatefulBeanToCsv csvWriter = new StatefulBeanToCsvBuilder(writer)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withEscapechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                    .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                    .withOrderedResults(false)
                    .build();
            csvWriter.write(data);
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            throw new TripChargeCalculatorException(String.format("Failed to write to file: %s", fileName), e);
        }
    }
}
