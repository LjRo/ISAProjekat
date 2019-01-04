package isa.projekat.Projekat.model.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import isa.projekat.Projekat.model.airline.Airline;
import isa.projekat.Projekat.model.hotel.Hotel;
import isa.projekat.Projekat.model.rent_a_car.RentACar;
import org.joda.time.DateTime;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "Users")
public class User implements UserDetails {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column
    private Integer type;
    //0 - Normal, 1 - Admin, 2 - Airline Admin, 3 - Hotel Admin, 4 - RentACar Admin

    // Email values will be stored in username
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String phoneNumber;


    @Column
    private String address;

    @Column(nullable = false)
    private String password;

    @ManyToMany
    private List<User> friends;

    @ManyToMany
    private List<User> friendRequests;

    @Column
    private boolean enabled = false;

    // Airline stuff
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonBackReference
    private Airline administratedAirline;

    // Hotel stuff
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonBackReference
    private Hotel administratedHotel;

    // RentACar stuff
    @OneToOne(cascade = CascadeType.PERSIST)
    @JsonBackReference
    private RentACar administratedRent;

    public Airline getAdministratedAirline() {
        return administratedAirline;
    }

    public void setAdministratedAirline(Airline administratedAirline) {
        this.administratedAirline = administratedAirline;
    }

public Hotel getAdministratedHotel() {
        return administratedHotel;
    }

    public void setAdministratedHotel(Hotel administratedHotel) {
        this.administratedHotel = administratedHotel;
    }

    public RentACar getAdministratedRent() {
        return administratedRent;
    }

    public void setAdministratedRent(RentACar administratedRent) {
        this.administratedRent = administratedRent;
    }

    //

    @Column
    private Timestamp lastPasswordResetDate;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    private List<Authority> authorities;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public List<User> getFriendRequests() {
        return friendRequests;
    }

    public void setFriendRequests(List<User> friendRequests) {
        this.friendRequests = friendRequests;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPassword(String password) {
        Timestamp now = new Timestamp(DateTime.now().getMillis());
        this.setLastPasswordResetDate( now );
        this.password = password;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }



    public Timestamp getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Timestamp lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


}
