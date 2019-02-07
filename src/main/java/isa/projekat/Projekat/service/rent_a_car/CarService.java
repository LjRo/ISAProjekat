package isa.projekat.Projekat.service.rent_a_car;

import isa.projekat.Projekat.model.airline.Reservation;
import isa.projekat.Projekat.model.rent_a_car.Cars;
import isa.projekat.Projekat.model.rent_a_car.RentACar;
import isa.projekat.Projekat.model.rent_a_car.RentReservation;
import isa.projekat.Projekat.model.user.User;
import isa.projekat.Projekat.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private RentCarRepository rentCarRepository;

    @Autowired
    private CarTypeRepository carTypeRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RentReservationRepository rentReservationRepository;



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

        if (!everythingPresent(optionalRentACar, optionalCars, user, true))
            return false;

        Cars fromDatabase = optionalCars.get();

        setCar(fromDatabase,cars);

        carRepository.save(fromDatabase);
        return  true;
    }


    public boolean removeCar(Long id, Long idrent, User user){

        Optional<RentACar> optionalRentACar = rentCarRepository.findById(idrent);
        Optional<Cars> optionalCars = carRepository.findById(id);

        if (!everythingPresent(optionalRentACar, optionalCars, user, true))
            return false;

        Cars toRemove = optionalCars.get();

        optionalRentACar.get().getCars().remove(toRemove);
        carRepository.delete(toRemove);
        return true;
    }


    public boolean reserveCar(Long id, Long idrent, User user, RentReservation rentReservation, Long idAirlineReservation){

        Optional<RentACar> optionalRentACar = rentCarRepository.findById(idrent);
        Optional<Cars> optionalCars = carRepository.findById(id);

        if (!everythingPresent(optionalRentACar, optionalCars, user, false))
            return false;

        RentReservation newReservation = new RentReservation();
        Cars rentedCar = optionalCars.get();

        Reservation reservation = reservationRepository.getOne(idAirlineReservation);

        // not same user
        if (!reservation.getUser().equals(user))
            return false;

        // max 1 per airline reservation, can be removed later on ...
        List<RentReservation> list = rentReservationRepository.findAllByUserId(user.getId());
        for (RentReservation r: list) {
            if (r.getAirlineReservation() != null){
                if (r.getAirlineReservation().getId() == idAirlineReservation){
                    return false;
                }
            }

        }



        newReservation.setAirlineReservation(reservation);
        newReservation.setEndDate(rentReservation.getEndDate());
        newReservation.setEndLocation(rentReservation.getEndLocation());
        newReservation.setStartDate(rentReservation.getStartDate());
        newReservation.setStartLocation(rentReservation.getStartLocation());
        newReservation.setNumberOfPeople(rentReservation.getNumberOfPeople());
        newReservation.setUser(user);
        newReservation.setRentedCar(newReservation.getRentedCar());
        newReservation.setFastReservation(false);
        newReservation.setRentedCar(rentedCar);

        // Price without % discount
        newReservation.setPrice(BigDecimal.valueOf(getDifferenceDays(rentReservation.getStartDate(),rentReservation.getEndDate())).multiply(rentedCar.getDailyPrice()));

        rentReservationRepository.save(newReservation);

        return true;
    }


    private static long getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }


    public boolean quickReserve(Long idReservationAirline, Long idReservation, User user){

       Reservation res = reservationRepository.getOne(idReservationAirline);
       RentReservation rentReservation = rentReservationRepository.getOne(idReservation);

       if (res == null || rentReservation == null||!res.getUser().equals(user))
           return  false;

       rentReservation.setAirlineReservation(res);
       rentReservation.setUser(user);


        return true;
    }


    public List<RentReservation> listQuickReservations(Long idrent){
        String date = java.time.LocalDate.now().toString();
        return rentReservationRepository.listQuick(idrent, date);
    }


    public Page<Cars> listAvailableWithDateOnly(Long idrent, PageRequest pageRequest, Long carType, String start, String end, Integer passengers){
        return listAvailableWithDate(idrent,pageRequest, carType, BigDecimal.valueOf(0),BigDecimal.valueOf(200000), start, end, passengers);
    }


    public Boolean checkEdibility(Long idrent, Long id){

        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");

        String strDate = sm.format(new Date());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, 10);
        String endDate = sm.format(calendar.getTime());

        if (carRepository.isTheCarRentedInTheFuture(idrent,id,strDate, endDate) != null){
            return true;
        }else {
            return false;
        }

    }



    public  Page<Cars> listAvailableWithDate(Long idrent, PageRequest pageRequest, Long carType, BigDecimal min, BigDecimal max, String start, String end, Integer passengers){
        return carRepository.filterCars(carType,passengers,start,end,idrent,min,max,pageRequest);
        }




    // Checks that the Rent a car object and Car object is present as well as the does the person have privileges
    public Boolean everythingPresent(Optional<RentACar> optionalRentACar, Optional<Cars> optionalCars, User user, boolean neededAdmin){
        if (!optionalRentACar.isPresent() || !optionalCars.isPresent()){
            return  false;
        }
        if (neededAdmin)
            if (!optionalRentACar.get().getAdmins().contains(user))
                return  false;
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
