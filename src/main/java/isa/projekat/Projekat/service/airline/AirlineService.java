package isa.projekat.Projekat.service.airline;

import isa.projekat.Projekat.model.airline.*;
import isa.projekat.Projekat.model.rent_a_car.Location;
import isa.projekat.Projekat.model.user.User;
import isa.projekat.Projekat.repository.AirlineRepository;
import isa.projekat.Projekat.repository.FlightRepository;
import isa.projekat.Projekat.repository.UserRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Service
public class AirlineService {
    @Autowired
    private AirlineRepository airlineRepository;
    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private UserRepository userRepository;
    @PersistenceContext
    private EntityManager entityManager;


    public List<Airline> findAll(){
        return airlineRepository.findAll();
    }

    public Airline findById(Long id) { return airlineRepository.findById(id).get(); }

    public Location findLocationById(Long id, Long locId) {
        List<Location> locs = airlineRepository.findById(id).get().getDestinations();
        for (Location loc : locs) {
            if(loc.getId().equals(locId)) {
                return loc;
            }
        }
        return null;
    }

    public boolean updateAirlineData(AirlineData ad, String username) {

        Airline target = findById(ad.getId());
        User admin = userRepository.findByUsername(username);

        if(target == null || ad.getAddress() == "" || ad.getDescription() == "" || ad.getName() == ""){
            return false;
        }

        if(target.getAdmins().contains(admin)) {

            target.setAddress(ad.getAddress());
            target.setDescription(ad.getDescription());
            target.setName(ad.getName());
            return true;

        }

        return false;

    }

    public List<Location> findAirlineDestinations(Long id) {
        return airlineRepository.findById(id).get().getDestinations();
    }

    @Transactional
    public boolean addFlight(FlightData flightData, String email) {
        User admin = userRepository.findByUsername(email);

        if(admin.getAdministratedAirline() == null) {
            return false;
        }

        Airline target = admin.getAdministratedAirline();

        Flight fl = new Flight();
        fl.setStart(findLocationById(target.getId(),flightData.getStartID()));
        fl.setFinish(findLocationById(target.getId(),flightData.getDestID()));
        fl.setStartTime(flightData.getStartDate());
        fl.setLandTime(flightData.getFinishDate());
        fl.setDuration(flightData.getLength());
        fl.setDistance(flightData.getDistance());
        fl.setPrice(flightData.getPrice());
        fl.setSegments(flightData.getSegments());
        fl.setC_columns(flightData.getColumns());
        fl.setC_rows(flightData.getRows());
        fl.setNumberOfStops(flightData.getStopCount());
        fl.setAirline(target);

        ArrayList<Location> stops = new ArrayList<>();

        ArrayList<Seat> seats = new ArrayList<>();

        for(int j = 0; j < fl.getSegments()*fl.getC_columns(); j++) {
            for(int k = 0; k< fl.getC_rows(); k++) {
                Seat st = new Seat(k,j,fl.getPrice(),null,false,false);
                //entityManager.persist(st);
                seats.add(st);
            }
        }

        for(int i = 0; i < flightData.getStopCount(); i++) {
            stops.add(findLocationById(target.getId(),flightData.getStopIDs().get(i)));
        }
        fl.setStops(stops);
        fl.setSeats(seats);

        if(target.getAdmins().contains(admin)) {

            target.getFlights().add(fl);
            entityManager.persist(target);
            return true;

        }

        return false;
    }

    public List<Flight> findAllActiveFlights(Long id) {
        List<Flight> allFlights = airlineRepository.findById(id).get().getFlights();
        ArrayList<Flight> result = new ArrayList<>();
        DateTime current = new DateTime();
        for(Flight fl : allFlights) {
            if(current.isBefore(fl.getStartTime().getTime())) {
                result.add(fl);
            }
        }
        return result;
    }
}
