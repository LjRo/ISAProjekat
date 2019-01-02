package isa.projekat.Projekat.service.rent_a_car;

import isa.projekat.Projekat.model.rent_a_car.Cars;
import isa.projekat.Projekat.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public Page<Cars> findAll(PageRequest pageRequest){
        return carRepository.findAll(pageRequest);
    }

    public Page<Cars> findByRentACarId( Long id,PageRequest pageRequest) { return carRepository.findByRentACarId(id,pageRequest);}
}
