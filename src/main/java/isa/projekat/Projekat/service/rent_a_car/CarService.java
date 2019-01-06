package isa.projekat.Projekat.service.rent_a_car;

import isa.projekat.Projekat.model.rent_a_car.Cars;
import isa.projekat.Projekat.model.rent_a_car.RentACar;
import isa.projekat.Projekat.model.user.User;
import isa.projekat.Projekat.repository.CarRepository;
import isa.projekat.Projekat.repository.CarTypeRepository;
import isa.projekat.Projekat.repository.RentCarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;

import java.util.Optional;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private RentCarRepository rentCarRepository;

    @Autowired
    private CarTypeRepository carTypeRepository;

    public Page<Cars> findAll(PageRequest pageRequest){
        return carRepository.findAll(pageRequest);
    }

    public Page<Cars> findByRentACarId( Long id,PageRequest pageRequest) { return carRepository.findByRentACarId(id,pageRequest);}

    public Cars findByCarId( long id){ return carRepository.findById(id).get(); }

    public boolean addCars(Cars cars, User user, Long id){

        Optional<RentACar> optionalRentACar = rentCarRepository.findById(id);

        if ( !optionalRentACar.isPresent())
            return  false;

        if (!optionalRentACar.get().getAdmins().contains(user))
            return  false;

        Cars carToAdd = new Cars();

        carToAdd.setRentACar(optionalRentACar.get());

        setCar(carToAdd,cars);

        carRepository.save(carToAdd);
        return  true;
    }

    public boolean editCar(Cars cars, User user){

        Optional<Cars> optionalCars = carRepository.findById(cars.getId());
        Optional<RentACar> optionalRentACar = rentCarRepository.findById(optionalCars.get().getId());


        if ( !optionalRentACar.isPresent() && !optionalCars.isPresent())
            return  false; // rent a car doesnt exist nor the car in the database

        if (!optionalRentACar.get().getAdmins().contains(user))
            return  false; // no permission

        Cars fromDatabase = optionalCars.get();

        setCar(fromDatabase,cars);

        carRepository.save(fromDatabase);
        return  true;
    }

    @SuppressWarnings("Duplicates")
    public boolean removeCar(Long id, Long idrent, User user){

        Optional<RentACar> optionalRentACar = rentCarRepository.findById(idrent);
        Optional<Cars> optionalCars = carRepository.findById(id);

        if (!optionalRentACar.isPresent() || !optionalCars.isPresent()){
            return  false;
        }

        if (!optionalRentACar.get().getAdmins().contains(user))
            return  false;


        Cars toRemove = optionalCars.get();

        optionalRentACar.get().getCars().remove(toRemove);

     //   toRemove.setRentACar(null);
     //   toRemove.setType(null);

        carRepository.delete(toRemove);


        return true;
    }


    private void setCar(Cars toSet, Cars dataFrom){

        toSet.setDailyPrice(dataFrom.getDailyPrice());
        toSet.setFastReserved(dataFrom.getFastReserved());
        toSet.setMark(dataFrom.getMark());
        toSet.setMaxPassengers(dataFrom.getMaxPassengers());
        toSet.setModel(dataFrom.getModel());
        toSet.setName(dataFrom.getName());
        toSet.setNumberOfBags(dataFrom.getNumberOfBags());
        toSet.setNumberOfDoors(dataFrom.getNumberOfDoors());
        toSet.setRegistrationNumber(dataFrom.getRegistrationNumber());
        toSet.setType(carTypeRepository.findById(dataFrom.getType().getId()).get());
    }

}
