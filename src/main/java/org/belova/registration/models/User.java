package org.belova.registration.models;

import org.jboss.logging.Field;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Indexed;

import javax.annotation.Generated;
import javax.persistence.*;
import java.lang.annotation.Documented;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "user", schema = "registration")
public class User{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "email")
    private String email;
    @Column(name = "name")
    private String name;
    @Column(name = "gender")
    private String gender;
    @Column(name = "country")
    private String country;
    @Column(name = "city")
    private String city;
    @Column(name = "dateBith")
    private LocalDate dateBith;
    @Column(name = "category")
    private String category;
    @Column(name = "club")
    private String club;
    @Column(name = "possword")
    private String password;
    @Column(name = "user_role")
    private  String userRole;
    @Column(name = "points")
    private float points;

    public User() {
        this.email = "";
        this.name = "";
        this.gender = "male";
        this.country = "";
        this.city = "";
        this.category = "";
        this.club = "";
        this.password = "";
        this.userRole = "USER";
        this.points = 0;
    }

    public User(String email, String name, String gender, String country, String city, LocalDate dateBith, String category, String club, String password, String userRole) {
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.country = country;
        this.city = city;
        this.dateBith = dateBith;
        this.category = category;
        this.club = club;
        this.password = password;
        this.userRole = userRole;
        this.points = 0;
    }

    public float getPoints() {
        return points;
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

    public void setPoints(float points) {
        this.points = points;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateBith() {
        return dateBith;
    }

    public void setDateBith(LocalDate dateBith) {
        this.dateBith = dateBith;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }


}
