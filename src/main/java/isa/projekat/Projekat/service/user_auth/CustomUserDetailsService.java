package isa.projekat.Projekat.service.user_auth;

import isa.projekat.Projekat.model.user.Authority;
import isa.projekat.Projekat.model.user.User;
import isa.projekat.Projekat.model.user.UserData;
import isa.projekat.Projekat.model.user.VerificationToken;
import isa.projekat.Projekat.repository.TokenRepository;
import isa.projekat.Projekat.repository.UserRepository;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


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
		Authority authority = new Authority("USER");
		List<Authority> list = new ArrayList<>();
		list.add(authority);
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

	@SuppressWarnings("Duplicates")
	public Long registerAdmin(UserData userData) {
		User user = new User();
		user.setType(userData.getType());
		String auth;
		switch (userData.getType())
		{
			case 1: auth = "ROLE_ADMIN";
				break;
			case 2: auth = "ROLE_ADMIN_AIRLINE";
				break;
			case 3: auth = "ROLE_ADMIN_HOTEL";
				break;
			case 4: auth = "ROLE_ADMIN_RENT";
				break;
			default:
				return Long.parseLong("-1");
		}
		Authority authority = new Authority(auth);
		List<Authority> list = new ArrayList<>();
		list.add(authority);
		user.setUsername(userData.getEmail());
		user.setAuthorities(list);
		user.setAddress(userData.getAddress());
		user.setCity(userData.getCity());
		user.setFirstName(userData.getFirstName());
		user.setLastName(userData.getLastName());
		user.setPassword(passwordEncoder.encode(userData.getPassword()));
		user.setPhoneNumber(userData.getPhoneNumber());
		userRepository.save(user);
		sendConfirmationEmail(user);
		return userRepository.findByUsername(user.getUsername()).getId();
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
