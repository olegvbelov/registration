package org.belova.registration.models;

public class ResultRow {
    private String participant;
    private String info;
    private String result;
    private String gender;
    private String distance;
    private String ageGroup;

    public ResultRow(String participant, String info, String gender, String distance, String ageGroup, String result) {
        this.participant = participant;
        this.gender = gender;
        this.distance = distance;
        this.ageGroup = ageGroup;
        this.info = info;
        this.result = result;
    }

    public ResultRow() {
    }

    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String participantInfo) {
        this.participant = participantInfo;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }
}
