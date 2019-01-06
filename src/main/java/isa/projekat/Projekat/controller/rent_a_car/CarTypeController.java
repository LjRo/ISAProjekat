package isa.projekat.Projekat.controller.rent_a_car;

import isa.projekat.Projekat.model.rent_a_car.CarType;
import isa.projekat.Projekat.service.rent_a_car.CarTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import java.util.List;

@RestController
public class CarTypeController {

    @Autowired
    private CarTypeService carTypeService;

    @PermitAll
    @RequestMapping(value = "api/cartypes/", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<CarType> findAll() {
        return carTypeService.findAll();
    }

}
