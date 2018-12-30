package isa.projekat.Projekat.service.rent_a_car;

import isa.projekat.Projekat.model.rent_a_car.RentACar;
import isa.projekat.Projekat.repository.RentCarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentCarsService {

    @Autowired
    private RentCarRepository rentCarRepository;

    public List<RentACar> findAll(){
        return rentCarRepository.findAll();
    }


}
