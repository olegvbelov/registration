package org.belova.registration.models;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "agegroup", schema = "registration")
public class AgeGroup {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @Column(name = "nameGroup")
    String nameGroup;
    @Column(name = "groupBegin")
    String groupBegin;
    @Column(name = "groupEnd")
    String groupEnd;
    @Column(name = "ageType")
    String ageType;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "distance")
    private Distance distance;

    public AgeGroup(String nameGroup, String groupBegin, String groupEnd, String ageType) {
        this.nameGroup = nameGroup;
        this.groupBegin = groupBegin;
        this.groupEnd = groupEnd;
        this.ageType = ageType;
    }

    public AgeGroup() {
    }

    public String getNameGroup() {
        return nameGroup;
    }

    public void setNameGroup(String nameGroup) {
        this.nameGroup = nameGroup;
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

    public void setGroupBegin(String groupBegin) {
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

    public Distance getDistance() {
        return distance;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }
}
