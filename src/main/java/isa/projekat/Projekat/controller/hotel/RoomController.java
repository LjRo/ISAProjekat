package isa.projekat.Projekat.controller.hotel;

import isa.projekat.Projekat.model.hotel.Room;
import isa.projekat.Projekat.model.hotel.RoomSearchData;
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
    @RequestMapping(value = "api/rooms/findById", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Room find(@RequestParam Long id) {
        return roomService.findById(id);
    }

    @PermitAll
    @RequestMapping(value = "api/rooms/findAll", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Page<Room> findAll(@RequestParam String page) {
        return roomService.findAll(pageRequestProvider.provideRequest(page));
    }

    @PermitAll
    @RequestMapping(value = "api/rooms/findByIdAll", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Page<Room> findById(@RequestParam long id,@RequestParam String page) {
        return roomService.findByHotelId(id,pageRequestProvider.provideRequest(page));
    }

    @PermitAll
    @RequestMapping(value = "api/rooms/findByIdAvailable", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Page<Room> findByIdAvailable(@RequestBody RoomSearchData roomSearchData) {
        return roomService.findAvailableByHotelId(roomSearchData,pageRequestProvider.provideRequest(roomSearchData.getPage()));
    }


    @RequestMapping(value = "api/rooms/{id}/editRoom", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN_HOTEL')")
    public ResponseEntity<?> editHotel(@RequestBody Room room,@PathVariable Long id, HttpServletRequest httpServletRequest) {
        User user = getUser(httpServletRequest);
        return responseTransaction(roomService.editRoom(room,user,id));
    }

    @RequestMapping(value = "api/rooms/{id}/deleteRoom", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN_HOTEL')")
    public ResponseEntity<?> deleteRoom(@RequestBody Room room,@PathVariable Long id, HttpServletRequest httpServletRequest) {
        User user = getUser(httpServletRequest);
        return responseTransaction(roomService.deleteRoom(room,user,id));
    }


    @SuppressWarnings("Duplicates")
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
