package isa.projekat.Projekat.controller.hotel;


import isa.projekat.Projekat.model.hotel.FloorPlan;
import isa.projekat.Projekat.model.hotel.Hotel;
import isa.projekat.Projekat.model.hotel.RoomData;
import isa.projekat.Projekat.model.hotel.RoomType;
import isa.projekat.Projekat.model.user.User;
import isa.projekat.Projekat.security.TokenUtils;
import isa.projekat.Projekat.service.hotel.HotelService;
import isa.projekat.Projekat.service.user_auth.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


@RestController
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private TokenUtils jwtTokenUtils;

    @Autowired
    private UserService userService;

    @PermitAll
    @RequestMapping(value = "api/hotel/findAll", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Hotel> findAll() {
        return hotelService.findAll();
    }

    @PermitAll
    @RequestMapping(value = "api/hotel/findById={id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Hotel findHotelById(@PathVariable("id") long id) { return  hotelService.findHotelById(id);}


    @PermitAll
    @RequestMapping(value = "api/hotel/{id}/roomTypes", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Set<RoomType> findRoomTypes(@PathVariable Long id, HttpServletRequest req){
        return hotelService.findHotelById(id).getRoomTypes();
    }

    @RequestMapping(value = "api/hotel/{id}/addRoom", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @PreAuthorize("hasRole('ROLE_ADMIN_HOTEL')")
    public ResponseEntity<?> addRoom(@PathVariable Long id, HttpServletRequest httpServletRequest, @RequestBody RoomData room){

        User user =  getUser(httpServletRequest);
        return  responseTransaction(hotelService.addRoom(room,id,user));
    }

    @RequestMapping(value = "api/hotel/{id}/addRoomType", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @PreAuthorize("hasRole('ROLE_ADMIN_HOTEL')")
    public ResponseEntity<?> addRoomType(@PathVariable Long id, HttpServletRequest httpServletRequest, @RequestBody RoomType room){

        User user =  getUser(httpServletRequest);
        return  responseTransaction(hotelService.addRoomType(room,id,user));
    }
    @RequestMapping(value = "api/hotel/{id}/addNewFloor", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @PreAuthorize("hasRole('ROLE_ADMIN_HOTEL')")
    public ResponseEntity<?> addRoomType(@PathVariable Long id, HttpServletRequest httpServletRequest, @RequestBody FloorPlan floorPlan){
        User user =  getUser(httpServletRequest);
        return  responseTransaction(hotelService.addFloorPlan(floorPlan,id,user));
    }


    // /api/hotel/1/removeFloor:1 Failed to load resource: the server responded with a status of 404 ()

    @RequestMapping(value = "api/hotel/{id}/{idFloor}/removeFloor", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN_HOTEL')")
    public ResponseEntity<?> addRoomType(@PathVariable Long id,@PathVariable Long idFloor, HttpServletRequest httpServletRequest){

        User user =  getUser(httpServletRequest);
        return responseTransaction(hotelService.removeFloorPlan(idFloor,id,user));
    }


    private ResponseEntity<?> responseTransaction(Boolean resultOfTransaction ){
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
