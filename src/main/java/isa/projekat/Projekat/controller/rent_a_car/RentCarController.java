package isa.projekat.Projekat.controller.rent_a_car;

import isa.projekat.Projekat.aspects.AdminEnabledCheck;
import isa.projekat.Projekat.model.rent_a_car.RentACar;
import isa.projekat.Projekat.model.user.User;
import isa.projekat.Projekat.security.TokenUtils;
import isa.projekat.Projekat.service.rent_a_car.RentCarsService;
import isa.projekat.Projekat.service.user_auth.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RentCarController {


    @Autowired
    private RentCarsService rentCarsService;

    @Autowired
    private TokenUtils jwtTokenUtils;

    @Autowired
    private UserService userService;


    @GetMapping(value = "api/rentacar/findAll", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<RentACar> findAll() {
        List<RentACar> list = rentCarsService.findAll();
        for (RentACar rent : list){
            rent.setAdmins(null);
        }
        return list;
    }



    @GetMapping(value = "api/rentacar/filtered", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<RentACar> findFilteredRentACar(@RequestParam Integer type, @RequestParam String search, @RequestParam String start, @RequestParam String end){

        if (type == 0){
            return rentCarsService.findAllByName(search,start,end);
        }else {
            return rentCarsService.findAllByLocation(search,start,end);
        }

    }



    @GetMapping(value = "api/rentacar/findById={id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public RentACar findRentACarById(@PathVariable("id") long id) {
        RentACar rentACar = rentCarsService.findById(id);
        rentACar.setAdmins(null);
        return rentACar;
    }


    @PreAuthorize("hasRole('ROLE_ADMIN_RENT')")
    @AdminEnabledCheck
    @PostMapping(value = "api/rentacar/edit", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map<String,String>> editRentACar(@RequestBody RentACar rentACar,  HttpServletRequest httpServletRequest){
        User user =  getUser(httpServletRequest);

        if (rentCarsService.editRentACar(rentACar,user)){
            Map<String, String> result = new HashMap<>();
            result.put("result", "success");
            return ResponseEntity.accepted().body(result);
        }else {
            return ResponseEntity.status(401).build();
        }

    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @AdminEnabledCheck
    @PostMapping(value = "api/rentacar/add", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map<String,String>> addRentacar(@RequestBody RentACar rentACar,  HttpServletRequest httpServletRequest){
        if (rentCarsService.addRentacar(rentACar)){
            Map<String, String> result = new HashMap<>();
            result.put("result", "success");
            return ResponseEntity.accepted().body(result);
        }else {
            return ResponseEntity.status(401).build();
        }

    }

    private User getUser( HttpServletRequest httpServletRequest){
        String authToken = jwtTokenUtils.getToken(httpServletRequest);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);
        return userService.findByUsername(email);
    }
}
