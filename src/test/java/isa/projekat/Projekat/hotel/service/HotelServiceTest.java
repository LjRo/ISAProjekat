package isa.projekat.Projekat.hotel.service;

import isa.projekat.Projekat.model.hotel.FloorPlan;
import isa.projekat.Projekat.model.hotel.Hotel;
import isa.projekat.Projekat.model.hotel.HotelServices;
import isa.projekat.Projekat.model.hotel.RoomType;
import isa.projekat.Projekat.model.rent_a_car.Location;
import isa.projekat.Projekat.model.user.User;
import isa.projekat.Projekat.repository.*;
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
    public static final Long DB_ID_2 = 1L;
    public static final Long LOC_ID = 520L;


    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private RoomTypeRepository roomTypeRepository;

    @Mock
    private FloorRepository floorRepository;

    @Mock
    private HotelPricesRepository hotelPricesRepository;

    @Mock
    private HotelServicesRepository hotelServicesRepository;

    @Mock
    private Hotel hotelMock;

    @Mock
    private FloorPlan floorPlanMock;

    @Mock
    private RoomType roomTypeMock;

    @Mock
    private HotelServices hotelServicesMock;

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private LocationRepository locationRepositoryMock;

    @Mock
    private User userMock;

    @Mock
    private Location locationMock;

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
    public void testFindAvailable() {
        assertEquals(hotelService.findAvailableByHotelId("2019-01-01","2019-01-15", "Milano" ,"Al"), new ArrayList<>());
    }




}
