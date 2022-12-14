## TRIP FARE CALCULATOR

This application calculates fares according to the below calculation.

```
Trips between Stop 1 and Stop 2 cost $3.25
Trips between Stop 2 and Stop 3 cost $5.50
Trips between Stop 1 and Stop 3 cost $7.30
```

Note that the above prices apply for travel in either direction (e.g. a passenger can tap on at stop 1 and tap off
at stop 2, or they can tap on at stop 2 and can tap off at stop 1. In either case, they would be charged $3.25).

It reads an input file (taps.csv) from a preconfigured /resources folder
and maps it to an output file (trips.csv)

#### Sample Input
```csv
ID, DateTimeUTC, TapType, StopId, CompanyId, BusID, PAN
1, 22-01-2018 13:00:00, ON, Stop1, Company1, Bus37, 5500005555555559
2, 22-01-2018 13:05:00, OFF, Stop2, Company1, Bus37, 5500005555555559
```

#### Sample Output
```csv
Started, Finished, DurationSecs, FromStopId, ToStopId, ChargeAmount, CompanyId, BusID, PAN,
Status
22-01-2018 13:00:00, 22-01-2018 13:05:00, 900, Stop1, Stop2, $3.25, Company1, B37,
5500005555555559, COMPLETED
```

### Prerequisites

- Install Java
- Install Maven

### How to run the application

- Run `mvn clean install` in the root folder
- Run `mvn spring-boot:run -Dspring-boot.run.arguments=src/main/resources/taps.csv` in the root folder to run the application
- Output csv can be found in the root folder as well once the application has run successfully

**NOTE:**

- If new fare data are to be added, add them to the /resources/fares.csv
- To input a different test file simply replace the file path  - `src/main/resources/taps.csv` in the command line arguments in the run command

### Tests

#### Unit tests

- Run `mvn test` inside root folder to run tests

#### Automated tests

- Run `TripFareProcessorTest`

### Assumptions

- There cannot be a Tap Type 'OFF' record without an 'ON' record
- csv data are ordered by timestamp
- In incomplete trips, finished time and last stop is null 
- Cannot have 2 'ON' taps at the same stop

### Improvements
- Improve to list the stop the fare is calculated against in incomplete trips.
- Improve unit tests to include unhappy paths