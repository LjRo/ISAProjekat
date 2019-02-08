package isa.projekat.Projekat.rent.service;

import isa.projekat.Projekat.model.airline.Order;
import isa.projekat.Projekat.model.rent_a_car.*;
import isa.projekat.Projekat.model.user.User;
import isa.projekat.Projekat.repository.*;
import isa.projekat.Projekat.service.rent_a_car.CarService;
import isa.projekat.Projekat.service.rent_a_car.RentCarsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RentServiceTest {
    public static final Long DB_ID = 1L;


    @InjectMocks
    private RentCarsService rentCarsService;

    @Mock
    private CarRepository carRepository;

    @Mock
    private User userMock;

    @Mock
    private User userMock2;

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

    @Mock
    private LocationRepository locationRepository;

    @Test
    @Transactional
    public void testAddCar() {
        List a = new ArrayList();
        RentACar rentACar = new RentACar();
        a.add(rentACar);
        when(rentCarRepository.findAll()).thenReturn(a);
        List<RentACar> got = rentCarsService.findAll();
        assertEquals(got,a);
    }

    @Test
    @Transactional(readOnly = true)
    public void testFindRentCar() {
        when(rentCarRepository.findById(DB_ID)).thenReturn(Optional.of(rentACarMock));
        RentACar ret = rentCarsService.findById(DB_ID);
        assertEquals(ret,rentACarMock);
    }

    @Test
    @Transactional(readOnly = true)
    public void testFailFindRentCar() {
        when(rentCarRepository.findById(DB_ID)).thenReturn(Optional.empty());
        RentACar ret = rentCarsService.findById(DB_ID);
        assertEquals(ret,null);
    }


    @Test
    @Transactional
    public void addRentacar(){
        RentACar aCar = new RentACar();
        aCar.setDescription("De");
        aCar.setFastDiscount(5);
        aCar.setName("Na");

        Location location = new Location();
        location.setAddressName("aca");
        location.setCity("Meg");
        location.setLongitude(45.45);
        location.setLatitude(45.45);
        location.setCountry("Niger");

        aCar.setAddress(location);
        Boolean got = rentCarsService.addRentacar(aCar);
        assertEquals(got,true);
    }

    @Test
    @Transactional
    public void findAllByName(){
        String name = "Ale";
        String start = "2019-01-01";
        String end = "2019-01-10";
        List<RentACar> list = new ArrayList<>();
        list.add(new RentACar());
        list.add(new RentACar());
        when(rentCarRepository.findAllByName(name,start,end)).thenReturn(list);
        List<RentACar> rentACarList = rentCarsService.findAllByName(name,start,end);
        assertEquals(rentACarList.size(),list.size());
    }

    @Test
    @Transactional
    public void editRentACar(){
        when(rentACarMock.getId()).thenReturn(DB_ID);
        Location location = new Location();
        location.setAddressName("aca");
        location.setCity("Meg");
        location.setLongitude(45.45);
        location.setLatitude(45.45);
        location.setCountry("Niger");
        when(rentACarMock.getAddress()).thenReturn(location);
        when(rentACarMock.getFastDiscount()).thenReturn(5);
        when(rentCarRepository.findById(DB_ID)).thenReturn(Optional.of(rentACarMock));
        List<User> list = new ArrayList<>();
        list.add(userMock);
        when(rentACarMock.getAdmins()).thenReturn(list);
        Boolean got = rentCarsService.editRentACar(rentACarMock,userMock);
        assertEquals(got,true);
    }

    @Test
    @Transactional
    public void editFailNotGoodAdminRentACar(){
        when(rentACarMock.getId()).thenReturn(DB_ID);
        when(rentCarRepository.findById(DB_ID)).thenReturn(Optional.of(rentACarMock));
        List<User> list = new ArrayList<>();
        list.add(userMock2);
        when(rentACarMock.getAdmins()).thenReturn(list);
        Boolean got = rentCarsService.editRentACar(rentACarMock,userMock);
        assertEquals(got,false);
    }

    @Test
    @Transactional
    public void testFailEditCar(){
        when(rentACarMock.getId()).thenReturn(DB_ID);
        when(rentCarRepository.findById(DB_ID)).thenReturn(Optional.empty());
        Boolean got = rentCarsService.editRentACar(rentACarMock,userMock);
        assertEquals(got,false);
    }





}
