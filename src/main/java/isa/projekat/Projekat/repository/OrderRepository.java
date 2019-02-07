package isa.projekat.Projekat.repository;

import isa.projekat.Projekat.model.airline.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByPlacedOrderIdAndFinishedIsFalse(Long id);
    List<Order> findAllByPlacedOrderId(Long id);


    @Query(value = "SELECT o.* FROM orders o LEFT OUTER JOIN orders_reservations os  ON o.id = os.order_id LEFT OUTER JOIN reservations r ON os.reservations_id = r.id" +
            " LEFT OUTER JOIN flights f ON r.flight_id = f.id WHERE" +
            " f.start_time >=?2 AND r.user_id = ?1", nativeQuery = true)
    List<Order> findAllFutureOrdersByUserIdAndDate(Long userId, String todayDate);

}
