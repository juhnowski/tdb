package ru.simplgroupp.webapp.terrorist.model;

import javax.persistence.*;

/**
 * 26.07.2015
 * 18:42
 */

@Entity
@Table(name = "terrorist_settings")
public class TerroristSettingsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String days;
    private Integer hour;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }
}
