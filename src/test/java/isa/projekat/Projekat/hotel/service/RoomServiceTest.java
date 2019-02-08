package isa.projekat.Projekat.hotel.service;

import isa.projekat.Projekat.model.airline.Order;
import isa.projekat.Projekat.model.hotel.Hotel;
import isa.projekat.Projekat.model.hotel.ReservationHotel;
import isa.projekat.Projekat.model.hotel.ReservationHotelData;
import isa.projekat.Projekat.model.hotel.Room;
import isa.projekat.Projekat.model.user.User;
import isa.projekat.Projekat.repository.HotelReservationRepository;
import isa.projekat.Projekat.repository.OrderRepository;
import isa.projekat.Projekat.repository.RoomRepository;
import isa.projekat.Projekat.service.hotel.RoomService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoomServiceTest {
    public static final Long DB_ID = 1L;


    @InjectMocks
    private RoomService roomService;

    @Mock
    private Room roomMock;

    @Mock
    private User userMock;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private HotelReservationRepository hotelReservationRepository;

    @Mock
    private Order orderMock;

    @Mock
    private Hotel hotelMock;

    @Test
    @Transactional
    public void testReserve(){

        ReservationHotelData reservationHotelData = new ReservationHotelData(DB_ID,DB_ID,DB_ID,"","2019-04-10","2019-04-15");
        when(roomRepository.checkIfAvailableStill(DB_ID,"2019-04-10","2019-04-15")).thenReturn(roomMock);
        when(orderRepository.findById(DB_ID)).thenReturn(Optional.of(orderMock));
        when(roomRepository.findById(DB_ID)).thenReturn(Optional.of(roomMock));
        when(roomMock.getHotel()).thenReturn(new Hotel());
        int response  = roomService.reserveRoom(reservationHotelData,userMock);
        assertEquals(response,0);
    }

    @Test
    @Transactional
    public void testFailReserve() {

        ReservationHotelData reservationHotelData = new ReservationHotelData(DB_ID, DB_ID, DB_ID, "", "2019-04-10", "2019-04-15");
        when(orderRepository.findById(DB_ID)).thenReturn(Optional.of(orderMock));
        int response = roomService.reserveRoom(reservationHotelData, userMock);
        assertEquals(response, 3);
    }

    @Test
    @Transactional
    public void testFailReserve2(){
        ReservationHotelData reservationHotelData = new ReservationHotelData(DB_ID,DB_ID,DB_ID,"","2019-04-10","2019-04-15");
        when(hotelReservationRepository.findByUserOrder_Id(DB_ID)).thenReturn(new ReservationHotel());
        int response  = roomService.reserveRoom(reservationHotelData,userMock);
        assertEquals(response,2);
    }


    @Test
    @Transactional
    public void testNotUsed(){
        int number = roomService.getAvailableUnavailabeRooms(DB_ID,false,"2019-01-05","2019-01-15");
        assertEquals(number,0);
    }

    @Test
    @Transactional
    public void testUsed(){
        int number = roomService.getAvailableUnavailabeRooms(DB_ID,true,"2019-01-16","2019-01-18");
        assertEquals(number,0);
    }



}
