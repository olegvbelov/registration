package org.belova.registration.models;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.spi.LocaleServiceProvider;

@Entity
@Table(name = "event", schema = "registration")
public class Event {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "nameCompetition", nullable = false)
    private String nameCompetition;
    @Column(name = "location", nullable = false)
    private String location;
    @Column(name = "dateCompetition", nullable = false)
    private LocalDateTime dateCompetition;
    @Column(name = "position")
    private String position;
    @Column(name = "info")
    private String info;
    @Column(name = "image")
    private String image;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "distances")
    private Set<Distance> distances;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "patricipants")
    private Set<Participant> participantes;

    public Event() {
        distances = new HashSet<>();
    }

    public Event(String nameCompetition, String location, LocalDateTime dateCompetition, String position, String info, String image) {
        this.nameCompetition = nameCompetition;
        this.location = location;
        this.dateCompetition = dateCompetition;
        this.position = position;
        this.info = info;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNameCompetition() {
        return nameCompetition;
    }

    public void setNameCompetition(String nameCompetition) {
        this.nameCompetition = nameCompetition;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getDateCompetition() {
        return dateCompetition;
    }

    public void setDateCompetition(LocalDateTime dateCompetition) {
        this.dateCompetition = dateCompetition;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Set<Participant> getPsrticipantes() {
        return participantes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Distance> getDistances() {
        return distances;
    }

    public void setDistances(Set<Distance> distances) {
        this.distances = distances;
    }

    public Set<Participant> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(Set<Participant> participantes) {
        this.participantes = participantes;
    }
    public Distance addDistance(Distance distance) {
        if(distances == null) {
            distances = new HashSet<>();
            distances.add(distance);
        }
        else {
            distances.add(distance);
        }
        return distance;
    }
    public Participant addParticipant(Participant participant) {
        if(participantes == null) {
            participantes = new HashSet<>();
            participantes.add(participant);
        }
        else {
            participantes.add(participant);
        }
        return participant;
    }

    public boolean findParticipant(String email) {
        for (Participant participant: participantes) {
            if(participant.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
