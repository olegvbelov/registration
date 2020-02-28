package org.belova.registration.form;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

import static javax.persistence.TemporalType.DATE;

public class ParticipantForm {
    private Long id;
    @NotEmpty
    private String email;
    @NotEmpty
    private String name;
    @NotEmpty
    private String gender;
    @NotEmpty
    private String country;
    @NotEmpty
    private String city;
    @NotEmpty
    private String dateBith;
    private String category;
    private String club;

    public ParticipantForm() {
    }

    public ParticipantForm(Long id, @NotEmpty String email, @NotEmpty String name, @NotEmpty String gender,@NotEmpty String country, @NotEmpty String city, @NotEmpty String dateBith, String category, String club) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.country = country;
        this.city = city;
        this.dateBith = dateBith;
        this.category = category;
        this.club = club;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getDateBith() {
        return dateBith;
    }

    public void setDateBith(String dateBith) {
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
}

