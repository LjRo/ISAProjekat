package isa.projekat.Projekat.service.user_auth;

import isa.projekat.Projekat.model.user.User;
import isa.projekat.Projekat.model.user.UserData;
import isa.projekat.Projekat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

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

	public boolean updateUserData(UserData ud) {

		User target = findByUsername(ud.getEmail());
		if(target == null || ud.getFirstName() == "" || ud.getLastName() == "" || ud.getAddress() == "" || ud.getUsername() == "") {
			return false;
		}

		target.setUsername(ud.getUsername());
		target.setFirstName(ud.getFirstName());
		target.setLastName(ud.getLastName());
		target.setAddress(ud.getAddress());
		target.setCity(ud.getCity());
		target.setPhoneNumber(ud.getPhoneNumber());


		if(ud.getPassword() != "") {
			target.setPassword(passwordEncoder.encode(ud.getPassword()));
		}

		return true;
	}

	public List<User> findAllFriends(String email) throws AccessDeniedException {
		List<User> result = userRepository.findByUsername(email).getFriends();
		return result;
	}

	public List<User> findAllFriendRequests(String email) throws AccessDeniedException {
		List<User> result = userRepository.findByUsername(email).getFriendRequests();
		return result;
	}

	public List<User> findSpecificFriends(String email, String searchString) throws AccessDeniedException {
		List<User> result =  new ArrayList<>();
		List<User> friendsList = userRepository.findByUsername(email).getFriends();
		String[] tokens = searchString.split(" ");

		for(String tok : tokens) {
			for(User u : friendsList) {
				if(u.getFirstName().contains(tok) || u.getLastName().contains(tok)) {
					result.add(u);
				}
			}
		}
		return result;
	}

	public boolean addFriendRequest(String user, String friendUsername) {
		User firstUser = findByUsername(user);
		User friendUser = findByUsername(friendUsername);

		if(friendUser != null && firstUser != null && !firstUser.equals(friendUser) && !friendUser.getFriendRequests().contains(firstUser) && friendUser.getFriends().contains(firstUser) && !firstUser.getFriendRequests().contains(friendUser)) {
			friendUser.getFriendRequests().add(firstUser);
			return true;
		}

		return false;
	}

	public boolean confirmRequest(String user, String friendEmail) {
		User firstUser = findByUsername(user);
		User friendUser = findByUsername(friendEmail);

		if(friendUser != null && firstUser != null) {
			if(firstUser.getFriendRequests().contains(friendUser)) {
				firstUser.getFriendRequests().remove(friendUser);
				firstUser.getFriends().add(friendUser);
				friendUser.getFriends().add(firstUser);
				return true;
			}
		}

		return false;
	}

	public boolean denyRequest(String user, String friendEmail) {
		User firstUser = findByUsername(user);
		User friendUser = findByUsername(friendEmail);

		if(friendUser != null && firstUser != null) {
			if(firstUser.getFriendRequests().contains(friendUser)) {
				firstUser.getFriendRequests().remove(friendUser);
				return true;
			}
		}
		return false;
	}

	public boolean removeFriend(String user, String friendEmail) {
		User firstUser = findByUsername(user);
		User friendUser = findByUsername(friendEmail);

		if(friendUser != null && firstUser != null) {
			if(firstUser.getFriends().contains(friendUser)) {
				firstUser.getFriendRequests().remove(friendUser);
				friendUser.getFriendRequests().remove(firstUser);
				return true;
			}
		}
		return false;
	}

	public UserData getProfileData(String email) {
		User u = findByUsername(email);
		UserData result = new UserData();
		if(u == null) {
			return null;
		}

		result.setUsername(u.getUsername());
		result.setAddress(u.getAddress());
		result.setFirstName(u.getFirstName());
		result.setLastName(u.getLastName());
		result.setCity(u.getCity());
		result.setPhoneNumber(u.getPhoneNumber());
		result.setPassword("");

		return result;

	}


}