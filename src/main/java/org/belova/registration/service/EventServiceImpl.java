package org.belova.registration.service;

import org.belova.registration.models.AgeGroup;
import org.belova.registration.models.Distance;
import org.belova.registration.models.Event;
import org.belova.registration.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    DistanceService distanceService;

    @Autowired
    AgeGroupService ageGroupService;

    @Override
    public Event addEvent(Event event) {
        return eventRepository.saveAndFlush(event);
    }

    @Override
    public void deleteEvent(Event event) {
        for (Distance distance: event.getDistances()) {
            for (AgeGroup ageGroup: distance.getAgeGroups()) {
                ageGroupService.deleteAgeGroup(ageGroup);
            }
            distanceService.deleteDistance(distance);
        }
        eventRepository.delete(event);
    }

    @Override
    public List<Event> findEventByNameCompetition(String nameCompetition) {
        return eventRepository.findEventsByNameCompetition(nameCompetition);
    }

    @Override
    public List<Event> fildAll() {
            return eventRepository.findAll();
    }

    @Override
    public Set<Distance> findDistancesByNameCompetition(String nameCompetition) {
        return eventRepository.findEventsByNameCompetition(nameCompetition).get(0).getDistances();
    }

    @Override
    public Event findEventById(Long id) {
        return eventRepository.findEventsById(id);
    }
}
