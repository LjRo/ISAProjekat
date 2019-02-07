package isa.projekat.Projekat.controller.rent_a_car;

import isa.projekat.Projekat.aspects.AdminEnabledCheck;
import isa.projekat.Projekat.model.rent_a_car.Cars;
import isa.projekat.Projekat.model.rent_a_car.RentReservation;
import isa.projekat.Projekat.model.user.User;
import isa.projekat.Projekat.security.TokenUtils;
import isa.projekat.Projekat.service.rent_a_car.CarService;
import isa.projekat.Projekat.service.user_auth.UserService;
import isa.projekat.Projekat.utils.PageRequestProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
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


    @RequestMapping(value = "api/cars/findAll", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Page<Cars> findAll(@RequestParam String page) {
        return carService.findAll(pageRequestProvider.provideRequest(page));
    }


    @RequestMapping(value = "api/cars/findByIdAll", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Page<Cars> findById(@RequestParam long id,@RequestParam String page) {
        return carService.findByRentACarId(id,pageRequestProvider.provideRequest(page));
    }


    @RequestMapping(value = "api/cars/{id}",method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Cars findCarById(@PathVariable Long id){

        return carService.findByCarId(id);
    }



    @PreAuthorize("hasRole('ROLE_ADMIN_RENT')")
    @AdminEnabledCheck
    @RequestMapping(value = "api/cars/edit", method =  RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> editCar(@RequestBody Cars cars, HttpServletRequest httpServletRequest){
        User user = getUser(httpServletRequest);
        return responseTransaction(carService.editCar(cars,user));
    }



    @PreAuthorize("hasRole('ROLE_ADMIN_RENT')")
    @AdminEnabledCheck
    @RequestMapping(value = "api/cars/{id}/add", method =  RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> addCar(@PathVariable Long id,@RequestBody Cars cars, HttpServletRequest httpServletRequest){
        User user = getUser(httpServletRequest);
        return responseTransaction(carService.addCars(cars,user,id));
    }


    @PreAuthorize("hasRole('ROLE_ADMIN_RENT')")
    @AdminEnabledCheck
    @RequestMapping(value = "api/cars/{idrent}/remove", method =  RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> removeCar(@PathVariable Long idrent,@RequestParam long id ,HttpServletRequest httpServletRequest){
        User user = getUser(httpServletRequest);
        return responseTransaction(carService.removeCar(id, idrent, user));
    }




    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "api/cars/{idrent}/{idAir}/reserve", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> reserveCar(@PathVariable Long idrent, @PathVariable Long idAir, @RequestBody RentReservation rentReservation, HttpServletRequest httpServletRequest){
        User user = getUser(httpServletRequest);
        return responseTransaction(carService.reserveCar(rentReservation.getRentedCar().getId(), idrent, user, rentReservation, idAir));
    }



    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "api/cars/{idReservation}/{idOrder}/quickReserve", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> quickReserve(@PathVariable Long idReservation, @PathVariable Long idOrder, HttpServletRequest httpServletRequest){
        User user = getUser(httpServletRequest);
        return responseTransaction(carService.quickReserve(idOrder, idReservation, user));
    }




    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "api/cars/{idrent}/quick", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<RentReservation> listQuickReservations(@PathVariable Long idrent){
        return carService.listQuickReservations(idrent);
    }



    //@PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "api/cars/{idrent}/availablePrice", method = RequestMethod.GET)
    public Page<Cars> listAvailablePrice(@PathVariable Long idrent, @RequestParam String page, @RequestParam Long carTypeId, @RequestParam String start, @RequestParam String end,
                                         @RequestParam Integer passengers, @RequestParam BigDecimal minPrice, @RequestParam BigDecimal maxPrice){
        if (minPrice.equals(BigDecimal.valueOf(0)) && minPrice.equals(maxPrice)){
            maxPrice = BigDecimal.valueOf(20000);
        }
        return carService.listAvailableWithDate(idrent,pageRequestProvider.provideRequest(page),carTypeId,  minPrice, maxPrice,start,end,passengers);
    }



    //@PreAuthorize("hasRole('ROLE_ADMIN_RENT')")
    @RequestMapping(value = "api/cars/{idrent}/check", method = RequestMethod.GET)
    public Boolean checkEdibility(@PathVariable Long idrent, @RequestParam Long id){
        return carService.checkEdibility(idrent, id);
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
