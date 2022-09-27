package org.company.trip.fare.calculator.core;

import org.company.trip.fare.calculator.BaseTest;
import org.company.trip.fare.calculator.model.Fare;
import org.company.trip.fare.calculator.util.CsvFileReader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class FareManagerTest extends BaseTest {

    @MockBean
    private CsvFileReader<Fare> reader;

    private FareManager fareManager;

    private List<Fare> fareData = new ArrayList<>();

    @Before
    public void setup() {
        fareManager = new FareManager(reader);
    }

    @Test
    public void getFareBetweenStop1AndStop2ForCompletedTrip() {
        mockFareData();
        when(reader.read(any(), eq(Fare.class))).thenReturn(fareData);
        fareManager.initializeFareData();
        Double fare = fareManager.getFare(STOP_1, STOP_2);
        Assert.assertEquals(3.25, fare, 0);
    }

    @Test
    public void getFareBetweenStop3AndStop1ForCompletedTrip() {
        mockFareData();
        when(reader.read(any(), eq(Fare.class))).thenReturn(fareData);
        fareManager.initializeFareData();
        Double fare = fareManager.getFare(STOP_3, STOP_1);
        Assert.assertEquals(7.3, fare, 0);
    }

    @Test
    public void getFareForIncompleteTrip_shouldReturnMaxFare() {
        mockFareData();
        when(reader.read(any(), eq(Fare.class))).thenReturn(fareData);
        fareManager.initializeFareData();
        Double fare = fareManager.getMaximumFareForTrip(STOP_1);
        Assert.assertEquals(7.3, fare, 0);
    }

    @Test
    public void getFareForCancelledTrip_shouldReturnNoFare() {
        mockFareData();
        when(reader.read(any(), eq(Fare.class))).thenReturn(fareData);
        fareManager.initializeFareData();
        Double fare = fareManager.getFare(STOP_1, STOP_1);
        Assert.assertEquals(0, fare, 0);
    }

    @Test
    public void getFareWhenThereAreNoFareData_shouldReturnNullAndShouldNotFail() {
        mockFareData();
        when(reader.read(any(), eq(Fare.class))).thenReturn(new ArrayList<>());
        fareManager.initializeFareData();
        Double fare = fareManager.getFare(STOP_1, STOP_2);
        Assert.assertNull(fare);
    }

    private void mockFareData() {
        fareData.add(new Fare("Stop1", "Stop2", 3.25));
        fareData.add(new Fare("Stop2", "Stop3", 5.5));
        fareData.add(new Fare("Stop1", "Stop3", 7.3));
    }
}