package isa.projekat.Projekat.repository;

import isa.projekat.Projekat.model.Prices;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PriceRepository extends JpaRepository<Prices, Long> {

    public Optional<Prices> findByPriceName(String name);
}
