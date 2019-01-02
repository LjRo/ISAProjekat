package isa.projekat.Projekat.controller.rent_a_car;

import isa.projekat.Projekat.model.rent_a_car.Cars;
import isa.projekat.Projekat.service.rent_a_car.CarService;
import isa.projekat.Projekat.utils.PageRequestProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import javax.annotation.security.PermitAll;


@RestController
public class CarController {

    @Autowired
    private CarService carService;

    @Autowired
    private PageRequestProvider pageRequestProvider;

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
}
