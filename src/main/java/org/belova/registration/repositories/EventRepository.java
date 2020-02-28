package org.belova.registration.repositories;

import org.belova.registration.models.Event;
import org.belova.registration.models.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findEventsByNameCompetition(String nameCompetition);
    Event findEventsById(Long id);
}
