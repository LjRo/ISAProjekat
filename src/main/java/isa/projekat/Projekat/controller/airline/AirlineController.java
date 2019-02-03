package isa.projekat.Projekat.controller.airline;

import isa.projekat.Projekat.aspects.AdminEnabledCheck;
import isa.projekat.Projekat.model.airline.*;
import isa.projekat.Projekat.model.rent_a_car.Location;
import isa.projekat.Projekat.model.user.User;
import isa.projekat.Projekat.repository.UserRepository;
import isa.projekat.Projekat.security.TokenUtils;
import isa.projekat.Projekat.service.airline.AirlineService;
import isa.projekat.Projekat.service.airline.FlightService;
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
    private FlightService flightService;


    @Autowired
    private TokenUtils jwtTokenUtils;

    @Autowired
    private UserRepository userRepository;

    @PermitAll
    @RequestMapping(value = "api/airline/findAll", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Airline> findAll(){
        return airlineService.findAll();
    }

    @PermitAll
    @RequestMapping(value = "api/airline/findById={id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Airline findHotelById(@PathVariable("id") Long id) { return  airlineService.findById(id);}

    @PermitAll
    @RequestMapping(value = "api/airline/{id}/flights", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Flight> findAllActiveFlights(@PathVariable Long id,HttpServletRequest req){
        return airlineService.findAllActiveFlights(id);
    }

    @PermitAll
    @RequestMapping(value = "api/airline/{id}/quickFlights", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<QuickTicketData> findAllActiveFlightsWithQuickReservation(@PathVariable Long id, HttpServletRequest req){
        return airlineService.findAllActiveFlightsWithQuickReservation(id);
    }

    @RequestMapping(value = "api/airline/updateInfo", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('ROLE_ADMIN_AIRLINE')")
    @AdminEnabledCheck
    public void updateInfo(@RequestBody AirlineData ad, HttpServletRequest req){
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);
        airlineService.updateAirlineData(ad, email);
    }

    @RequestMapping(value = "api/airline/add", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @AdminEnabledCheck
    public void addAirline(@RequestBody Airline ad, HttpServletRequest req){
        airlineService.addAirline(ad);
    }

    @PermitAll
    @RequestMapping(value = "api/airline/{id}/profile", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Airline findAirline(@PathVariable Long id,HttpServletRequest req){

        return airlineService.findById(id);
    }

    @PermitAll
    @RequestMapping(value = "api/airline/{id}/lastSeatData", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public SeatData findLastSeatData(@PathVariable Long id,HttpServletRequest req){
        Long flightId = airlineService.findLastSeat(id);
        if(flightId == null) {
            return null;
        }

        return flightService.findSeatDataById(flightId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN_AIRLINE')")
    @RequestMapping(value = "api/airline/{id}/editData", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @AdminEnabledCheck
    public AirlineEditData findAirlineEditData(@PathVariable Long id,HttpServletRequest req){
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);
        User user = userRepository.findByUsername(email);

        if(user.getAdministratedAirline().getId() == id) {
            return airlineService.getEditData(id);
        } else {
            return null;
        }
    }

    @RequestMapping(value = "api/airline/{id}/edit", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('ROLE_ADMIN_AIRLINE')")
    @AdminEnabledCheck
    public void editAirline(@PathVariable Long id, @RequestBody AirlineEditData aED, HttpServletRequest req){
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);
        User user = userRepository.findByUsername(email);

        if(user.getAdministratedAirline().getId() == id) {
            airlineService.editAirline(aED, id);
        } else {
            return;
        }
    }

    @PermitAll
    @RequestMapping(value = "api/airline/{id}/destinations", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Location> findAirlineDestinations(@PathVariable Long id, HttpServletRequest req){

        return airlineService.findAirlineDestinations(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN_AIRLINE')")
    @RequestMapping(value = "api/location/{id}/delete", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @AdminEnabledCheck
    public boolean deleteDest(@PathVariable Long id, HttpServletRequest req){
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);
        return airlineService.deleteLocation(id,email);
    }

    @RequestMapping(value = "api/airline/{id}/addFlight", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('ROLE_ADMIN_AIRLINE')")
    @AdminEnabledCheck
    public Boolean addFlight(@PathVariable Long id, HttpServletRequest req, @RequestBody FlightData fd){
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);

        airlineService.addFlight(fd, email);
        return true;
    }

    @RequestMapping(value = "api/airline/{id}/addLocation", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('ROLE_ADMIN_AIRLINE')")
    @AdminEnabledCheck
    public void addLocation(@RequestBody Location loc , @PathVariable Long id, HttpServletRequest req){
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);
        airlineService.addLocation(loc,id,email);
    }

}
