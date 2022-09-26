package org.company.trip.fare.calculator.model;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import lombok.Getter;
import lombok.ToString;
import org.company.trip.fare.calculator.constant.TapType;
import org.company.trip.fare.calculator.util.LocalDateConverter;

import java.time.LocalDateTime;

@Getter
@ToString
public class Tap {

    @CsvBindByPosition(position = 0)
    private String id;

    @CsvCustomBindByPosition(position = 1, converter = LocalDateConverter.class)
    private LocalDateTime dateTimeUTC;

    @CsvBindByPosition(position = 2)
    private TapType tapType;

    @CsvBindByPosition(position = 3)
    private String stopId;

    @CsvBindByPosition(position = 4)
    private String companyId;

    @CsvBindByPosition(position = 5)
    private String busId;

    @CsvBindByPosition(position = 6)
    private String pan;
}
