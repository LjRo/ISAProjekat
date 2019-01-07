package isa.projekat.Projekat.controller.airline;

import isa.projekat.Projekat.model.airline.BookingData;
import isa.projekat.Projekat.model.airline.SeatData;
import isa.projekat.Projekat.security.TokenUtils;
import isa.projekat.Projekat.service.airline.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    public void updateInfo(@RequestBody BookingData bookingData, HttpServletRequest req){
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);

        flightService.bookFlight(bookingData,email);
    }

    


}
