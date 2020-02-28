package org.belova.registration.service;

import org.belova.registration.models.AgeGroup;
import org.belova.registration.models.Distance;
import org.belova.registration.repositories.AgeGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AgeGroupServiceImpl implements AgeGroupService{
    @Autowired
    private AgeGroupRepository ageGroupRepository;

    @Override
    public AgeGroup addAgeGroup(AgeGroup ageGroup) {
        return ageGroupRepository.saveAndFlush(ageGroup);
    }

    @Override
    public void deleteAgeGroup(AgeGroup ageGroup) {
        ageGroupRepository.delete(ageGroup);
    }


    @Override
    public List<AgeGroup> findAll() {
        return null;
    }

    @Override
    public List<AgeGroup> findAgeGroupByNameGroup(String nameGroup) {
        return ageGroupRepository.findAgeGroupByNameGroup(nameGroup);
    }

    @Override
    public List<AgeGroup> findAgeGroupByDistance(Distance distance) {
        return ageGroupRepository.findAgeGroupByDistance(distance);
    }
}
