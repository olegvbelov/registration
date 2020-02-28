package org.belova.registration.repositories;

import org.belova.registration.models.AgeGroup;
import org.belova.registration.models.Distance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgeGroupRepository extends JpaRepository<AgeGroup, Long> {
    List<AgeGroup> findAgeGroupByNameGroup(String nameGroup);
    List<AgeGroup> findAgeGroupByDistance(Distance distance);
}
