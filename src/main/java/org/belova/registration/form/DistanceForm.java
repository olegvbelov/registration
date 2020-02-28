package org.belova.registration.form;

import org.belova.registration.models.AgeGroup;

import java.util.Set;

public class DistanceForm {
    private Long id;
    private String distanceName;

    public DistanceForm() {
    }

    public DistanceForm(String distanceName) {
        this.distanceName = distanceName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDistanceName() {
        return distanceName;
    }

    public void setDistanceName(String distanceName) {
        this.distanceName = distanceName;
    }
}
