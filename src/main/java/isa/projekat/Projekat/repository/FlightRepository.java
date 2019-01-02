package isa.projekat.Projekat.repository;

import isa.projekat.Projekat.model.airline.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface FlightRepository extends JpaRepository<Flight, Long> {
}
