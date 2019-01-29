package isa.projekat.Projekat.controller.airline;

import isa.projekat.Projekat.model.airline.*;
import isa.projekat.Projekat.security.TokenUtils;
import isa.projekat.Projekat.service.airline.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class FlightController {

    @Autowired
    private FlightService flightService;

    @Autowired
    private TokenUtils jwtTokenUtils;


    @RequestMapping(value = "api/flight/{id}/seatData", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('USER')")
    public SeatData findFlightSeatData(@PathVariable Long id, HttpServletRequest req){

        return flightService.findSeatDataById(id);
    }

    @RequestMapping(value = "api/flight/book", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('USER')")
    public void bookFlight(@RequestBody BookingData bookingData, HttpServletRequest req){
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);

        flightService.bookFlight(bookingData,email);
    }

    @RequestMapping(value = "api/flight/quickBook", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('USER')")
    public void quickBook(@RequestBody QuickTicketData quickTicketData, HttpServletRequest req){
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);

        flightService.quickBookFlight(quickTicketData,email);
    }

    @RequestMapping(value = "api/flight/search", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('USER')")
    public List<Flight> searchFlight(@RequestBody FlightSearchData searchData, HttpServletRequest req){
        String authToken = jwtTokenUtils.getToken(req);

        //flightService.quickBookFlight(quickTicketData,email);
        return flightService.searchFlights(searchData);
    }




}
