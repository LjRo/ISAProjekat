package isa.projekat.Projekat.repository;

import isa.projekat.Projekat.model.airline.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Query(value = "SELECT * From Flights f WHERE f.airline_id = ?1 AND YEAR(f.start_time) = YEAR(CURDATE())", nativeQuery = true)
    public List<Flight> getAllFlightsThisYear(Long id);
}
