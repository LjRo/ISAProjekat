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
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RentServiceTest {
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
        when(carTypeRepository.findById(DB_ID));
        Mockito.when(rentCarRepository.findById(DB_ID)).thenReturn(Optional.of(rentACarMock));
        Boolean isTrue = carService.addCars(carsMock,userMock,DB_ID);
        assertTrue(isTrue);
    }


}
