package isa.projekat.Projekat.controller.rent_a_car;

import isa.projekat.Projekat.model.rent_a_car.RentOffice;
import isa.projekat.Projekat.service.rent_a_car.RentOfficeService;
import isa.projekat.Projekat.utils.PageRequestProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;

@RestController
public class RentOfficeController {


    @Autowired
    private RentOfficeService rentOfficeService;

    @Autowired
    private PageRequestProvider pageRequestProvider;

    @PermitAll
    @RequestMapping(value = "api/office/findAll", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Page<RentOffice> findAll(@RequestParam String page) {
        return rentOfficeService.findAll(pageRequestProvider.provideRequest(page));
    }

    @PermitAll
    @RequestMapping(value = "api/office/findByIdAll", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Page<RentOffice> findAllByRentACarId(@RequestParam long id,@RequestParam String page) {
        System.out.println("USAOOOOO");
        return rentOfficeService.findAllByRentACarId(id,pageRequestProvider.provideRequest(page));
    }


}
