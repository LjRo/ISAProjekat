package isa.projekat.Projekat.controller.rent_a_car;

import isa.projekat.Projekat.model.rent_a_car.CarType;
import isa.projekat.Projekat.service.rent_a_car.CarTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CarTypeController {

    @Autowired
    private CarTypeService carTypeService;



    @GetMapping(value = "api/cartypes", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<CarType> findAll() {
        return carTypeService.findAll();
    }

}
