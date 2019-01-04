package isa.projekat.Projekat.controller.rent_a_car;

import isa.projekat.Projekat.model.rent_a_car.RentACar;
import isa.projekat.Projekat.model.rent_a_car.RentOffice;
import isa.projekat.Projekat.model.user.User;
import isa.projekat.Projekat.security.TokenUtils;
import isa.projekat.Projekat.service.rent_a_car.RentCarsService;
import isa.projekat.Projekat.service.user_auth.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
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

    @PermitAll
    @RequestMapping(value = "api/rentacar/findAll", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<RentACar> findAll() {
        List<RentACar> list = rentCarsService.findAll();
        for (RentACar rent : list){
            rent.setAdmins(null);
        }
        return list;
    }


    @PermitAll
    @RequestMapping(value = "api/rentacar/findById={id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public RentACar findRentACarById(@PathVariable("id") long id) {
        RentACar rentACar = rentCarsService.findById(id);
        rentACar.setAdmins(null);
        return rentACar;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN_RENT')")
    @RequestMapping(value = "api/rentacar/edit", method =  RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> editRentACar(@RequestBody RentACar rentACar,  HttpServletRequest httpServletRequest){

        String authToken = jwtTokenUtils.getToken(httpServletRequest);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);
        User user = userService.findByUsername(email);

        if (rentCarsService.editRentACar(rentACar,user)){
            Map<String, String> result = new HashMap<>();
            result.put("result", "success");
            return ResponseEntity.accepted().body(result);
        }else {
            return ResponseEntity.status(401).build();
        }

    }


}
