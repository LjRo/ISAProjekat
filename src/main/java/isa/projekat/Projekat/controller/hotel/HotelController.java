package isa.projekat.Projekat.controller.hotel;


import isa.projekat.Projekat.model.hotel.Hotel;
import isa.projekat.Projekat.service.hotel.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import java.util.List;


@RestController
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PermitAll
    @RequestMapping(value = "api/hotel/findAll", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Hotel> findAll() {
        return hotelService.findAll();
    }

    @PermitAll
    @RequestMapping(value = "api/hotel/findById={id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Hotel findHotelById(@PathVariable("id") long id) { return  hotelService.findHotelById(id);}
}
