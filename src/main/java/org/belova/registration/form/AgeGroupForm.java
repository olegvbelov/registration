package org.belova.registration.form;

public class AgeGroupForm {
    private Long id;
    private String groupBegin;
    private String groupEnd;
    private String ageType;
    private DistanceForm distanceForm;

    public AgeGroupForm() {
    }

    public AgeGroupForm(Long id, String groupBegin, String groupEnd, String ageType) {
        this.id = id;
        this.groupBegin = groupBegin;
        this.groupEnd = groupEnd;
        this.ageType = ageType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupBegin() {
        return groupBegin;
    }

    public void setGroupBegin(String dateBegin) {
        this.groupBegin = groupBegin;
    }

    public String getGroupEnd() {
        return groupEnd;
    }

    public void setGroupEnd(String groupEnd) {
        this.groupEnd = groupEnd;
    }

    public String getAgeType() {
        return ageType;
    }

    public void setAgeType(String ageType) {
        this.ageType = ageType;
    }

    public DistanceForm getDistanceForm() {
        return distanceForm;
    }

    public void setDistanceForm(DistanceForm distanceForm) {
        this.distanceForm = distanceForm;
    }
}
