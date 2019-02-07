package isa.projekat.Projekat.airline.service;

import isa.projekat.Projekat.model.airline.*;
import isa.projekat.Projekat.model.user.User;
import isa.projekat.Projekat.repository.*;
import isa.projekat.Projekat.service.airline.FlightService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FlightServiceTest {

    public static final Long DB_ID = 1L;
    public static final Long DB_ID_2 = 2L;

    @Mock
    private AirlineRepository airlineRepositoryMock;

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private LocationRepository locationRepositoryMock;

    @Mock
    private FlightRepository flightRepositoryMock;

    @Mock
    private SeatRepository seatRepositoryMock;

    @Mock
    private OrderRepository orderRepositoryMock;

    @Mock
    private ReservationRepository reservationRepositoryMock;

    @Mock
    private Airline airlineMock;

    @Mock
    private Flight flightMock;

    @Mock
    private Order orderMock;

    @Mock
    private Order orderMock2;

    @Mock
    private Seat seatMock;

    @Mock
    private User userMock;

    @InjectMocks
    private FlightService flightService;

    @Test
    public void testFinishOrderPass() {
        when(orderRepositoryMock.findById(DB_ID)).thenReturn(Optional.of(orderMock));
        when(userRepositoryMock.findByUsername("pera@peric.com")).thenReturn(userMock);
        when(userMock.getOrders()).thenReturn(Arrays.asList(orderMock));
        when(orderMock.getId()).thenReturn(DB_ID);
        assertTrue(flightService.finishOrder(DB_ID,"pera@peric.com"));

    }

    @Test
    public void testFinishOrderFail() {
        when(orderRepositoryMock.findById(DB_ID)).thenReturn(Optional.of(orderMock));
        when(userRepositoryMock.findByUsername("pera@peric.com")).thenReturn(userMock);
        when(userMock.getOrders()).thenReturn(Arrays.asList(orderMock2));
        when(orderMock2.getId()).thenReturn(DB_ID_2);
        when(orderMock.getId()).thenReturn(DB_ID);
        assertFalse(flightService.finishOrder(DB_ID,"pera@peric.com"));

    }

    @Test
    public void bookFlightFail() {
        BookingData bd = new BookingData();
        bd.setAirlineReservations(new ArrayList<>());
        ReservationData rd = new ReservationData();
        rd.setUserId(DB_ID);
        rd.setTotalCost(new BigDecimal("120.00"));
        rd.setPointsUsed(0.0);
        rd.setFirstName("Pera");
        rd.setFlight(DB_ID);
        rd.setLastName("Peric");
        rd.setPassport("SR2019");
        rd.setSeatId(DB_ID);
        bd.getAirlineReservations().add(rd);

        when(userRepositoryMock.findByUsername("pera@peric.com")).thenReturn(userMock);
        when(userMock.getId()).thenReturn(DB_ID);
        when(seatRepositoryMock.findById(DB_ID)).thenReturn(Optional.of(seatMock));
        when(userRepositoryMock.findById(DB_ID)).thenReturn(Optional.of(userMock));
        when(seatMock.isTaken()).thenReturn(true);
        when(flightRepositoryMock.findById(DB_ID)).thenReturn(Optional.of(flightMock));
        when(userMock.getPoints()).thenReturn(0.0);
        when(seatMock.getPrice()).thenReturn(new BigDecimal("120.00"));
        when(userMock.getFirstName()).thenReturn("Pera");
        when(userMock.getLastName()).thenReturn("Peric");
        when(flightMock.getDistance()).thenReturn(1200.0);

        assertFalse(flightService.bookFlight(bd,"pera@peric.com"));

    }

    @Test
    public void bookFlightPass() {
        BookingData bd = new BookingData();
        bd.setAirlineReservations(new ArrayList<>());
        ReservationData rd = new ReservationData();
        rd.setUserId(DB_ID);
        rd.setTotalCost(new BigDecimal("120.00"));
        rd.setPointsUsed(0.0);
        rd.setFirstName("Pera");
        rd.setFlight(DB_ID);
        rd.setLastName("Peric");
        rd.setPassport("SR2019");
        rd.setSeatId(DB_ID);
        bd.getAirlineReservations().add(rd);

        when(userRepositoryMock.findByUsername("pera@peric.com")).thenReturn(userMock);
        when(userMock.getId()).thenReturn(DB_ID);
        when(seatRepositoryMock.findById(DB_ID)).thenReturn(Optional.of(seatMock));
        when(userRepositoryMock.findById(DB_ID)).thenReturn(Optional.of(userMock));
        when(seatMock.isTaken()).thenReturn(false);
        when(flightRepositoryMock.findById(DB_ID)).thenReturn(Optional.of(flightMock));
        when(userMock.getPoints()).thenReturn(0.0);
        when(seatMock.getPrice()).thenReturn(new BigDecimal("120.00"));
        when(userMock.getFirstName()).thenReturn("Pera");
        when(userMock.getLastName()).thenReturn("Peric");
        when(flightMock.getDistance()).thenReturn(1200.0);

        assertTrue(flightService.bookFlight(bd,"pera@peric.com"));

    }

    @Test
    public void bookFlightNull() {
        assertFalse(flightService.bookFlight(null,"pera@peric.com"));
    }

    @Test
    public void quickBookFlightPass() {
        QuickTicketData qTD = new QuickTicketData();
        qTD.setSeatId(DB_ID);
        qTD.setPassport("PASSPORT");

        when(userRepositoryMock.findByUsername("pera@peric.com")).thenReturn(userMock);
        when(userMock.getId()).thenReturn(DB_ID);
        when(seatRepositoryMock.findById(DB_ID)).thenReturn(Optional.of(seatMock));
        when(userRepositoryMock.findById(DB_ID)).thenReturn(Optional.of(userMock));
        when(seatMock.isTaken()).thenReturn(false);
        when(seatMock.getPrice()).thenReturn(new BigDecimal("120.00"));
        when(userMock.getFirstName()).thenReturn("Pera");
        when(userMock.getLastName()).thenReturn("Peric");
        when(flightMock.getDistance()).thenReturn(1200.0);
        when(seatMock.isQuick()).thenReturn(true);

        assertTrue(flightService.quickBookFlight(qTD,"pera@peric.com"));
    }

    @Test
    public void quickBookFlightFail() {
        QuickTicketData qTD = new QuickTicketData();
        qTD.setSeatId(DB_ID);
        qTD.setPassport("PASSPORT");

        when(userRepositoryMock.findByUsername("pera@peric.com")).thenReturn(userMock);
        when(userMock.getId()).thenReturn(DB_ID);
        when(seatRepositoryMock.findById(DB_ID)).thenReturn(Optional.of(seatMock));
        when(userRepositoryMock.findById(DB_ID)).thenReturn(Optional.of(userMock));
        when(seatMock.isTaken()).thenReturn(true);
        when(seatMock.getPrice()).thenReturn(new BigDecimal("120.00"));
        when(userMock.getFirstName()).thenReturn("Pera");
        when(userMock.getLastName()).thenReturn("Peric");
        when(flightMock.getDistance()).thenReturn(1200.0);
        when(seatMock.isQuick()).thenReturn(true);

        assertFalse(flightService.quickBookFlight(qTD,"pera@peric.com"));
    }


    @Test
    public void quickBookFlightNotQuick() {
        QuickTicketData qTD = new QuickTicketData();
        qTD.setSeatId(DB_ID);
        qTD.setPassport("PASSPORT");

        when(userRepositoryMock.findByUsername("pera@peric.com")).thenReturn(userMock);
        when(userMock.getId()).thenReturn(DB_ID);
        when(seatRepositoryMock.findById(DB_ID)).thenReturn(Optional.of(seatMock));
        when(userRepositoryMock.findById(DB_ID)).thenReturn(Optional.of(userMock));
        when(seatMock.isTaken()).thenReturn(false);
        when(seatMock.getPrice()).thenReturn(new BigDecimal("120.00"));
        when(userMock.getFirstName()).thenReturn("Pera");
        when(userMock.getLastName()).thenReturn("Peric");
        when(flightMock.getDistance()).thenReturn(1200.0);
        when(seatMock.isQuick()).thenReturn(false);

        assertFalse(flightService.quickBookFlight(qTD,"pera@peric.com"));
    }


}
