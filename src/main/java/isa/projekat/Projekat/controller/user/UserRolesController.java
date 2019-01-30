package isa.projekat.Projekat.controller.user;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRolesController {

    @RequestMapping(value = "api/user/user", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_USER')")
    public boolean checkUser(){
        return true;
    }

    @RequestMapping(value = "api/user/hotelAdmin", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN_HOTEL')")
    public boolean checkUserHotel(){
        return true;
    }

    @RequestMapping(value = "api/user/rentAdmin", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN_RENT')")
    public boolean checkUserRent(){
        return true;
    }

    @RequestMapping(value = "api/user/airlineAdmin", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN_AIRLINE')")
    public boolean checkUserAirline(){
        return true;
    }


}
