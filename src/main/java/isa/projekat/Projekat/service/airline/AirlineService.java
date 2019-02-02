package isa.projekat.Projekat.service.airline;

import isa.projekat.Projekat.model.airline.*;
import isa.projekat.Projekat.model.rent_a_car.Location;
import isa.projekat.Projekat.model.user.User;
import isa.projekat.Projekat.repository.AirlineRepository;
import isa.projekat.Projekat.repository.FlightRepository;
import isa.projekat.Projekat.repository.LocationRepository;
import isa.projekat.Projekat.repository.UserRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    private LocationRepository locationRepository;

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

       /* Airline target = findById(ad.getId());
        User admin = userRepository.findByUsername(username);

        if(target == null || ad.getAddress() == null || ad.getDescription() == "" || ad.getName() == ""){
            return false;
        }

        if(target.getAdmins().contains(admin)) {
            Optional<Location> optionalLocation = locationRepository.findById(ad.getAddress().getId());
            Location found  = (optionalLocation.isPresent())?optionalLocation.get():null;
            if(found==null)
                return false;
            found.setCountry(ad.getAddress().getCountry());
            found.setLatitude(ad.getAddress().getLatitude());
            found.setCity(ad.getAddress().getCity());
            found.setLongitude(ad.getAddress().getLongitude());
            found.setAddressName(ad.getAddress().getAddressName());


            target.setDescription(ad.getDescription());
            target.setName(ad.getName());
            return true;

        }*/

        return false;

    }

    public boolean addAirline(Airline ad) {


        if(ad.getAddress() == null || ad.getDescription() == "" || ad.getName() == ""){
            return false;
        }

        int size = ad.getDestinations().size();
        List<Location> locationList = new ArrayList<>();
        for(int i=0;i<size;++i){
            Location location = new Location();
            location.setCountry(ad.getDestinations().get(i).getCountry());
            location.setLatitude(ad.getDestinations().get(i).getLatitude());
            location.setCity(ad.getDestinations().get(i).getCity());
            location.setLongitude(ad.getDestinations().get(i).getLongitude());
            location.setAddressName(ad.getDestinations().get(i).getAddressName());
            locationList.add(location);
            locationRepository.save(location);
        }

        Airline target = new Airline();
        target.setDestinations(locationList);
        target.setAddress(ad.getAddress());
        target.setDescription(ad.getDescription());
        target.setName(ad.getName());

        airlineRepository.save(target);

        return true;
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

    public List<QuickTicketData> findAllActiveFlightsWithQuickReservation(Long id) {
        List<Flight> allFlights = airlineRepository.findById(id).get().getFlights();
        ArrayList<QuickTicketData> result = new ArrayList<>();
        DateTime current = new DateTime();
        for(Flight fl : allFlights) {
            if(current.isBefore(fl.getStartTime().getTime())) {
                for(Seat st : fl.getSeats()) {
                    if(st.isQuick() && !st.isTaken()) {
                        QuickTicketData qTicket = new QuickTicketData();
                        qTicket.setId(fl.getId());
                        qTicket.setStartTime(fl.getStartTime());
                        qTicket.setLandTime(fl.getLandTime());
                        qTicket.setDuration(fl.getDuration());
                        qTicket.setDistance(fl.getDistance());
                        qTicket.setNumberOfStops(fl.getNumberOfStops());
                        qTicket.setC_column(st.getC_column());
                        qTicket.setC_row(st.getC_row());
                        qTicket.setPrice(st.getPrice());
                        qTicket.setStart(fl.getStart());
                        qTicket.setFinish(fl.getFinish());
                        qTicket.setSeatId(st.getId());
                        result.add(qTicket);
                    }
                }
            }
        }
        return result;
    }
}
