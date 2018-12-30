package isa.projekat.Projekat.service.airline;

import isa.projekat.Projekat.model.airline.Airline;
import isa.projekat.Projekat.model.airline.AirlineData;
import isa.projekat.Projekat.model.airline.Flight;
import isa.projekat.Projekat.model.user.User;
import isa.projekat.Projekat.repository.AirlineRepository;
import isa.projekat.Projekat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirlineService {
    @Autowired
    private AirlineRepository airlineRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Airline> findAll(){
        return airlineRepository.findAll();
    }

    public Airline findById(Long id) { return airlineRepository.findById(id).get(); }

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

    public boolean addFlight(Flight fl, Long id, String username) {
        Airline target = findById(id);
        User admin = userRepository.findByUsername(username);

        if(target.getAdmins().contains(admin)) {

            target.getFlights().add(fl);
            return true;

        }

        return false;
    }


}
