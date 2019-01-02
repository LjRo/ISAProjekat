package isa.projekat.Projekat.controller.hotel;

import isa.projekat.Projekat.model.hotel.Room;
import isa.projekat.Projekat.service.hotel.RoomService;
import isa.projekat.Projekat.utils.PageRequestProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;

@RestController
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private PageRequestProvider pageRequestProvider;


    @PermitAll
    @RequestMapping(value = "api/rooms/findAll", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Page<Room> findAll(@RequestParam String page) {
        return roomService.findAll(pageRequestProvider.provideRequest(page));
    }


}
