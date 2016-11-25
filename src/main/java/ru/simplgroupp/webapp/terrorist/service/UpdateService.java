package ru.simplgroupp.webapp.terrorist.service;

import ru.simplgroupp.webapp.terrorist.data.UpdateData;

import javax.ejb.Local;
import java.util.List;

/**
 * 10.08.2015
 * 16:10
 */

@Local
public interface UpdateService {
    List<UpdateData> getUpdateDataWithLimit();
}
