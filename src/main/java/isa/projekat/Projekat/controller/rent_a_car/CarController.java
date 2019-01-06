package isa.projekat.Projekat.controller.rent_a_car;

import isa.projekat.Projekat.model.rent_a_car.Cars;
import isa.projekat.Projekat.model.rent_a_car.RentACar;
import isa.projekat.Projekat.model.user.User;
import isa.projekat.Projekat.security.TokenUtils;
import isa.projekat.Projekat.service.rent_a_car.CarService;
import isa.projekat.Projekat.service.user_auth.UserService;
import isa.projekat.Projekat.utils.PageRequestProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@RestController
public class CarController {

    @Autowired
    private CarService carService;

    @Autowired
    private PageRequestProvider pageRequestProvider;

    @Autowired
    private TokenUtils jwtTokenUtils;

    @Autowired
    private UserService userService;

    @PermitAll
    @RequestMapping(value = "api/cars/findAll", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Page<Cars> findAll(@RequestParam String page) {
        return carService.findAll(pageRequestProvider.provideRequest(page));
    }

    @PermitAll
    @RequestMapping(value = "api/cars/findByIdAll", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Page<Cars> findById(@RequestParam long id,@RequestParam String page) {
        return carService.findByRentACarId(id,pageRequestProvider.provideRequest(page));
    }

    @RequestMapping(value = "api/cars/{id}",method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Cars findCarById(@PathVariable Long id){

        return carService.findByCarId(id);
    }


    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN_RENT')")
    @RequestMapping(value = "api/cars/edit", method =  RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> editCar(@RequestBody Cars cars, HttpServletRequest httpServletRequest){
        User user = getUser(httpServletRequest);
        return responseTransaction(carService.editCar(cars,user));
    }


    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN_RENT')")
    @RequestMapping(value = "api/cars/{id}/add", method =  RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> addCar(@PathVariable Long id,@RequestBody Cars cars, HttpServletRequest httpServletRequest){
        User user = getUser(httpServletRequest);
        return responseTransaction(carService.addCars(cars,user,id));
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN_RENT')")
    @RequestMapping(value = "api/cars/{idrent}/remove", method =  RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> removeCar(@PathVariable Long idrent,@RequestParam long id ,HttpServletRequest httpServletRequest){
        User user = getUser(httpServletRequest);
        return responseTransaction(carService.removeCar(id,idrent,user));
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
