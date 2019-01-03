package isa.projekat.Projekat.service.airline;

import isa.projekat.Projekat.model.airline.Flight;
import isa.projekat.Projekat.model.airline.SeatData;
import isa.projekat.Projekat.repository.AirlineRepository;
import isa.projekat.Projekat.repository.FlightRepository;
import isa.projekat.Projekat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class FlightService {
    @Autowired
    private AirlineRepository airlineRepository;
    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private UserRepository userRepository;
    @PersistenceContext
    private EntityManager entityManager;


    public List<Flight> findAll(){
        return flightRepository.findAll();
    }

    public Flight findById(Long id) { return flightRepository.findById(id).get(); }


    public SeatData findSeatDataById(Long id) {
        SeatData result = new SeatData();
        Flight target = findById(id);

        result.setSegments(target.getSegments());
        result.setColumns(target.getC_columns());
        result.setRows(target.getC_rows());
        result.setSeats(target.getSeats());

        return result;
    }
}
