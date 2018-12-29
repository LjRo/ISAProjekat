package isa.projekat.Projekat.service.airline;

import isa.projekat.Projekat.model.airline.Airline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirlineService {
    @Autowired
    private AirlineService airlineRepository;

    public List<Airline> findAll(){
        return airlineRepository.findAll();
    }

}
