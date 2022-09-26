package org.company.trip.fare.calculator.exception;

public class TripChargeCalculatorException extends Exception {

    public TripChargeCalculatorException(String message) {
        super(message);
    }

    public TripChargeCalculatorException(String message, Throwable cause) {
        super(message, cause);
    }
}
