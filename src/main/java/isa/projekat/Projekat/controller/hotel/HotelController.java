package isa.projekat.Projekat.controller.hotel;


import isa.projekat.Projekat.aspects.AdminEnabledCheck;
import isa.projekat.Projekat.model.hotel.*;
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
    @GetMapping(value = "api/hotel/findAll", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Hotel> findAll() {
        return hotelService.findAll();
    }

    @PermitAll
    @GetMapping(value = "api/hotel/findAvailable", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Hotel> findAvailable(@RequestParam String name,@RequestParam String location,@RequestParam String arrival,@RequestParam String departure)
    {
        return hotelService.findAvailableByHotelId(arrival,departure,location,name);
    }


    @PermitAll
    @GetMapping(value = "api/hotel/findById={id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Hotel findHotelById(@PathVariable("id") long id) { return  hotelService.findHotelById(id);}


    @PermitAll
    @GetMapping(value = "api/hotel/{id}/roomTypes", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Set<RoomType> findRoomTypes(@PathVariable Long id, HttpServletRequest req){
        return hotelService.findHotelById(id).getRoomTypes();
    }


    @PermitAll
    @GetMapping(value = "api/hotel/{id}/PriceLists", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Set<HotelPriceList> findPriceLists(@PathVariable Long id, HttpServletRequest req)
    {
        return  hotelService.findHotelById(id).getHotelPriceList();
    }



    @PostMapping(value = "api/hotel/{id}/addRoom", consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @PreAuthorize("hasRole('ROLE_ADMIN_HOTEL')")
    @AdminEnabledCheck
    public ResponseEntity<Map<String,String>> addRoom(@PathVariable Long id, HttpServletRequest httpServletRequest, @RequestBody RoomData room){

        User user =  getUser(httpServletRequest);
        return  responseTransaction(hotelService.addRoom(room,id,user));
    }

    @PostMapping(value = "api/hotel/{id}/addRoomType", consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @PreAuthorize("hasRole('ROLE_ADMIN_HOTEL')")
    @AdminEnabledCheck
    public ResponseEntity<Map<String,String>> addRoomType(@PathVariable Long id, HttpServletRequest httpServletRequest, @RequestBody RoomType room){

        User user =  getUser(httpServletRequest);
        return  responseTransaction(hotelService.addRoomType(room,id,user));
    }
    @PostMapping(value = "api/hotel/{id}/addNewFloor", consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @PreAuthorize("hasRole('ROLE_ADMIN_HOTEL')")
    @AdminEnabledCheck
    public ResponseEntity<Map<String,String>> addNewFloor(@PathVariable Long id, HttpServletRequest httpServletRequest, @RequestBody FloorPlan floorPlan){
        User user =  getUser(httpServletRequest);
        return  responseTransaction(hotelService.addFloorPlan(floorPlan,id,user));
    }



    @PostMapping(value = "api/hotel/{id}/{idFloor}/removeFloor")
    @PreAuthorize("hasRole('ROLE_ADMIN_HOTEL')")
    @AdminEnabledCheck
    public ResponseEntity<Map<String,String>> removeFloor(@PathVariable Long id,@PathVariable Long idFloor, HttpServletRequest httpServletRequest){

        User user =  getUser(httpServletRequest);
        return responseTransaction(hotelService.removeFloorPlan(idFloor,id,user));
    }


    @PostMapping(value = "api/hotel/editHotel")
    @PreAuthorize("hasRole('ROLE_ADMIN_HOTEL')")
    @AdminEnabledCheck
    public  ResponseEntity<Map<String,String>> editHotel(@RequestBody Hotel hotel,HttpServletRequest httpServletRequest) {
        User user =  getUser(httpServletRequest);
        return responseTransaction(hotelService.editHotel(hotel,user));
    }
    @PostMapping(value = "api/hotel/addHotel")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @AdminEnabledCheck
    public void addHotel(@RequestBody Hotel hotel,HttpServletRequest httpServletRequest) {
        User user =  getUser(httpServletRequest);
        hotelService.addHotel(hotel,user);
    }

    @PostMapping(value = "api/hotel/editPriceList")
    @PreAuthorize("hasRole('ROLE_ADMIN_HOTEL')")
    public  ResponseEntity<Map<String,String>> editPriceList(@RequestBody HotelPriceList hotelPriceList,HttpServletRequest httpServletRequest) {
        User user =  getUser(httpServletRequest);
        return responseTransaction(hotelService.editHotelList(hotelPriceList,user));
    }

    @PostMapping(value = "api/hotel/{id}/addHotelServices", consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @PreAuthorize("hasRole('ROLE_ADMIN_HOTEL')")
    public ResponseEntity<Map<String,String>> addHotelServices(@PathVariable Long id, HttpServletRequest httpServletRequest, @RequestBody HotelServices hotelServices){
        User user =  getUser(httpServletRequest);
        return  responseTransaction(hotelService.addHotelServices(hotelServices,id,user));
    }

    @PostMapping(value = "api/hotel/{id}/{userId}/", consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Map<String,String>> addAdmin(@PathVariable Long id, HttpServletRequest httpServletRequest, @PathVariable Long userId){
        return  responseTransaction(hotelService.addHotelAdmin(id,userId));
    }

    @PostMapping(value = "api/hotel/editHotelServices")
    @PreAuthorize("hasRole('ROLE_ADMIN_HOTEL')")
    public  ResponseEntity<Map<String,String>> editHotelServices(@RequestBody HotelServices services,HttpServletRequest httpServletRequest) {
        User user =  getUser(httpServletRequest);
        return responseTransaction(hotelService.editHotelServices(services,user));
    }

    @PostMapping(value = "api/hotel/removeHotelServices")
    @PreAuthorize("hasRole('ROLE_ADMIN_HOTEL')")
    public ResponseEntity<Map<String,String>> removeHotelServices(@RequestBody HotelServices hotelServices, HttpServletRequest httpServletRequest){
        User user =  getUser(httpServletRequest);
        return responseTransaction(hotelService.removeHotelService(hotelServices,user));
    }

    @PermitAll
    @GetMapping(value = "api/hotel/{id}/HotelServicesForHotel", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Set<HotelServices> findHotelServicesList(@PathVariable Long id, HttpServletRequest req)
    {
        return  hotelService.findHotelById(id).getHotelServices();
    }

    @PermitAll
    @GetMapping(value = "api/hotel/{id}/HotelServices", produces = {MediaType.APPLICATION_JSON_VALUE})
    public HotelServices findHotelService(@PathVariable Long id, HttpServletRequest req)
    {
        return  hotelService.findHotelServiceById(id);
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
