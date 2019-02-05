package isa.projekat.Projekat.repository;

import isa.projekat.Projekat.model.airline.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Query(value = "SELECT * From Flights f WHERE f.airline_id = ?1 AND date_part('year',f.start_time) = date_part('year',current_date)", nativeQuery = true)
    public List<Flight> getAllFlightsThisYear(Long id);

    @Query(value = "SELECT * From Flights f WHERE f.airline_id = ?1 AND  date_part('year',f.start_time) = date_part('year',current_date) AND date_part('month',f.start_time) = date_part('month',current_date))", nativeQuery = true)
    public List<Flight> getAllFlightsThisMonth(Long id);

    @Query(value = "SELECT * From Flights f WHERE f.airline_id = ?1 AND date_part('year',f.start_time) = date_part('year',current_date) AND date_part('week',f.start_time) = date_part('week',current_date))", nativeQuery = true)
    public List<Flight> getAllFlightsThisWeek(Long id);

    @Query(value = "SELECT f.start_time as startTime, r.total_cost as price From Flights f" +
            " LEFT JOIN flights_seats fs ON f.id = fs.flight_id" +
            " LEFT JOIN Seats s ON fs.seats_id = s.id" +
            " LEFT JOIN Reservations r ON s.reservation_id = r.id" +
            " WHERE f.airline_id = ?1" +
            " AND f.start_time BETWEEN ?2 AND ?3" +
            " AND s.taken = TRUE", nativeQuery = true)
    public List<Object[]> getProfitFromRange(Long id, LocalDate sDate, LocalDate eDate);

}
