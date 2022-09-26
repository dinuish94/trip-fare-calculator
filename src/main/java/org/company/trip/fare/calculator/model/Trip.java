package org.company.trip.fare.calculator.model;

import lombok.Builder;
import lombok.ToString;
import org.company.trip.fare.calculator.constant.TripStatus;

@Builder
@ToString
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
