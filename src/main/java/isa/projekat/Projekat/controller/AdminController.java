package isa.projekat.Projekat.controller;

import isa.projekat.Projekat.model.Prices;
import isa.projekat.Projekat.service.user_auth.PricesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;

@RestController
public class AdminController {


    @Autowired
    private PricesService pricesService;

    @PermitAll
    @GetMapping(value = "api/pricesDiscount", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Float findDiscountPrice(@RequestParam String name)
    {
        Float value = pricesService.getPrice(name);
        return value;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "api/pricesDiscount/update")
    public void addHotel(@RequestBody Prices prices, HttpServletRequest httpServletRequest) {
        pricesService.setPrice(prices);
    }
}
