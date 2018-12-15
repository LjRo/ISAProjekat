package isa.projekat.Projekat.repository;

import isa.projekat.Projekat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findOne(Long id);
}

