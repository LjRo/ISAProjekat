package isa.projekat.Projekat.controller.airline;

import isa.projekat.Projekat.model.airline.Seat;
import isa.projekat.Projekat.model.airline.SeatData;
import isa.projekat.Projekat.security.TokenUtils;
import isa.projekat.Projekat.service.airline.AirlineService;
import isa.projekat.Projekat.service.airline.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;

@RestController
public class FlightController {

    @Autowired
    private AirlineService airlineService;

    @Autowired
    private FlightService flightService;

    @Autowired
    private TokenUtils jwtTokenUtils;


    @RequestMapping(value = "api/flight/{id}/seatData", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('USER')")
    public SeatData findFlightSeatData(@PathVariable Long id, HttpServletRequest req){

        //return flightService.findSeatDataById(id);
        SeatData dummy = new SeatData();
        ArrayList<Seat> dummySeats = new ArrayList<>();
        dummySeats.add(new Seat(0,0,new BigDecimal("5"),null,false,false));
        dummySeats.add(new Seat(0,1,new BigDecimal("5"),null,true,false));
        dummySeats.add(new Seat(0,2,new BigDecimal("5"),null,false,false));
        dummySeats.add(new Seat(0,3,new BigDecimal("5"),null,true,false));
        dummySeats.add(new Seat(1,0,new BigDecimal("5"),null,false,false));
        dummySeats.add(new Seat(1,1,new BigDecimal("5"),null,false,true));
        dummySeats.add(new Seat(1,2,new BigDecimal("5"),null,false,true));
        dummySeats.add(new Seat(1,3,new BigDecimal("5"),null,false,false));
        dummy.setSegments(2);
        dummy.setColumns(2);
        dummy.setRows(2);
        dummy.setSeats(dummySeats);

        return dummy;
    }


}
