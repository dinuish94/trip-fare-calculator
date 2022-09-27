package org.company.trip.fare.calculator.core;

import org.company.trip.fare.calculator.BaseTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class TripFareCalculatorTest extends BaseTest {

    @MockBean
    private FareManager fareManager;

    private TripFareCalculator tripFareCalculator;

    @Before
    public void setup() {
        tripFareCalculator = new TripFareCalculator(fareManager);
    }

    @Test
    public void calculateTripFare() {
        when(fareManager.getFare(STOP_1, STOP_2)).thenReturn(3.25);
        tripFareCalculator.calculateTripFare(STOP_1, STOP_2);
        verify(fareManager).getFare(STOP_1, STOP_2);
    }

    @Test
    public void calculateTripFareWhenThereIsNoStop() {
        when(fareManager.getMaximumFareForTrip(STOP_1)).thenReturn(7.5);
        tripFareCalculator.calculateTripFare(STOP_1, null);
        verify(fareManager).getMaximumFareForTrip(STOP_1);
    }
}