package isa.projekat.Projekat.config;

import isa.projekat.Projekat.security.TokenUtils;
import isa.projekat.Projekat.security.auth.RestAuthenticationEntryPoint;
import isa.projekat.Projekat.security.auth.TokenAuthenticationFilter;
import isa.projekat.Projekat.service.user_auth.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	// Implementacija PasswordEncoder-a koriscenjem BCrypt hashing funkcije.
	// BCrypt po defalt-u radi 10 rundi hesiranja prosledjene vrednosti.
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private CustomUserDetailsService jwtUserDetailsService;

	// Neautorizovani pristup zastcenim resursima
	@Autowired
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	// Definisemo nacin autentifikacije
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Autowired
	TokenUtils tokenUtils;

	// Definisemo prava pristupa odredjenim URL-ovima
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			// komunikacija izmedju klijenta i servera je stateless
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			
			// za neautorizovane zahteve posalji 401 gresku
			.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()
			
			// svim korisnicima dopusti da pristupe putanjama /auth/** i /h2-console/**
			.authorizeRequests()
			.antMatchers("/auth/**").permitAll()
			.antMatchers("/h2-console/**").permitAll()
			.antMatchers("/assets/img/**").permitAll()
			.antMatchers("/api/office/all**").permitAll()
			.antMatchers("/api/cartypes").permitAll()
			.antMatchers("/api/cars/**/availablePrice**").permitAll()
				//.antMatchers("/**/**").permitAll()

				// svaki zahtev mora biti autorizovan
			.anyRequest().authenticated().and()
			
			// presretni svaki zahtev filterom
			.addFilterBefore(new TokenAuthenticationFilter(tokenUtils, jwtUserDetailsService), BasicAuthenticationFilter.class);

		http.csrf().disable();
	}

	// Generalna bezbednost aplikacije
	@Override
	public void configure(WebSecurity web) throws Exception {
		// TokenAuthenticationFilter ce ignorisati sve ispod navedene putanje
		web.ignoring().antMatchers(HttpMethod.POST, "/auth/login","/auth/refresh","/auth/loginToken");
		web.ignoring().antMatchers(HttpMethod.GET, "/", "/webjars/**", "/*.html", "/favicon.ico", "/**/*.html", "/**/*.css", "/**/*.js");
		web.ignoring().antMatchers(HttpMethod.GET, "/api/cars/findAll**", "/api/airline/findAll" , "/api/hotel/findAll" , "/api/rentacar/findAll","/api/rooms/findAll**", "/api/floor/findById**" , "/api/floor/findAllByHotelId**", "/api/rooms/findById**","/api/hotel/*/PriceLists");
		web.ignoring().antMatchers(HttpMethod.GET,"*.css");
		web.ignoring().antMatchers(HttpMethod.GET, "/api/hotel/findById=**", "/api/rentacar/findById=**" , "/api/airline/findById=**" );
		web.ignoring().antMatchers(HttpMethod.GET, "/api/office/find**", "/api/cars/findByIdAll**" , "/api/rooms/findByIdAll**" , "/api/hotel/*/roomTypes" );
		web.ignoring().antMatchers(HttpMethod.GET, "/api/airline/{id}/flights","/api/airline/{id}/profile","/api/airline/{id}/destinations","/api/user/{id}/friends","/api/user/{id}/profile" );
	}
}