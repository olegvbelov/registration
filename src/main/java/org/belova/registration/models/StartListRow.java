package org.belova.registration.models;

public class StartListRow {

    private String participant;

    private String info;

    private String gender;

    private String ageGroupName;

    private String distanceName;

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String participant) {
        this.participant = participant;
    }

    public String getAgeGroupName() {
        return ageGroupName;
    }

    public void setAgeGroupName(String ageGroupName) {
        this.ageGroupName = ageGroupName;
    }

    public String getDistanceName() {
        return distanceName;
    }

    public void setDistanceName(String distanceName) {
        this.distanceName = distanceName;
    }

    public StartListRow() {
    }

    public StartListRow(String participant, String info, String gender, String ageGroupName, String distanceName) {
        this.participant = participant;
        this.info = info;
        this.gender = gender;
        this.ageGroupName = ageGroupName;
        this.distanceName = distanceName;
    }
}
