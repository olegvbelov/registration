package org.belova.registration.repositories;

import org.belova.registration.models.Distance;
import org.belova.registration.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DistanceRepository extends JpaRepository<Distance, Long> {
    List<Distance> findDistanceByDistanceName(String distanceName);
    List<Distance> findDistanceByDistanceNameAndEvent(String distanceName, Event event);
    List<Distance> findDistancesByEvent(Event event);
    Distance findDistancesById(Long id);
}
