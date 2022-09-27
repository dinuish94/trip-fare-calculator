package org.company.trip.fare.calculator.util;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

/**
 * Reads CSV Files
 *
 * @param <T>
 */
@Component
public class CsvFileReader<T> {

    public List<T> read(String filePath, Class<? extends T> type) {
        try {
            return new CsvToBeanBuilder(new BufferedReader(new FileReader(filePath)))
                    .withType(type)
                    .withSkipLines(1)
                    .build()
                    .parse();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
