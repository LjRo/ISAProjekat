package isa.projekat.Projekat.controller.airline;

import isa.projekat.Projekat.model.RentACar.Cars;
import isa.projekat.Projekat.service.RentACar.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AirlineController {

    @Autowired
    private CarService airlineService;

    @RequestMapping(value = "api/airlines/findAll", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Cars> findAll(){
        return airlineService.findAll();
    }

}
