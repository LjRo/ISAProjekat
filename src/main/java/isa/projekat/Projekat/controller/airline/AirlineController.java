package isa.projekat.Projekat.controller.airline;

import isa.projekat.Projekat.model.airline.Airline;
import isa.projekat.Projekat.model.airline.AirlineData;
import isa.projekat.Projekat.model.airline.FlightData;
import isa.projekat.Projekat.model.rent_a_car.Location;
import isa.projekat.Projekat.security.TokenUtils;
import isa.projekat.Projekat.service.airline.AirlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class AirlineController {

    @Autowired
    private AirlineService airlineService;

    @Autowired
    private TokenUtils jwtTokenUtils;

    @PermitAll
    @RequestMapping(value = "api/airline/findAll", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Airline> findAll(){
        return airlineService.findAll();
    }

    @PermitAll
    @RequestMapping(value = "api/airline/findById={id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Airline findHotelById(@PathVariable("id") Long id) { return  airlineService.findById(id);}


    @RequestMapping(value = "api/airline/updateInfo", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('ROLE_ADMIN_AIRLINE')")
    public void updateInfo(@RequestBody AirlineData ad, HttpServletRequest req){
        String email = "temp";
        airlineService.updateAirlineData(ad, email);
    }

    @PermitAll
    @RequestMapping(value = "api/airline/{id}/profile", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Airline findAirline(@PathVariable Long id,HttpServletRequest req){

        return airlineService.findById(id);
    }

    @PermitAll
    @RequestMapping(value = "api/airline/{id}/destinations", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Location> findAirlineDestinations(@PathVariable Long id, HttpServletRequest req){

        return airlineService.findAirlineDestinations(id);
    }

    @RequestMapping(value = "api/airline/{id}/addFlight", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('ROLE_ADMIN_AIRLINE')")
    public Boolean addFlight(@PathVariable Long id, HttpServletRequest req, @RequestBody FlightData fd){
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);

        airlineService.addFlight(fd, email);
        return true;
    }

}
