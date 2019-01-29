package isa.projekat.Projekat.controller.rent_a_car;

import isa.projekat.Projekat.model.rent_a_car.CarType;
import isa.projekat.Projekat.model.rent_a_car.Cars;
import isa.projekat.Projekat.model.rent_a_car.RentACar;
import isa.projekat.Projekat.model.rent_a_car.RentReservation;
import isa.projekat.Projekat.model.user.User;
import isa.projekat.Projekat.security.TokenUtils;
import isa.projekat.Projekat.service.rent_a_car.CarService;
import isa.projekat.Projekat.service.user_auth.UserService;
import isa.projekat.Projekat.utils.PageRequestProvider;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
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



    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "api/cars/{idrent}/{idAir}/reserve", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> reserveCar(@PathVariable Long idrent, @PathVariable Long idAir, @RequestParam Long id, HttpServletRequest httpServletRequest,@RequestBody RentReservation rentReservation){
        User user = getUser(httpServletRequest);
        return responseTransaction(carService.reserveCar(id,idrent,user,rentReservation,idAir));
    }

   /* @Transactional(readOnly = true)
   // @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "api/cars/{idrent}/available", method = RequestMethod.GET)
    public Page<Cars> listAvailable(@PathVariable Long idrent, @RequestParam String page,@RequestParam Long carTypeId, @RequestParam String start, @RequestParam String end, @RequestParam Integer passengers) throws java.text.ParseException{

        //SimpleDateFormat  format =new SimpleDateFormat("yyyy-MM-dd");

        return carService.listAvailableWithDateOnly(idrent,pageRequestProvider.provideRequest(page),carTypeId,start,end,passengers);
    }*/

    @Transactional(readOnly = true)
    //@PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "api/cars/{idrent}/availablePrice", method = RequestMethod.GET)
    public Page<Cars> listAvailablePrice(@PathVariable Long idrent, @RequestParam String page, @RequestParam Long carTypeId, @RequestParam String start, @RequestParam String end,
                                         @RequestParam Integer passengers, @RequestParam BigDecimal minPrice, @RequestParam BigDecimal maxPrice){
        if (minPrice.equals(BigDecimal.valueOf(0)) && minPrice.equals(maxPrice)){
            maxPrice = BigDecimal.valueOf(20000);
        }
        return carService.listAvailableWithDate(idrent,pageRequestProvider.provideRequest(page),carTypeId,  minPrice, maxPrice,start,end,passengers);
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
