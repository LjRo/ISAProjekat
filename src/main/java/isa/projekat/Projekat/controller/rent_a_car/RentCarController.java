package isa.projekat.Projekat.controller.rent_a_car;

import isa.projekat.Projekat.model.rent_a_car.RentACar;
import isa.projekat.Projekat.model.rent_a_car.RentOffice;
import isa.projekat.Projekat.service.rent_a_car.RentCarsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.util.List;

@RestController
public class RentCarController {


    @Autowired
    private RentCarsService rentCarsService;

    @PermitAll
    @RequestMapping(value = "api/rentacar/findAll", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<RentACar> findAll() {
        return rentCarsService.findAll();
    }


    @PermitAll
    @RequestMapping(value = "api/rentacar/findById={id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public RentACar findRentACarById(@PathVariable("id") long id) { return  rentCarsService.findById(id);}




}
