package org.company.trip.fare.calculator.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class FareIdentifier {
    private String start;
    private String stop;

    public static FareIdentifier createKey(String stop1, String stop2) {
        return new FareIdentifier(stop1, stop2);
    }
}
