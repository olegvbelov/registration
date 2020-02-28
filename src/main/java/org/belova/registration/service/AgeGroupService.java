package org.belova.registration.service;

import org.belova.registration.models.AgeGroup;
import org.belova.registration.models.Distance;

import java.util.List;
import java.util.Set;

public interface AgeGroupService {
    AgeGroup addAgeGroup(AgeGroup ageGroup);
    void deleteAgeGroup(AgeGroup ageGroup);
    List<AgeGroup> findAll();
    List<AgeGroup> findAgeGroupByNameGroup(String nameGroup);
    List<AgeGroup> findAgeGroupByDistance(Distance distance);
}
