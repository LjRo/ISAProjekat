package isa.projekat.Projekat.hotel.service;

import isa.projekat.Projekat.model.hotel.FloorPlan;
import isa.projekat.Projekat.model.hotel.Hotel;
import isa.projekat.Projekat.model.user.User;
import isa.projekat.Projekat.repository.FloorRepository;
import isa.projekat.Projekat.repository.HotelRepository;
import isa.projekat.Projekat.service.hotel.FloorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FloorServiceTest {
    public static final Long DB_ID = 1L;


    @Mock
    private FloorPlan floorPlanMock;
    @Mock
    private Hotel hotelMock;
    @Mock
    private User userMock;


    @Mock
    private HotelRepository hotelRepository;
    @Mock
    private FloorRepository floorRepository;


    @InjectMocks
    private FloorService floorService;

    @Test
    @Transactional
    public void testEditFloor() {
        when(floorPlanMock.getId()).thenReturn(DB_ID);
        when(floorPlanMock.getConfiguration()).thenReturn("<div>'</div>");
        when(floorPlanMock.getFloorNumber()).thenReturn(1);
        when(userMock.getType()).thenReturn(1);
        when(hotelRepository.findById(DB_ID)).thenReturn(Optional.of(hotelMock));
        when(floorRepository.findById(DB_ID)).thenReturn(Optional.of(floorPlanMock));
        when(userMock.getAdministratedHotel()).thenReturn(hotelMock);
        List<User>listing = new ArrayList<>();
        listing.add(userMock);
        when(hotelMock.getAdmins()).thenReturn((listing));
        assertTrue(floorService.editFloorPlan(floorPlanMock,DB_ID,userMock));
    }


}
