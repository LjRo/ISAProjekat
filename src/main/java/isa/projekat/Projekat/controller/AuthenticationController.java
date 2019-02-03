package isa.projekat.Projekat.controller;

import isa.projekat.Projekat.model.user.User;
import isa.projekat.Projekat.model.user.UserData;
import isa.projekat.Projekat.model.user.UserTokenState;
import isa.projekat.Projekat.model.user.VerificationToken;
import isa.projekat.Projekat.security.TokenUtils;
import isa.projekat.Projekat.security.auth.JwtAuthenticationRequest;
import isa.projekat.Projekat.service.user_auth.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

//import isa.projekat.Projekat.utils.DeviceProvider;
//import org.springframework.mobile.device.Device;

//Kontroler zaduzen za autentifikaciju korisnika
@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	//@Autowired
	//private DeviceProvider deviceProvider;

	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
                                                       HttpServletResponse response) throws AuthenticationException, IOException {

		final Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(
						authenticationRequest.getUsername(),
						authenticationRequest.getPassword()));

		// Ubaci username + password u kontext
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Kreiraj token
		User user = (User) authentication.getPrincipal();
		String jwt = tokenUtils.generateToken(user.getUsername());
		int expiresIn = tokenUtils.getExpiredIn();

		// Vrati token kao odgovor na uspesno autentifikaciju
		return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
	}


	@RequestMapping(value = "/loginToken", method = RequestMethod.POST)
	public ResponseEntity<?> loginWithToken(HttpServletRequest request) throws AuthenticationException, ParseException {
		String token = tokenUtils.getToken(request);
		String username = this.tokenUtils.getUsernameFromToken(token);
		User user = (User) this.userDetailsService.loadUserByUsername(username);
		//Date expiresIn = tokenUtils.getExpirationDateFromToken(token);

		final Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(
						user.getUsername(),
						user.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		Long expireIn = (long)tokenUtils.getExpiredIn();

		return ResponseEntity.ok(new UserTokenState(token,expireIn));

	}


	@RequestMapping(value = "/refresh", method = RequestMethod.POST)
	public ResponseEntity<?> refreshAuthenticationToken(HttpServletRequest request) {

		String token = tokenUtils.getToken(request);
		String username = this.tokenUtils.getUsernameFromToken(token);
	    User user = (User) this.userDetailsService.loadUserByUsername(username);

		//Device device = deviceProvider.getCurrentDevice(request);

		if (this.tokenUtils.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
			String refreshedToken = tokenUtils.refreshToken(token);
			int expiresIn = tokenUtils.getExpiredIn();

			return ResponseEntity.ok(new UserTokenState(refreshedToken, expiresIn));
		} else {
			UserTokenState userTokenState = new UserTokenState();
			return ResponseEntity.badRequest().body(userTokenState);
		}
	}

	@RequestMapping(value = "/change-password", method = RequestMethod.POST)
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> changePassword(@RequestBody PasswordChanger passwordChanger) {
		userDetailsService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);
		
		Map<String, String> result = new HashMap<>();
		result.put("result", "success");
		return ResponseEntity.accepted().body(result);
	}


	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> register(@RequestBody UserData userData) {


		try {
			userDetailsService.loadUserByUsername(userData.getEmail());
			Map<String, String> result = new HashMap<>();
			result.put("result", "User already in database");
			return ResponseEntity.badRequest().body(result);
		}catch (UsernameNotFoundException e){
			userDetailsService.register(userData);
		}


		Map<String, String> result = new HashMap<>();
		result.put("result", "success");
		return ResponseEntity.accepted().body(result);
	}


	@RequestMapping(value = "/registerAdmin", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> registerAdmins(@RequestBody UserData userData) {
		try {
			Map<String, String> result = new HashMap<>();
			userDetailsService.loadUserByUsername(userData.getEmail());
			result.put("result", "User already in database");
			return ResponseEntity.badRequest().body(result);
		}catch (UsernameNotFoundException e){
			userDetailsService.registerAdmin(userData);
		}

		Map<String, String> result = new HashMap<>();
		result.put("result", "Successfully added");
		return ResponseEntity.accepted().body(result);
	}



	@RequestMapping(value = "/confirm", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> confirm(@RequestBody VerificationToken verificationToken){
		//producer.sendMessageTo(topic, message);
		System.out.println("TOKEN:" + verificationToken.getToken());
		userDetailsService.confirm(verificationToken.getToken());

		Map<String, String> result = new HashMap<>();
		result.put("result", "success");
		return ResponseEntity.accepted().body(result);
	}


	static class PasswordChanger {
		public String oldPassword;
		public String newPassword;
	}
}