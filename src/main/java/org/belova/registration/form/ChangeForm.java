package org.belova.registration.form;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

import static javax.persistence.TemporalType.DATE;

public class ChangeForm {
    private Long id;
    @NotEmpty
    private String email;
    @NotEmpty
    private String name;
    @NotEmpty
    private String gender;
    @NotEmpty
    private String adress;
    @NotEmpty
    @Temporal(DATE)
    @DateTimeFormat(pattern="dd-MMM-YYYY")
    private Date dateBith;
    private String category;
    private String club;

    public ChangeForm() {
    }

    public ChangeForm(Long id, String email, String name, String gender, String adress, Date dateBith, String category, String club) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.adress = adress;
        this.dateBith = dateBith;
        this.category = category;
        this.club = club;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Date getDateBith() {
        return dateBith;
    }

    public void setDateBith(Date dateBith) {
        this.dateBith = dateBith;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }
}
