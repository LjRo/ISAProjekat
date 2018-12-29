package isa.projekat.Projekat.service.airline;

import isa.projekat.Projekat.model.Airline;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AirlineService {
    @Autowired
    private AirlineService airlineRepository;

    public List<Airline> findAll(){
        return airlineRepository.findAll();
    }

}
