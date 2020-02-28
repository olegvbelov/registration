package org.belova.registration.service;

import org.belova.registration.models.Event;
import org.belova.registration.models.Participant;
import org.belova.registration.repositories.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParticipantServiceImpl implements ParticipantService{

    @Autowired
    ParticipantRepository participantRepository;

    @Override
    public Participant addParticipant(Participant participant) {
        return participantRepository.saveAndFlush(participant);
    }

    @Override
    public void deleteParticipant(Participant participant) {
        participantRepository.delete(participant);
    }

    @Override
    public List<Participant> findAll() {
        return participantRepository.findAll();
    }

    @Override
    public Participant findByEmail(String email) {
        List<Participant> participants = this.participantRepository.findParticipantByEmail(email);
        if(participants.isEmpty()) {
            return null;
        }
        return participants.get(0);
    }

    @Override
    public Participant findByToken(String token) {
        List<Participant> participants = this.participantRepository.findParticipantByToken(token);
        if(participants.isEmpty()) {
            return null;
        }
        return participants.get(0);
    }

    @Override
    public List<Participant> findByEvent(Event event) {
        List<Participant> startList = new ArrayList<>();
        for (Participant participant: participantRepository.findParticipantByEvent(event)) {
            if(participant.isConfirmed()) {
                startList.add(participant);
            }
        }
        return startList;
    }
}
