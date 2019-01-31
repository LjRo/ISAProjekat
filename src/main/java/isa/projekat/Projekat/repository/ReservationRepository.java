package isa.projekat.Projekat.repository;

import isa.projekat.Projekat.model.airline.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ReservationRepository extends JpaRepository<Reservation, Long> {


    @Query(value ="Select * from reservations r where r.user_id = ?1 ",nativeQuery = true)
    public List<Reservation> returnAllReservationByUser(Long userId);
}
