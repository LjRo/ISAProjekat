package isa.projekat.Projekat.controller.airline;

import isa.projekat.Projekat.model.rent_a_car.Cars;
import isa.projekat.Projekat.service.rent_a_car.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public class AirlineController {

    @Autowired
    private CarService airlineService;

    @RequestMapping(value = "api/airlines/findAll", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Cars> findAll(){
        return airlineService.findAll();
    }

}
