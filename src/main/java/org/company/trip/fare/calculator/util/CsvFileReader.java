package org.company.trip.fare.calculator.util;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Component;

import java.io.InputStreamReader;
import java.util.List;

/**
 * Reads CSV Files
 * @param <T>
 */
@Component
public class CsvFileReader<T> {

    public List<T> read(String fileName, Class<? extends T> type) {
        return new CsvToBeanBuilder(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(fileName)))
                .withType(type)
                .withSkipLines(1)
                .build()
                .parse();
    }
}
