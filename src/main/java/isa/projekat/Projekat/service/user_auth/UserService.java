package isa.projekat.Projekat.service.user_auth;

import isa.projekat.Projekat.model.user.User;
import isa.projekat.Projekat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User findByUsername(String username) throws UsernameNotFoundException {
		User u = userRepository.findByUsername(username);
		return u;
	}

	public User findById(Long id) throws AccessDeniedException {
		Optional<User> u = this.userRepository.findById(id);
		if (u.isPresent()) {
			return u.get();
		} else {
			return null;
		}
	}

	public List<User> findAll() throws AccessDeniedException {
		List<User> result = userRepository.findAll();
		return result;
	}
}
