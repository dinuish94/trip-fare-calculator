package org.company.trip.fare.calculator.model;

import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Fare {
    @CsvBindByPosition(position = 0)
    private String start;

    @CsvBindByPosition(position = 1)
    private String end;

    @CsvBindByPosition(position = 2)
    private double cost;
}
