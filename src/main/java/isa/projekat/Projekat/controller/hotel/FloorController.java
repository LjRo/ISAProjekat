package isa.projekat.Projekat.controller.hotel;

import isa.projekat.Projekat.aspects.AdminEnabledCheck;
import isa.projekat.Projekat.model.hotel.FloorPlan;
import isa.projekat.Projekat.model.user.User;
import isa.projekat.Projekat.security.TokenUtils;
import isa.projekat.Projekat.service.hotel.FloorService;
import isa.projekat.Projekat.service.user_auth.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
public class FloorController {


    @Autowired
    private FloorService floorService;

    @Autowired
    private TokenUtils jwtTokenUtils;

    @Autowired
    private UserService userService;

    @PermitAll
    @GetMapping(value = "api/floor/findById", produces = {MediaType.APPLICATION_JSON_VALUE})
    public FloorPlan findById(@RequestParam long id) {
        return floorService.findById(id);
    }


    @PermitAll
    @GetMapping(value = "api/floor/findAllByHotelId", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Set<FloorPlan> findAllByHotelId(@RequestParam long id) {
        return floorService.findByHotelIdOrderedByFloorNumberAsc(id);
    }

    @PostMapping(value = "api/floor/{id}/editFloor", consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @PreAuthorize("hasRole('ROLE_ADMIN_HOTEL')")
    @AdminEnabledCheck
    public ResponseEntity<Map<String,String>> addRoomType(@PathVariable Long id, HttpServletRequest httpServletRequest, @RequestBody FloorPlan floorPlan){
        User user =  getUser(httpServletRequest);
        return  responseTransaction(floorService.editFloorPlan(floorPlan,id,user));
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
