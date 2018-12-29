package isa.projekat.Projekat.service.RentACar;

import isa.projekat.Projekat.model.RentACar.Cars;
import isa.projekat.Projekat.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public List<Cars> findAll(){
       return carRepository.findAll();
    }

}
