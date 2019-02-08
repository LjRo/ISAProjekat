package isa.projekat.Projekat.controller.airline;

import isa.projekat.Projekat.model.airline.*;
import isa.projekat.Projekat.model.user.User;
import isa.projekat.Projekat.security.TokenUtils;
import isa.projekat.Projekat.service.airline.FlightService;
import isa.projekat.Projekat.service.user_auth.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private UserService userService;


    @RequestMapping(value = "api/flight/{id}/seatData", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('USER')")
    public SeatData findFlightSeatData(@PathVariable Long id, HttpServletRequest req){

        return flightService.findSeatDataById(id);
    }

    @RequestMapping(value = "api/flight/{id}/", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Flight findFlightById(@PathVariable Long id, HttpServletRequest req){
        return flightService.findById(id);
    }

    @RequestMapping(value = "api/flight/book", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity bookFlight(@RequestBody BookingData bookingData, HttpServletRequest req){
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);

        return ResponseFormatter.format(flightService.bookFlight(bookingData,email),false);
    }

    @RequestMapping(value = "api/flight/quickBook", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity quickBook(@RequestBody QuickTicketData quickTicketData, HttpServletRequest req){
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);

        return ResponseFormatter.format(flightService.quickBookFlight(quickTicketData,email),false);

    }

    @RequestMapping(value = "api/order/{id}/confirm", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity confirmOrder(@PathVariable Long orderId, HttpServletRequest req){
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);

        return ResponseFormatter.format(flightService.finishOrder(orderId,email),false);
    }

    @RequestMapping(value = "api/order/{id}/isOrdering", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('USER')")
    public Boolean isOrdering(@PathVariable Long orderId, HttpServletRequest req){
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);

        return flightService.isOrdering(orderId,email);
    }

    @RequestMapping(value = "api/order/confirmLast", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity confirmLastOrder(HttpServletRequest req){
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);

        return ResponseFormatter.format(flightService.finishLastOrder(email),false);
    }

    @RequestMapping(value = "api/flight/search", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('USER')")
    public List<FlightSearchResultData> searchFlight(@RequestBody FlightSearchData searchData, HttpServletRequest req){
        return flightService.searchFlights(searchData);
    }

    @RequestMapping(value = "api/flight/reservations", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('USER')")
    public List<Reservation> findUserReservations(HttpServletRequest req){
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);
        User user = userService.findByUsername(email);
        return flightService.findReservationsByUserId(user.getId());
    }

    @RequestMapping(value = "api/flight/allReservations", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('USER')")
    public List<Reservation> findAllUserReservations(HttpServletRequest req){
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);
        User user = userService.findByUsername(email);
        return flightService.findAllReservationsByUserId(user.getId());
    }

    @RequestMapping(value= "api/flight/allOrders", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('USER')")
    public List<Order> findAllUserOrders(HttpServletRequest req){
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);
        User user = userService.findByUsername(email);
        return flightService.findAllOrders(user.getId());
    }

    @RequestMapping(value= "api/flight/{id}/cancel", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('USER')")
    public Boolean cancelFlight(@PathVariable Long id, HttpServletRequest req){
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);
        User user = userService.findByUsername(email);
        return flightService.cancelFlight(id,user);
    }




    @RequestMapping(value= "api/flight/{id}/order", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('USER')")
    public Order findAllUserOrders(HttpServletRequest req, @PathVariable Long id){
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);
        User user = userService.findByUsername(email);
        Order or =  flightService.findOrderById(id);
        if (or.getPlacedOrder().getId().equals(user.getId())){
            return or;
        }else {
            return null;
        }
    }

    @RequestMapping(value= "api/flight/allFutureOrders", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAnyRole('USER','ROLE_ADMIN','ROLE_ADMIN_AIRLINE','ROLE_ADMIN_HOTEL','ROLE_ADMIN_RENT')")
    public List<Order> findAllUserFutureOrders(HttpServletRequest req){
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);
        User user = userService.findByUsername(email);
        return flightService.findAllFutureOrders(user.getId());
    }


    @RequestMapping(value= "api/flight/allNonFinishedOrders", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('USER')")
    public List<Order> findAllUserNotFinishedOrders(HttpServletRequest req){
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);
        User user = userService.findByUsername(email);
        return flightService.findAllNotFinished(user.getId());
    }

    @RequestMapping(value = "api/flight/futureReservations", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('USER')")
    public List<Reservation> findFutureUserReservations(HttpServletRequest req){
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);
        User user = userService.findByUsername(email);
        return flightService.findFutureReservationByUserId(user.getId());
    }


    @RequestMapping(value = "api/flight/reservationRent", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('USER')")
    public List<Reservation> findRentReservations(HttpServletRequest req){
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);
        User user = userService.findByUsername(email);
        return flightService.findRentReservations(user.getId());
    }








}
