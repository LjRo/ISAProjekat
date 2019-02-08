package isa.projekat.Projekat.controller.user;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRolesController {

    @GetMapping(value = "api/user/user")
    @PreAuthorize("hasRole('ROLE_USER')")
    public boolean checkUser(){
        return true;
    }

    @GetMapping(value = "api/user/hotelAdmin")
    @PreAuthorize("hasRole('ROLE_ADMIN_HOTEL')")
    public boolean checkUserHotel(){
        return true;
    }

    @GetMapping(value = "api/user/rentAdmin")
    @PreAuthorize("hasRole('ROLE_ADMIN_RENT')")
    public boolean checkUserRent(){
        return true;
    }

    @GetMapping(value = "api/user/airlineAdmin")
    @PreAuthorize("hasRole('ROLE_ADMIN_AIRLINE')")
    public boolean checkUserAirline(){
        return true;
    }


}
