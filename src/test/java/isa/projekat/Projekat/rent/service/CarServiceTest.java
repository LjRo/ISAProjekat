package isa.projekat.Projekat.rent.service;

import isa.projekat.Projekat.model.airline.Order;
import isa.projekat.Projekat.model.rent_a_car.CarType;
import isa.projekat.Projekat.model.rent_a_car.Cars;
import isa.projekat.Projekat.model.rent_a_car.RentACar;
import isa.projekat.Projekat.model.rent_a_car.RentReservation;
import isa.projekat.Projekat.model.user.User;
import isa.projekat.Projekat.repository.*;
import isa.projekat.Projekat.service.rent_a_car.CarService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarServiceTest {
    public static final Long DB_ID = 1L;


    @InjectMocks
    private CarService carService;

    @Mock
    private CarRepository carRepository;

    @Mock
    private User userMock;

    @Mock
    private RentReservation rentReservationMock;

    @Mock
    private RentCarRepository rentCarRepository;

    @Mock
    private CarTypeRepository carTypeRepository;

    @Mock
    private RentReservationRepository rentReservationRepository;

    @Mock
    private RentACar rentACarMock;

    @Mock
    private Cars carsMock;

    @Mock
    private CarType carTypeMock;

    @Mock
    private Order orderMock;

    @Mock
    private OrderRepository orderRepository;

    @Test
    @Transactional
    public void testAddCar() {
        ArrayList<User> list = new ArrayList<>();
        list.add(userMock);
        when(carsMock.getType()).thenReturn(carTypeMock);
        when(carTypeMock.getId()).thenReturn(DB_ID);
        when(rentACarMock.getAdmins()).thenReturn(list);
        when(carTypeRepository.findById(DB_ID)).thenReturn(Optional.of(carTypeMock));
        Mockito.when(rentCarRepository.findById(DB_ID)).thenReturn(Optional.of(rentACarMock));
        Boolean isTrue = carService.addCars(carsMock,userMock,DB_ID);
        assertTrue(isTrue);
    }

    @Test
    @Transactional
    public void testFailAddCar() {
        when(userMock.getId()).thenReturn(3L);
        when(rentCarRepository.findById(DB_ID)).thenReturn(Optional.empty());
        Boolean isFalse = carService.addCars(new Cars(),userMock,DB_ID);
        assertFalse(isFalse);
    }

    @Test
    @Transactional
    public void reserveCar(){
        when(rentCarRepository.findById(DB_ID)).thenReturn(Optional.of(rentACarMock));
        when(carRepository.findById(DB_ID)).thenReturn(Optional.of(carsMock));
        when(orderRepository.findById(DB_ID)).thenReturn(Optional.of(orderMock));
        when(orderMock.getPlacedOrder()).thenReturn(userMock);
        CarType tip = new CarType();
        tip.setId(DB_ID);
        when(carsMock.getType()).thenReturn(tip);

        Date a = new Date();
        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.DATE, 1);
        Date b = cal.getTime();

        when(rentReservationMock.getStartDate()).thenReturn(a);
        when(rentReservationMock.getEndDate()).thenReturn(b);
        when(rentReservationMock.getNumberOfPeople()).thenReturn(3);

        Boolean succ = carService.reserveCar(DB_ID,DB_ID,userMock,rentReservationMock,DB_ID);
        assertEquals(succ,false);

    }
    @Test
    @Transactional
    public void testQuick(){

        when(rentReservationRepository.getOne(DB_ID)).thenReturn(rentReservationMock);
        when(orderRepository.findById(DB_ID)).thenReturn(Optional.of(orderMock));
        carService.quickReserve(DB_ID,DB_ID,userMock);

    }
    @Test
    @Transactional
    public void testFailRserveCar(){
        when(rentCarRepository.findById(DB_ID)).thenReturn(Optional.empty());
        when(carRepository.findById(DB_ID)).thenReturn(Optional.of(carsMock));
        Boolean returning = carService.reserveCar(DB_ID,DB_ID,userMock,rentReservationMock,DB_ID);
        assertEquals(returning,false);
    }



}
