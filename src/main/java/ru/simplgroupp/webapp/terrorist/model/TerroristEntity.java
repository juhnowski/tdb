package ru.simplgroupp.webapp.terrorist.model;

import javax.persistence.*;
import java.util.Date;

/**
 * 06.08.2015
 * 21:16
 */

/**
 * Террористы
 */
@Entity
@Table(name = "terrorists")
public class TerroristEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String surname;
    private String name;
    private String midname;
    private Date birthday;
    @Column(name = "birthplace")
    private String birthPlace;
    private Date databeg;
    private Date dataend;
    @Column(name = "isactive")
    private Boolean isActive;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMidname() {
        return midname;
    }

    public void setMidname(String midname) {
        this.midname = midname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public Date getDatabeg() {
        return databeg;
    }

    public void setDatabeg(Date databeg) {
        this.databeg = databeg;
    }

    public Date getDataend() {
        return dataend;
    }

    public void setDataend(Date dataend) {
        this.dataend = dataend;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
