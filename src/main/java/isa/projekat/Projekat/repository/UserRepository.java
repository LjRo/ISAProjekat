package isa.projekat.Projekat.repository;

import isa.projekat.Projekat.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query(value = "SELECT * From Users u where u.type = 0", nativeQuery = true)
    public List<User> getAllNormalUsers();
}

