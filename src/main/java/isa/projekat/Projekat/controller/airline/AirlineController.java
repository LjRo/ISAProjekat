package isa.projekat.Projekat.controller.airline;

import isa.projekat.Projekat.model.airline.Airline;
import isa.projekat.Projekat.model.airline.AirlineData;
import isa.projekat.Projekat.service.airline.AirlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class AirlineController {

    @Autowired
    private AirlineService airlineService;

    @PermitAll
    @RequestMapping(value = "api/airline/findAll", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Airline> findAll(){
        return airlineService.findAll();
    }

    @PermitAll
    @RequestMapping(value = "api/airline/findById={id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Airline findHotelById(@PathVariable("id") Long id) { return  airlineService.findById(id);}


    @RequestMapping(value = "api/airline/updateInfo", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void updateInfo(@RequestBody AirlineData ad, HttpServletRequest req){
        String email = "temp";
        airlineService.updateAirlineData(ad, email);
    }

}
