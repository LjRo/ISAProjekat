package isa.projekat.Projekat.repository;

import isa.projekat.Projekat.model.airline.Airline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface AirlineRepository extends JpaRepository<Airline, Long> {

}
