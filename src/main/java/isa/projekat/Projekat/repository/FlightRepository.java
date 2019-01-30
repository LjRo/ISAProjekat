package isa.projekat.Projekat.repository;

import isa.projekat.Projekat.model.airline.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface FlightRepository extends JpaRepository<Flight, Long> {

    //@Query("SELECT f FROM Flights WHERE f.start.city = :fr AND f.finish.city = :to")
    //List<Flight> findFlights(@Param("fr") String cityFrom, @Param("to") String cityTo);
}
