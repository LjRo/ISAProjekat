package isa.projekat.Projekat.repository;

import isa.projekat.Projekat.model.airline.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface OrderRepository extends JpaRepository<Order, Long> {
}
