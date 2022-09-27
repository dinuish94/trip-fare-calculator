package org.company.trip.fare.calculator.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.*;
import org.company.trip.fare.calculator.constant.TripStatus;

@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Trip {
    private String started;
    private String finished;
    private Long durationSecs;
    private String fromStopId;
    private String toStopId;
    private Double chargeAmount;
    private String companyId;
    private String busId;
    private String pan;
    private TripStatus status;
}
