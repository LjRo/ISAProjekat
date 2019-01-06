package isa.projekat.Projekat.controller.hotel;

import isa.projekat.Projekat.model.hotel.FloorPlan;
import isa.projekat.Projekat.service.hotel.FloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import java.util.Set;

@RestController
public class FloorController {


    @Autowired
    private FloorService floorService;

    @PermitAll
    @RequestMapping(value = "api/floor/findById", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public FloorPlan findById(@RequestParam long id) {
        return floorService.findById(id);
    }


    @PermitAll
    @RequestMapping(value = "api/floor/findAllByHotelId", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Set<FloorPlan> findAllByHotelId(@RequestParam long id) {
        return floorService.findByHotelIdOrderedByFloorNumberAsc(id);
    }



}
