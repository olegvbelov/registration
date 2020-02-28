package org.belova.registration.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "distance", schema = "registration")
public class Distance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "distanceName")
    private String distanceName;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "participant")
    private Set<Participant> participants;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event")
    private Event event;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "ageGroups")
    private Set<AgeGroup> ageGroups;

    public Distance(String distanceName, Set<Participant> participants, Event event, Set<AgeGroup> ageGroups) {
        this.distanceName = distanceName;
        this.participants = participants;
        this.event = event;
        this.ageGroups = ageGroups;
    }

    public Distance() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDistanceName() {
        return distanceName;
    }

    public void setDistanceName(String distanceName) {
        this.distanceName = distanceName;
    }

    public Set<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<Participant> participants) {
        this.participants = participants;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Set<AgeGroup> getAgeGroups() {
        return ageGroups;
    }

    public void setAgeGroups(Set<AgeGroup> ageGroups) {
        this.ageGroups = ageGroups;
    }
    public void addAgeGroup(AgeGroup ageGroup) {
        if(ageGroups == null) {
            ageGroups = new HashSet<AgeGroup>();
        }
        ageGroups.add(ageGroup);
    }
    public Participant addParticipant(Participant participant) {
        if(participants == null) {
            participants = new HashSet<Participant>();
        }
        participants.add(participant);
        return participant;
    }
}
