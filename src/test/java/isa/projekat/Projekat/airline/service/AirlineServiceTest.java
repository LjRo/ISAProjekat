package isa.projekat.Projekat.airline.service;

import isa.projekat.Projekat.model.airline.Airline;
import isa.projekat.Projekat.model.airline.Flight;
import isa.projekat.Projekat.model.airline.FlightData;
import isa.projekat.Projekat.model.rent_a_car.Location;
import isa.projekat.Projekat.model.user.User;
import isa.projekat.Projekat.repository.AirlineRepository;
import isa.projekat.Projekat.repository.FlightRepository;
import isa.projekat.Projekat.repository.LocationRepository;
import isa.projekat.Projekat.repository.UserRepository;
import isa.projekat.Projekat.service.airline.AirlineService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AirlineServiceTest {
    public static final Long DB_ID = 1L;
    public static final Long DB_ID_2 = 1L;
    public static final Long LOC_ID = 520L;


    @Mock
    private AirlineRepository airlineRepositoryMock;

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private LocationRepository locationRepositoryMock;

    @Mock
    private FlightRepository flightRepositoryMock;

    @Mock
    private Airline airlineMock;

    @Mock
    private Airline airlineMock2;

    @Mock
    private Flight flightMock;

    @Mock
    private User userMock;

    @Mock
    private Location locationMock;

    private LocalDate dateMock = LocalDate.now();

    @InjectMocks
    private AirlineService airlineService;

    @Test
    public void testFindAll() {
        when(airlineRepositoryMock.findAll()).thenReturn(Arrays.asList(new Airline()));
        List<Airline> airlines = airlineService.findAll();
        assertThat(airlines).hasSize(1);

        verify(airlineRepositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(airlineRepositoryMock);

    }

    @Test
    public void testFindOne() {

        when(airlineRepositoryMock.findById(DB_ID)).thenReturn(Optional.of(airlineMock));
        Airline dbAirline = airlineService.findById(new Long(1));
        assertEquals(airlineRepositoryMock.findById(DB_ID).get(), dbAirline);


        verify(airlineRepositoryMock, times(2)).findById(DB_ID);
        verifyNoMoreInteractions(airlineRepositoryMock);
    }

    @Test
    public void testFindAllActiveFlights() {
        Date dtMock = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dtMock);
        c.add(Calendar.DATE, -10);

        when(flightMock.getStartTime()).thenReturn(dtMock);

        when(airlineRepositoryMock.findById(DB_ID)).thenReturn(Optional.of(airlineMock));
        when(airlineRepositoryMock.findById(DB_ID_2)).thenReturn(Optional.of(airlineMock2));
        when(airlineMock.getFlights()).thenReturn(Arrays.asList((flightMock)));



        assertEquals(airlineService.findAllActiveFlights(DB_ID).size(),0);
        assertEquals(airlineService.findAllActiveFlights(DB_ID_2).size(),0);
    }

    @Test
    public void deleteLocationFalse() {
        when(userRepositoryMock.findByUsername("admin@air.com")).thenReturn(userMock);
        when(userMock.getAdministratedAirline()).thenReturn(airlineMock);
        when(locationRepositoryMock.findById(LOC_ID)).thenReturn(Optional.of(locationMock));

        assertFalse(airlineService.deleteLocation(LOC_ID,"admin@air.com"));

    }

    @Test
    public void deleteLocationTrue() {
        when(userRepositoryMock.findByUsername("admin@air.com")).thenReturn(userMock);
        when(userMock.getAdministratedAirline()).thenReturn(airlineMock);
        when(locationRepositoryMock.findById(LOC_ID)).thenReturn(Optional.of(locationMock));
        when(airlineMock.getDestinations()).thenReturn(Arrays.asList(locationMock));
        when(locationMock.getActive()).thenReturn(true);

        assertTrue(airlineService.deleteLocation(LOC_ID,"admin@air.com"));

    }


    @Test
    @Transactional
    public void testAddFlightPass() {
        FlightData mockData = new FlightData(LOC_ID,LOC_ID,new Date(),new Date(),10.0,20.0,new BigDecimal("120"),1,2,1,0,null);
        when(userRepositoryMock.findByUsername("airline@gmail.com")).thenReturn(userMock);
        when(userMock.getAdministratedAirline()).thenReturn(airlineMock);
        when(airlineMock.getAdmins()).thenReturn(Arrays.asList(userMock));
        when(airlineRepositoryMock.findById(DB_ID)).thenReturn(Optional.of(airlineMock));
        when(airlineMock.getId()).thenReturn(DB_ID);
        when(airlineMock.getDestinations()).thenReturn(Arrays.asList(locationMock));
        when(locationMock.getId()).thenReturn(LOC_ID);

        assertTrue(airlineService.addFlight(mockData,"airline@gmail.com"));

    }

    @Test
    @Transactional
    public void testAddFlightFail() {
        FlightData mockData = new FlightData(LOC_ID,LOC_ID,new Date(),new Date(),10.0,20.0,new BigDecimal("120"),1,2,1,0,null);
        when(userRepositoryMock.findByUsername("airline@gmail.com")).thenReturn(userMock);
        when(userMock.getAdministratedAirline()).thenReturn(null);
        when(airlineMock.getAdmins()).thenReturn(Arrays.asList(userMock));
        when(airlineRepositoryMock.findById(DB_ID)).thenReturn(Optional.of(airlineMock));
        when(airlineMock.getId()).thenReturn(DB_ID);
        when(airlineMock.getDestinations()).thenReturn(Arrays.asList(locationMock));
        when(locationMock.getId()).thenReturn(LOC_ID);

        assertFalse(airlineService.addFlight(mockData,"airline@gmail.com"));

    }

    @Test
    public void calculatedIntervalTest() {
        Object dummy[] = new Object[2];

        dummy[0] = LocalDate.now();
        dummy[1] = new BigDecimal("25.00");
        ArrayList<Object[]> dummyArray = new ArrayList<Object[]>();
        dummyArray.add(dummy);

        when(flightRepositoryMock.getProfitFromRange(DB_ID,dateMock,dateMock)).thenReturn(dummyArray);
    }

}
