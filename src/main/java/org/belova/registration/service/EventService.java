package org.belova.registration.service;

import org.belova.registration.models.Distance;
import org.belova.registration.models.Event;
import org.belova.registration.models.Participant;

import java.util.List;
import java.util.Set;

public interface EventService {
    Event addEvent(Event event);
    void deleteEvent(Event event);
    List<Event> findEventByNameCompetition(String nameCompetition);
    List<Event> fildAll();
    Set<Distance> findDistancesByNameCompetition(String nameCompetition);
    Event findEventById(Long id);
}
