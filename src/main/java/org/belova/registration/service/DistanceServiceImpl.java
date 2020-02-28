package org.belova.registration.service;

import org.belova.registration.models.Distance;
import org.belova.registration.models.Event;
import org.belova.registration.repositories.DistanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DistanceServiceImpl implements DistanceService {
    @Autowired
    private DistanceRepository distanceRepository;
    @Override
    public Distance addDistance(Distance distance) {
        return distanceRepository.saveAndFlush(distance);
    }

    @Override
    public void deleteDistance(Distance distance) {
        distanceRepository.delete(distance);
    }

    @Override
    public List<Distance> findDistanceByDistanceName(String distanceName) {
        return distanceRepository.findDistanceByDistanceName(distanceName);
    }

    @Override
    public List<Distance> findAll() {
        return distanceRepository.findAll();
    }

    @Override
    public Set<Distance> findDistanceByNameCompetition(String nameCompetition) {
        Set<Distance> distances = new HashSet<Distance>();
        for (Distance distance: distanceRepository.findAll()) {
            if(distance.getEvent().getNameCompetition().equals(nameCompetition)) {
                distances.add(distance);
            }
        }
        return distances;
    }

    @Override
    public Distance findByDistanceNameAndEvent(String distanceName, Event event) {
        List<Distance> distances = distanceRepository.findDistanceByDistanceNameAndEvent(distanceName, event);
        if(distances.isEmpty()){
            return null;
        }
        return distances.get(0);
    }

    @Override
    public List<Distance> findDistancesByEvent(Event event) {
        return distanceRepository.findDistancesByEvent(event);
    }

    @Override
    public Distance findDistanceById(Long id) {
        return distanceRepository.findDistancesById(id);
    }
}
