package org.belova.registration.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Random;

@Entity
@Table(name = "participant", schema = "registration")
public class Participant {
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
    @Column(name = "confirmed")
    private boolean confirmed;
    @Column(name = "token")
    private String token;
    @Column(name = "result")
    private String result;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "distance")
    private Distance distance;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event")
    private Event event;


    public Participant() {
        this.email = "";
        this.name = "";
        this.gender = "male";
        this.country = "";
        this.city = "";
        this.category = "";
        this.club = "";
        this.confirmed = false;
        generateToken();
    }

    public Participant(String email, String name, String gender, String country, String city, LocalDate dateBith, String category, String club, boolean confirmed) {
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.country = country;
        this.city = city;
        this.dateBith = dateBith;
        this.category = category;
        this.club = club;
        this.confirmed = confirmed;
        generateToken();
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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

    public Distance getDistance() {
        return distance;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    private void generateToken() {
        String characters = "qwertyuiopasdfghjklzxcvbnm1234567890QWERTYUIOASDFGHJKLZXCVBNM";
        Random rnd = new Random();
        char[] text = new char[5];
        for (int i = 0; i < 5; i++)
        {
            text[i] = characters.charAt(rnd.nextInt(characters.length()));
        }
        token = String.copyValueOf(text);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
