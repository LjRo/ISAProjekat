package isa.projekat.Projekat.controller.rent_a_car;

import isa.projekat.Projekat.model.rent_a_car.RentOffice;
import isa.projekat.Projekat.model.user.User;
import isa.projekat.Projekat.service.rent_a_car.RentOfficeService;
import isa.projekat.Projekat.service.user_auth.UserService;
import isa.projekat.Projekat.utils.PageRequestProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import isa.projekat.Projekat.security.TokenUtils;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class RentOfficeController {


    @Autowired
    private RentOfficeService rentOfficeService;

    @Autowired
    private PageRequestProvider pageRequestProvider;

    @Autowired
    private TokenUtils jwtTokenUtils;

    @Autowired
    private UserService userService;

    @PermitAll
    @RequestMapping(value = "api/office/findAll", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Page<RentOffice> findAll(@RequestParam String page) {
        return rentOfficeService.findAll(pageRequestProvider.provideRequest(page));
    }

    @PermitAll
    @RequestMapping(value = "api/office/findByIdAll", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Page<RentOffice> findAllByRentACarId(@RequestParam long id,@RequestParam String page) {

        return rentOfficeService.findAllByRentACarId(id,pageRequestProvider.provideRequest(page));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN_RENT')")
    @RequestMapping(value = "api/office/{id}/edit", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public  boolean changed(@PathVariable long id, @RequestBody RentOffice changed) {



            return  true;
    }

    @RequestMapping(value = "api/office/{idrent}/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public RentOffice findByIdAndRentACarId(@PathVariable long id,@PathVariable long idrent){
        return rentOfficeService.findByIdAndRentACarId(id,idrent);
    }

    @PermitAll
    @RequestMapping(value = "api/office/{rentid}/add",method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> addOffice(@PathVariable long rentid, @RequestBody RentOffice rentOffice, HttpServletRequest httpServletRequest) {

        String authToken = jwtTokenUtils.getToken(httpServletRequest);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);
        User user = userService.findByUsername(email);

        if (rentOfficeService.addOffice(rentOffice,rentid,user)){
            Map<String, String> result = new HashMap<>();
            result.put("result", "success");
            return ResponseEntity.accepted().body(result);
        }else {
            Map<String, String> result = new HashMap<>();
            result.put("result", "error");
            result.put("body","401, Unauthorized access");
            return ResponseEntity.accepted().body(result);
        }



    }


}
