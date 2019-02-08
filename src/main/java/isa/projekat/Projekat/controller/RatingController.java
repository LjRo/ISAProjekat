package isa.projekat.Projekat.controller;


import isa.projekat.Projekat.model.Rating;
import isa.projekat.Projekat.model.user.User;
import isa.projekat.Projekat.security.TokenUtils;
import isa.projekat.Projekat.service.RatingService;
import isa.projekat.Projekat.service.user_auth.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @Autowired
    private TokenUtils jwtTokenUtils;

    @Autowired
    private UserService userService;

    @PermitAll
    @GetMapping(value = "/rating/check",produces=MediaType.APPLICATION_JSON_VALUE)
    public Double getRating(@RequestParam Integer type, @RequestParam Long id){
        return ratingService.getRatingForTypeAndId(type,id);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping(value = "/rating/add", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map<String,String>> reserveCar(@RequestBody Rating rating, HttpServletRequest httpServletRequest){
        String authToken = jwtTokenUtils.getToken(httpServletRequest);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);
        User user = userService.findByUsername(email);
        return responseTransaction(ratingService.addNewRating(rating));
    }

    @SuppressWarnings("Duplicates")
    private ResponseEntity<Map<String,String>> responseTransaction(Boolean resultOfTransaction ){
        Map<String, String> result = new HashMap<>();
        if(resultOfTransaction )
        {
            result.put("result", "success");
            return ResponseEntity.accepted().body(result);
        }
        else
        {
            result.put("result", "error");
            result.put("body","410, Error while loading");
            return ResponseEntity.accepted().body(result);
        }
    }
}
