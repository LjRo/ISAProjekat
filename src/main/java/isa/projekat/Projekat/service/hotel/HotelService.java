package isa.projekat.Projekat.service.hotel;

import isa.projekat.Projekat.model.hotel.Hotel;
import isa.projekat.Projekat.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    public List<Hotel> findAll(){
        return hotelRepository.findAll();
    }
}
