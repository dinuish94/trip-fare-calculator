package org.company.trip.fare.calculator.core;

import org.company.trip.fare.calculator.BaseTest;
import org.company.trip.fare.calculator.constant.TapType;
import org.company.trip.fare.calculator.constant.TripStatus;
import org.company.trip.fare.calculator.model.Tap;
import org.company.trip.fare.calculator.model.Trip;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class TripMapperTest extends BaseTest {

    @MockBean
    private TripFareCalculator tripFareCalculator;

    private TripMapper tripMapper;

    @Before
    public void setup() {
        tripMapper = new TripMapper(tripFareCalculator);
    }

    @Test
    public void mapTapsToTrips_completeTripForOneCustomer() {
        List<Trip> expectedTrips = new ArrayList<>();
        List<Tap> mockedTaps = new ArrayList<>();
        mockedTaps.add(mockTap("1", LocalDateTime.of(2015, 10, 26, 9, 40), STOP_1, TapType.ON, PAN_1));
        mockedTaps.add(mockTap("2", LocalDateTime.of(2015, 10, 26, 9, 42), STOP_2, TapType.OFF, PAN_1));
        when(tripFareCalculator.calculateTripFare(STOP_1, STOP_2)).thenReturn(2.0);
        List<Trip> trips = tripMapper.mapTapsToTrips(mockedTaps);
        expectedTrips.add(createTrip(STOP_1, STOP_2, PAN_1, "2015-10-26T09:40", "2015-10-26T09:42", 2.0, 120L, TripStatus.COMPLETED));
        assertEquals(trips, expectedTrips);
    }

    @Test
    public void mapTapsToTrips_incompleteTripForOneCustomer() {
        List<Trip> expectedTrips = new ArrayList<>();
        List<Tap> mockedTaps = new ArrayList<>();
        mockedTaps.add(mockTap("1", LocalDateTime.of(2015, 10, 26, 9, 40), STOP_1, TapType.ON, PAN_1));
        when(tripFareCalculator.calculateTripFare(STOP_1)).thenReturn(5.0);
        List<Trip> trips = tripMapper.mapTapsToTrips(mockedTaps);
        expectedTrips.add(createTrip(STOP_1, null, PAN_1, "2015-10-26T09:40", null, 5.0, 0L, TripStatus.INCOMPLETE));
        assertEquals(trips, expectedTrips);
    }

    @Test
    public void mapTapsToTrips_cancelledTripForOneCustomer() {
        List<Trip> expectedTrips = new ArrayList<>();
        List<Tap> mockedTaps = new ArrayList<>();
        mockedTaps.add(mockTap("1", LocalDateTime.of(2015, 10, 26, 9, 40), STOP_1, TapType.ON, PAN_1));
        mockedTaps.add(mockTap("1", LocalDateTime.of(2015, 10, 26, 9, 40), STOP_1, TapType.OFF, PAN_1));
        when(tripFareCalculator.calculateTripFare(STOP_1)).thenReturn(0.0);
        List<Trip> trips = tripMapper.mapTapsToTrips(mockedTaps);
        expectedTrips.add(createTrip(STOP_1, STOP_1, PAN_1, "2015-10-26T09:40", "2015-10-26T09:40", 0.0, 0L, TripStatus.CANCELLED));
        assertEquals(trips, expectedTrips);
    }

    private Tap mockTap(String id, LocalDateTime startTime, String stop, TapType tapType, String pan) {
        return new Tap(id, startTime, tapType, stop, COMPANY_1, BUS_1, pan);
    }

    private Trip createTrip(String fromStopId, String toStopId, String pan, String startTime, String endTime, Double chargeAmount, Long duration, TripStatus status) {
        return Trip.builder()
                .fromStopId(fromStopId)
                .pan(pan)
                .busId(BUS_1)
                .finished(endTime)
                .started(startTime)
                .companyId(COMPANY_1)
                .chargeAmount(chargeAmount)
                .durationSecs(duration)
                .status(status)
                .toStopId(toStopId)
                .build();
    }
}