package isa.projekat.Projekat.repository;

import isa.projekat.Projekat.model.airline.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface SeatRepository extends JpaRepository<Seat, Long> {
}
