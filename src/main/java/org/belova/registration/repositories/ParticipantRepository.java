package org.belova.registration.repositories;

import org.belova.registration.models.Event;
import org.belova.registration.models.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    List<Participant> findParticipantByEmail(String email);
    List<Participant> findParticipantByToken(String token);
    List<Participant> findParticipantByEvent(Event event);
}
