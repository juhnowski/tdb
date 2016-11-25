package ru.simplgroupp.webapp.terrorist.data;

import ru.simplgroupp.webapp.terrorist.model.UpdateEntity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 10.08.2015
 * 16:10
 */

public class UpdateData {
    private Integer id;
    private Boolean status;
    private String message;
    private String type;
    private String date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void fromEntity(UpdateEntity entity) {
        SimpleDateFormat sdf = new SimpleDateFormat();

        this.setId(entity.getId());
        this.setStatus(entity.getStatus());
        this.setType(entity.getType());
        this.setMessage(entity.getMessage());
        this.setDate(sdf.format(entity.getDate()));
    }
}
