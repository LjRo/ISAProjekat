package isa.projekat.Projekat.repository;

import isa.projekat.Projekat.model.airline.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
