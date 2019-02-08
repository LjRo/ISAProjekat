package isa.projekat.Projekat.controller.hotel;

import isa.projekat.Projekat.aspects.AdminEnabledCheck;
import isa.projekat.Projekat.model.hotel.*;
import isa.projekat.Projekat.model.user.User;
import isa.projekat.Projekat.security.TokenUtils;
import isa.projekat.Projekat.service.hotel.RoomService;
import isa.projekat.Projekat.service.user_auth.UserService;
import isa.projekat.Projekat.utils.PageRequestProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private PageRequestProvider pageRequestProvider;

    @Autowired
    private TokenUtils jwtTokenUtils;

    @Autowired
    private UserService userService;


    @PermitAll
    @GetMapping(value = "api/rooms/findById", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Room find(@RequestParam Long id) {
        return roomService.findById(id);
    }

    @PermitAll
    @GetMapping(value = "api/rooms/findAll", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Page<Room> findAll(@RequestParam String page) {
        return roomService.findAll(pageRequestProvider.provideRequest(page));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value = "api/rooms/{idHotel}/quick", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<ReservationHotel> listQuickReservations(@PathVariable Long idHotel){
        return roomService.listQuickReservations(idHotel);
    }


    @PermitAll
    @GetMapping(value = "api/rooms/findByIdAll", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Page<Room> findById(@RequestParam long id,@RequestParam String page) {
        return roomService.findByHotelId(id,pageRequestProvider.provideRequest(page));
    }

    @PermitAll
    @PostMapping(value = "api/rooms/findByIdAvailable", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Page<Room> findByIdAvailable(@RequestBody RoomSearchData roomSearchData) {
        return roomService.findAvailableByHotelId(roomSearchData,pageRequestProvider.provideRequest(roomSearchData.getPage()));
    }


    @PostMapping(value = "api/rooms/{id}/editRoom")
    @PreAuthorize("hasRole('ROLE_ADMIN_HOTEL')")
    @AdminEnabledCheck
    public ResponseEntity<Map<String,String>> editHotel(@RequestBody Room room,@PathVariable Long id, HttpServletRequest httpServletRequest) {
        User user = getUser(httpServletRequest);
        return responseTransaction(roomService.editRoom(room,user,id));
    }

    @PostMapping(value = "api/rooms/{id}/deleteRoom")
    @PreAuthorize("hasRole('ROLE_ADMIN_HOTEL')")
    @AdminEnabledCheck
    public ResponseEntity<Map<String,String>> deleteRoom(@RequestBody Room room,@PathVariable Long id, HttpServletRequest httpServletRequest) {
        User user = getUser(httpServletRequest);
        return responseTransaction(roomService.deleteRoom(room,user,id));
    }

    @PostMapping(value = "api/rooms/reserveRoom")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Map<String,String>> reserveRoom(@RequestBody ReservationHotelData reservationHotelData, HttpServletRequest httpServletRequest) {
        User user = getUser(httpServletRequest);
        return advanceResponse(roomService.reserveRoom(reservationHotelData,user));
    }

    @PostMapping(value = "api/rooms/quickReserve")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Map<String,String>> quickReserve(@RequestBody QuickReserveData quickReserveData, HttpServletRequest httpServletRequest) {
        User user = getUser(httpServletRequest);
        return advanceResponse(roomService.quickReserve(quickReserveData,user));
    }

    private ResponseEntity<Map<String,String>> advanceResponse(int resultOfTransaction ){
        Map<String, String> result = new HashMap<>();
       switch (resultOfTransaction )
       {
           case 0:
               result.put("result", "success");
               result.put("status","200");
                break;
           case 1:
               result.put("result", "error");
               result.put("body","Unauthorized access");
               result.put("status","401");
               break;
           case 2:
               result.put("result", "errorExists");
               result.put("body","Error already exists reservation for room for this airline reservation");
               result.put("status","508");
               break;
           case 3:
               result.put("result", "errorWithData");
               result.put("body","Error with data that was sent");
               result.put("status","509");
               break;
           case 4:
               result.put("result", "errorAlreadyReserved");
               result.put("body","Error already reserved room in this time period");
               result.put("status","510");
               break;
           default:
               result.put("result", "error");
               result.put("status","505");
               result.put("body","Unknown error");
       }
        return ResponseEntity.accepted().body(result);
    }

    @SuppressWarnings("Duplicates")
    private ResponseEntity<Map<String,String>> responseTransaction(Boolean resultOfTransaction ){
        Map<String, String> result = new HashMap<>();
        if(resultOfTransaction )
        {
            result.put("result", "success");
            return ResponseEntity.accepted().body(result);
        }
        else
        {
            result.put("result", "error");
            result.put("body","401, Unauthorized access");
            return ResponseEntity.accepted().body(result);
        }
    }

    private User getUser(HttpServletRequest httpServletRequest){
        String authToken = jwtTokenUtils.getToken(httpServletRequest);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);
        User user = userService.findByUsername(email);
        return user;
    }



}
