package org.company.trip.fare.calculator.model;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class Fare {
    @CsvBindByPosition(position = 0)
    private String start;

    @CsvBindByPosition(position = 1)
    private String end;

    @CsvBindByPosition(position = 2)
    private double cost;
}
