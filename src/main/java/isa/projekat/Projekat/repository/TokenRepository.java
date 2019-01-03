package isa.projekat.Projekat.repository;

import isa.projekat.Projekat.model.user.User;
import isa.projekat.Projekat.model.user.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface TokenRepository extends JpaRepository<VerificationToken,Long> {
    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);
}
