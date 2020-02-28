package org.belova.registration.service;

import org.belova.registration.models.Event;
import org.belova.registration.models.Participant;

import java.util.List;

public interface ParticipantService {
    Participant addParticipant(Participant participant);
    void deleteParticipant(Participant participant);
    List<Participant> findAll();
    Participant findByEmail(String email);
    Participant findByToken(String token);
    List<Participant> findByEvent(Event event);
}
