package isa.projekat.Projekat.model.rent_a_car;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Locations")
public class Location implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "addressName")
    private String addressName;

    @Column (name = "country")
    private String country;

    @Column (name = "city")
    private String city;

    @Column(name="latitude", nullable = false)
    private double latitude;

    @Column(name="longitude", nullable = false)
    private double longitude;

    @Column (name="active")
    private Boolean active = true;

    public Location() {
        super();
        active = true;
    }

    public Location(String addressName, String country, String city, double latitude, double longitude) {
        this.addressName = addressName;
        this.country = country;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
