package isa.projekat.Projekat.controller.rent_a_car;

import isa.projekat.Projekat.model.rent_a_car.Cars;
import isa.projekat.Projekat.service.rent_a_car.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import javax.annotation.security.PermitAll;
import java.util.List;

@RestController
public class CarController {

    @Autowired
    private CarService carService;

    @PermitAll
    @RequestMapping(value = "api/cars/findAll", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Page<Cars> findAll(@RequestParam String page) {
        int pageNumber;
        try {
            pageNumber = Integer.parseInt(page);
        } catch (Exception e){
            pageNumber = 0;
        }


        PageRequest pageRequest = PageRequest.of(pageNumber,10);

        return carService.findAll(pageRequest);
    }


}
