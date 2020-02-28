package org.belova.registration.service;

import org.belova.registration.models.Distance;
import org.belova.registration.models.Event;

import java.util.List;
import java.util.Set;

public interface DistanceService {
    Distance addDistance(Distance distance);
    void deleteDistance(Distance distance);
    List<Distance> findDistanceByDistanceName(String distanceName);
    List<Distance> findAll();
    Set<Distance> findDistanceByNameCompetition(String nameCompetition);
    Distance findByDistanceNameAndEvent(String distanceName, Event event);
    List<Distance> findDistancesByEvent(Event event);
    Distance findDistanceById(Long id);
}
