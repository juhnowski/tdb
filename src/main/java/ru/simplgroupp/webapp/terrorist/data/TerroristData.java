package ru.simplgroupp.webapp.terrorist.data;

/**
 * 23.07.2015
 * 13:59
 */

import ru.simplgroupp.webapp.terrorist.Constants;
import ru.simplgroupp.webapp.terrorist.model.TerroristEntity;

import java.text.SimpleDateFormat;

/**
 * Data transfer object
 */
public class TerroristData {
    /**
     * дата начала
     */
    private String databeg;
    /**
     * дата конца
     */
    private String dataend;
    /**
     * признак активен/неактивен
     */
    private Boolean isActive;

    public String getDatabeg() {
        return databeg;
    }

    public void setDatabeg(String databeg) {
        this.databeg = databeg;
    }

    public String getDataend() {
        return dataend;
    }

    public void setDataend(String dataend) {
        this.dataend = dataend;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public void fromDao(TerroristEntity dao) {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DD_MM_YYYY);
        this.databeg = sdf.format(dao.getDatabeg());
        if (dao.getDataend() != null) {
            this.dataend = sdf.format(dao.getDataend());
        }
        this.isActive = dao.getIsActive();
    }
}
