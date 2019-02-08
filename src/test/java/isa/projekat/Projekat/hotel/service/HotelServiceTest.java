package isa.projekat.Projekat.hotel.service;

import isa.projekat.Projekat.model.hotel.*;
import isa.projekat.Projekat.model.rent_a_car.Location;
import isa.projekat.Projekat.model.user.User;
import isa.projekat.Projekat.repository.HotelRepository;
import isa.projekat.Projekat.repository.HotelServicesRepository;
import isa.projekat.Projekat.repository.RoomTypeRepository;
import isa.projekat.Projekat.service.hotel.HotelService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HotelServiceTest {
    public static final Long DB_ID = 1L;
    public static final Long DB_ID_2 = 2L;
    public static final Long LOC_ID = 520L;

    @Mock
    private Hotel hotelMock;

    @Mock
    private FloorPlan floorPlanMock;

    @Mock
    private RoomType roomTypeMock;

    @Mock
    private HotelServices hotelServicesMock;


    @Mock
    private Room roomMock;

    @Mock
    private Location locationMock;

    @Mock
    private User userMock;

    @Mock
    private User user2Mock;

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private RoomTypeRepository roomTypeRepository;

    @Mock
    private HotelServicesRepository hotelServicesRepository;

    private LocalDate dateMock = LocalDate.now();

    @InjectMocks
    private HotelService hotelService;

    @Test
    public void testFindAll() {

        when(hotelRepository.findAll()).thenReturn(Arrays.asList(new Hotel()));
        assertThat(hotelService.findAll()).hasSize(1);
    }

    @Test
    public void testFindOne() {
        when(hotelMock.getId()).thenReturn(DB_ID);
        when(hotelRepository.findById(DB_ID)).thenReturn(Optional.of(hotelMock));
        Hotel hotel = hotelService.findHotelById(DB_ID);
        assertEquals(hotelRepository.findById(DB_ID).get().getId(), hotel.getId());

    }

    @Test
    public void testFailFineOne() {
        Optional<Hotel>  optionalHotel = Optional.ofNullable(null);
        when(hotelRepository.findById(DB_ID_2)).thenReturn(optionalHotel);
        Hotel hotel = hotelService.findHotelById(DB_ID_2);
        assertEquals(hotel,null);
    }

    @Test
    public void testFailFindHotelServicesById() {
        Optional<HotelServices>  optionalHotelServices = Optional.ofNullable(null);
        when(hotelServicesRepository.findById(DB_ID_2)).thenReturn(optionalHotelServices);
        HotelServices checkHotelServices = hotelService.findHotelServiceById(DB_ID_2);
        assertEquals(checkHotelServices,null);
    }

    @Test
    public void testFindHotelServiceById() {
        when(hotelServicesRepository.findById(DB_ID)).thenReturn(Optional.of(hotelServicesMock));
        HotelServices checkHotelServices = hotelService.findHotelServiceById(DB_ID);
        assertEquals(checkHotelServices,hotelServicesMock);
    }



    @Test
    @Transactional
    public void testAddHotel() {
        when(userMock.getType()).thenReturn(1);
        when(hotelMock.getAddress()).thenReturn(locationMock);
        when(hotelMock.getFastDiscount()).thenReturn(10);
        when(hotelMock.getName()).thenReturn("Name");
        when(hotelMock.getDescription()).thenReturn("Random Hotel Desc");
        assertTrue(hotelService.addHotel(hotelMock,userMock));
    }

    @Test
    @Transactional
    public void testFailPillages() {
        when(userMock.getType()).thenReturn(0);
        when(hotelMock.getAddress()).thenReturn(locationMock);
        assertFalse(hotelService.addHotel(hotelMock,userMock));
    }

    @Test
    @Transactional
    public void testFloorPlan() {
        when(floorPlanMock.getConfiguration()).thenReturn("<div>'</div>");
        when(floorPlanMock.getFloorNumber()).thenReturn(1);
        when(hotelRepository.findById(DB_ID)).thenReturn(Optional.of(hotelMock));
        when(userMock.getAdministratedHotel()).thenReturn(hotelMock);
        List<User>listing = new ArrayList<>();
        listing.add(userMock);
        when(hotelMock.getAdmins()).thenReturn((listing));
        assertTrue(hotelService.addFloorPlan(floorPlanMock,DB_ID,userMock));
    }

    @Test
    @Transactional
    public void testFailAddFloorPlan() {
        when(floorPlanMock.getConfiguration()).thenReturn("<div>'</div>");
        when(floorPlanMock.getFloorNumber()).thenReturn(1);
        when(hotelRepository.findById(DB_ID)).thenReturn(Optional.of(hotelMock));
        when(userMock.getAdministratedHotel()).thenReturn(null);
        assertFalse(hotelService.addFloorPlan(floorPlanMock,DB_ID,userMock));
    }

    @Test
    @Transactional
    public void testEditHotel() {
        when(userMock.getType()).thenReturn(1);
        when(userMock.getAdministratedHotel()).thenReturn(hotelMock);
        List<User>listing = new ArrayList<>();
        listing.add(userMock);
        when(hotelMock.getAdmins()).thenReturn((listing));

        when(hotelMock.getAddress()).thenReturn(locationMock);
        when(hotelMock.getFastDiscount()).thenReturn(10);
        when(hotelMock.getName()).thenReturn("NewName");
        when(hotelMock.getDescription()).thenReturn("New Random Hotel Desc");
        assertFalse(hotelService.editHotel(hotelMock,userMock));
    }


    @Test
    @Transactional
    public void testAddRoomType() {
        when(userMock.getType()).thenReturn(1);
        when(userMock.getAdministratedHotel()).thenReturn(hotelMock);
        List<User>listing = new ArrayList<>();
        listing.add(userMock);
        when(hotelMock.getAdmins()).thenReturn((listing));
        assertFalse(hotelService.addRoomType(roomTypeMock,DB_ID,userMock));
    }


    @Test
    @Transactional
    public void testFailAddRoom1() {
        RoomData data = new RoomData("Name",roomTypeMock,1,1,1,1,1);
        when(userMock.getType()).thenReturn(0);
        List<User>listing = new ArrayList<>();
        listing.add(userMock);
        when(hotelMock.getAdmins()).thenReturn((listing));
        when(userMock.getAdministratedHotel()).thenReturn(hotelMock);
        assertFalse(hotelService.addRoom(data,DB_ID,userMock));
    }

    @Test
    @Transactional
    public void testFailAddRoom2() {
        RoomData data = new RoomData("Name",roomTypeMock,1,1,1,1,1);
        when(userMock.getType()).thenReturn(3);
        when(userMock.getAdministratedHotel()).thenReturn(null);
        when(hotelMock.getAdmins()).thenReturn(new ArrayList<>());
        assertFalse(hotelService.addRoom(data,DB_ID,user2Mock));
    }

    @Test
    @Transactional
    public void testAddRoom() {
        when(roomTypeMock.getId()).thenReturn(DB_ID);
        when(roomTypeRepository.findById(DB_ID)).thenReturn(Optional.of(roomTypeMock));
        RoomData data = new RoomData("Name",roomTypeMock,1,1,1,1,1);
        when(userMock.getId()).thenReturn(DB_ID);
        List<User>listing = new ArrayList<>();
        listing.add(userMock);
        when(hotelMock.getAdmins()).thenReturn((listing));
        when(userMock.getAdministratedHotel()).thenReturn(hotelMock);
        when(hotelRepository.findById(DB_ID)).thenReturn(Optional.of(hotelMock));
        Boolean shouldBeTrue = hotelService.addRoom(data,DB_ID,userMock);
        assertTrue(shouldBeTrue);
    }


    @Test
    @Transactional
    public void testFindAvailable() {
        assertEquals(hotelService.findAvailableByHotelId("2019-01-01","2019-01-15", "Milano" ,"Al"), new ArrayList<>());
    }




}
