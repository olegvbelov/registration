package org.belova.registration.form;

import org.belova.registration.models.AgeGroup;
import org.belova.registration.models.Distance;
import org.belova.registration.models.Event;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static javax.persistence.TemporalType.DATE;

public class EventForm {
    private Long id;
    @NotEmpty
    private String nameCompetition;
    @NotEmpty
    private String location;
    private String info;
    //@Temporal(TemporalType.TIMESTAMP)
    private String dateCompetition;
    @NotEmpty
    private String position;
    private Set<Distance> distances;
    private List<ParticipantForm> participants;

    public EventForm(Event event) {
        this.id = event.getId();
        this.location = event.getLocation();
        this.nameCompetition = event.getNameCompetition();
        this.position = event.getPosition();
        this.info = event.getInfo();
        dateFormatter("HH:mm dd-MM-yyyy", event.getDateCompetition());
    }

    public EventForm() {
    }

    public EventForm(@NotEmpty String nameCompetition, String info, @NotEmpty String location, String dateCompetition, @NotEmpty String position, Set<Distance> distances, List<ParticipantForm> participants) {
        this.nameCompetition = nameCompetition;
        this.info = info;
        this.location = location;
        this.dateCompetition = dateCompetition;
        this.position = position;
        this.distances = distances;
        this.participants = participants;
    }

    public String dateFormatter(String format, LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        dateCompetition = date.format(formatter);
        return dateCompetition;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDateCompetition() {
        return dateCompetition;
    }

    public void setDateCompetition(String dateCompetition) {
        this.dateCompetition = dateCompetition;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Set<Distance> getDistances() {
        return distances;
    }

    public void setDistances(Set<Distance> distances) {
        this.distances = distances;
    }

    public List<ParticipantForm> getParticipants() {
        return participants;
    }

    public void setParticipants(List<ParticipantForm> participants) {
        this.participants = participants;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
