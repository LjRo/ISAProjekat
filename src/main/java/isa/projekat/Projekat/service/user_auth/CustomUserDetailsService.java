package isa.projekat.Projekat.service.user_auth;

import isa.projekat.Projekat.model.airline.Airline;
import isa.projekat.Projekat.model.hotel.Hotel;
import isa.projekat.Projekat.model.rent_a_car.RentACar;
import isa.projekat.Projekat.model.user.Authority;
import isa.projekat.Projekat.model.user.User;
import isa.projekat.Projekat.model.user.UserData;
import isa.projekat.Projekat.model.user.VerificationToken;
import isa.projekat.Projekat.repository.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.*;


@Service
public class CustomUserDetailsService implements UserDetailsService {

	protected final Log LOGGER = LogFactory.getLog(getClass());

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private EmailService emailService;

	@Autowired
	private TokenRepository tokenRepository;

	@Autowired
	private AirlineRepository airlineRepository;

	@Autowired
	private HotelRepository hotelRepository;

	@Autowired
	private RentCarRepository rentCarRepository;

	// Funkcija koja na osnovu username-a iz baze vraca objekat User-a
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
		} else {
			return user;
		}
	}

	// Funkcija pomocu koje korisnik menja svoju lozinku
	public void changePassword(String oldPassword, String newPassword) {

		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		String username = currentUser.getName();

		if (authenticationManager != null) {
			LOGGER.debug("Re-authenticating user '" + username + "' for password change request.");

			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));
		} else {
			LOGGER.debug("No authentication manager set. can't change Password!");

			return;
		}

		LOGGER.debug("Changing password for user '" + username + "'");

		User user = (User) loadUserByUsername(username); //email
		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);

	}

	public void confirm(String token){
		VerificationToken verificationToken = tokenRepository.findByToken(token);

		if (verificationToken == null)
			throw  new BadCredentialsException("Token does not exist");
		else {

			if (verificationToken.getExpiryDate().after(new Date())) {
				System.out.println("Expiration date OK");

				User user = verificationToken.getUser();
				user.setEnabled(true);
				tokenRepository.delete(verificationToken);

			} else {
				throw new BadCredentialsException("Token expired");
			}
		}
	}
	@SuppressWarnings("Duplicates")
	public void register(UserData userData) {
		User user = new User();
		user.setType(0);
		Authority authority1 = new Authority("USER");
		Authority authority2 = new Authority("ROLE_USER");
		List<Authority> list = new ArrayList<>();
		list.add(authority1);
		list.add(authority2);
		user.setAuthorities(list);

		user.setUsername(userData.getEmail());
		user.setAddress(userData.getAddress());
		user.setCity(userData.getCity());
		user.setFirstName(userData.getFirstName());
		user.setLastName(userData.getLastName());
		user.setPassword(passwordEncoder.encode(userData.getPassword()));
		user.setPhoneNumber(userData.getPhoneNumber());

		userRepository.save(user);
		sendConfirmationEmail(user);
	}

	public Long registerAdmin(UserData userData,Long idCompany) {
		User user;
		String auth;
		switch (userData.getType())
		{
			case 1: auth = "ROLE_ADMIN";
				user = fillUserData(userData,auth);
				userRepository.save(user);
				break;
			case 2: auth = "ROLE_ADMIN_AIRLINE";
				Optional<Airline> optionalAirline = airlineRepository.findById(idCompany);
				if(!optionalAirline.isPresent())
					return Long.parseLong("-1");
				Airline airline = optionalAirline.get();
				user = fillUserData(userData,auth);
				user.setAdministratedAirline(airline);
				airline.getAdmins().add(user);
				userRepository.save(user);
				airlineRepository.save(airline);
				break;
			case 3: auth = "ROLE_ADMIN_HOTEL";
				Optional<Hotel> hotelOptional = hotelRepository.findById(idCompany);
				if(!hotelOptional.isPresent())
					return Long.parseLong("-1");
				Hotel hotel = hotelOptional.get();
				user = fillUserData(userData,auth);
				user.setAdministratedHotel(hotel);
				hotel.getAdmins().add(user);
				userRepository.save(user);
				hotelRepository.save(hotel);
				break;
			case 4: auth = "ROLE_ADMIN_RENT";
				Optional<RentACar> optionalRentACar = rentCarRepository.findById(idCompany);
				if(!optionalRentACar.isPresent())
					return Long.parseLong("-1");
				RentACar rent = optionalRentACar.get();
				user =fillUserData(userData,auth);
				user.setAdministratedRent(rent);
				rent.getAdmins().add(user);
				userRepository.save(user);
				rentCarRepository.save(rent);
				break;
			default:
				return Long.parseLong("-1");
		}
		sendConfirmationEmail(user);
		return userRepository.findByUsername(user.getUsername()).getId();
	}

	@SuppressWarnings("Duplicates")
	private User fillUserData(UserData userData,String auth){
		User user = new User();
		Authority authority = new Authority(auth);
		List<Authority> list = new ArrayList<>();
		user.setType(userData.getType());
		list.add(authority);
		user.setPasswordChanged(false);
		user.setUsername(userData.getEmail());
		user.setAuthorities(list);
		user.setAddress(userData.getAddress());
		user.setCity(userData.getCity());
		user.setFirstName(userData.getFirstName());
		user.setLastName(userData.getLastName());
		user.setPassword(passwordEncoder.encode(userData.getPassword()));
		user.setPhoneNumber(userData.getPhoneNumber());
		return user;
	}


	private void sendConfirmationEmail(User user) {
		String token = UUID.randomUUID().toString();
		VerificationToken verificationToken = createVerificationToken(user, token);
		try {
			emailService.sendEmailAsync(user, verificationToken);
		}catch (MessagingException e){
			System.out.println("Failed to send email");
		}
	}

	private VerificationToken createVerificationToken(User user, String token) {
		VerificationToken myToken = new VerificationToken(token, user);
		tokenRepository.save(myToken);
		return myToken;
	}







}
