package isa.projekat.Projekat.service.user_auth;

import isa.projekat.Projekat.model.user.User;
import isa.projekat.Projekat.model.user.UserData;
import isa.projekat.Projekat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@PersistenceContext
	private EntityManager entityManager;

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

	@Transactional
	public boolean updateUserData(UserData ud) {

		User target = findByUsername(ud.getEmail());

		if(target == null || ud.getFirstName() == null || ud.getLastName() == null || ud.getAddress() == null || ud.getEmail() == null) {
			return false;
		}

		target.setUsername(ud.getEmail());
		target.setFirstName(ud.getFirstName());
		target.setLastName(ud.getLastName());
		target.setAddress(ud.getAddress());
		target.setCity(ud.getCity());
		target.setPhoneNumber(ud.getPhoneNumber());


		if(ud.getPassword() != null && ud.getPassword() != "") {
			target.setPassword(passwordEncoder.encode(ud.getPassword()));
		}

		entityManager.persist(target);
		return true;
	}

	public List<UserData> findAllFriends(String email) throws AccessDeniedException {
		List<User> fList = userRepository.findByUsername(email).getFriends();
        return getUserDataFromList(fList);
	}

    public List<UserData> findAllFriendsById(Long id) throws AccessDeniedException {
        List<User> fList = userRepository.findById(id).get().getFriends();
        return getUserDataFromList(fList);
    }

	public List<UserData> findAllFriendRequests(String email) throws AccessDeniedException {
		List<User> fList = userRepository.findByUsername(email).getFriendRequests();
        return getUserDataFromList(fList);
	}

	private List<UserData> getUserDataFromList(List<User> uList) {
        ArrayList<UserData> result = new ArrayList<>();
        for(User u : uList) {
            UserData ud = new UserData();
            ud.setFirstName(u.getFirstName());
            ud.setLastName(u.getLastName());
            ud.setId(u.getId());
            result.add(ud);
        }
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

	public boolean addFriendRequest(String user, Long id) {
		User firstUser = findByUsername(user);
		User friendUser = findById(id);
		if(friendUser != null && firstUser != null && !firstUser.equals(friendUser) && !friendUser.getFriendRequests().contains(firstUser) && !friendUser.getFriends().contains(firstUser) && !firstUser.getFriendRequests().contains(friendUser)) {
			friendUser.getFriendRequests().add(firstUser);
			userRepository.save(friendUser);
			return true;
		}

		return false;
	}

	public boolean confirmRequest(String user, Long id) {
		User firstUser = findByUsername(user);
		User friendUser = findById(id);

		if(friendUser != null && firstUser != null) {
			if(firstUser.getFriendRequests().contains(friendUser)) {
				firstUser.getFriendRequests().remove(friendUser);
				firstUser.getFriends().add(friendUser);
				friendUser.getFriends().add(firstUser);
                userRepository.save(friendUser);
                userRepository.save(firstUser);
                return true;
			}
		}

		return false;
	}

	public boolean denyRequest(String user, Long id) {
		User firstUser = findByUsername(user);
		User friendUser = findById(id);

		if(friendUser != null && firstUser != null) {
			if(firstUser.getFriendRequests().contains(friendUser)) {
				firstUser.getFriendRequests().remove(friendUser);
                userRepository.save(firstUser);
				return true;
			}
		}
		return false;
	}

	public boolean removeFriend(String user, Long id) {
		User firstUser = findByUsername(user);
		User friendUser = findById(id);

		if(friendUser != null && firstUser != null) {
			if(firstUser.getFriends().contains(friendUser)) {
				firstUser.getFriends().remove(friendUser);
				friendUser.getFriends().remove(firstUser);
                userRepository.save(friendUser);
                userRepository.save(firstUser);
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

		result.setEmail(u.getUsername());
		result.setAddress(u.getAddress());
		result.setFirstName(u.getFirstName());
		result.setLastName(u.getLastName());
		result.setCity(u.getCity());
		result.setPhoneNumber(u.getPhoneNumber());
		result.setPassword("");

		return result;

	}

    public String getFriendStatus(String email, Long targetId) {
	    User reqUser = findByUsername(email);
	    User target = findById(targetId);

	    if(reqUser.getFriends().contains(target)) {
	        return "FRIEND";
        }

	    if(target.getFriendRequests().contains(reqUser)) {
	        return "REQUEST_SENT";
        }

	    if(reqUser.getFriendRequests().contains(target)) {
	        return "REQUEST_RECEIVED";
        }

	    if(target.equals(reqUser)) {
	        return "IGNORE";
        }
	    return "NOT_FRIEND";
    }

    public UserData getUserData(Long id) {
	    UserData result = new UserData();
	    User target = findById(id);

        result.setLastName(target.getLastName());
        result.setFirstName(target.getFirstName());
        result.setAddress(target.getAddress());

        return result;
    }
}
