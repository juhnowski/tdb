package ru.simplgroupp.webapp.terrorist.service;

import ru.simplgroupp.webapp.terrorist.data.UpdateData;
import ru.simplgroupp.webapp.terrorist.model.UpdateEntity;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * 10.08.2015
 * 16:11
 */

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UpdateServiceImpl implements UpdateService {
    @PersistenceContext(unitName = "TerrPU")
    private EntityManager manager;

    @Override
    public List<UpdateData> getUpdateDataWithLimit() {
        List<UpdateEntity> entities =
                manager.createQuery("from UpdateEntity u order by u.id desc", UpdateEntity.class).setMaxResults(25).getResultList();
        List<UpdateData> result = new ArrayList<>();
        for (UpdateEntity entity : entities) {
            UpdateData data = new UpdateData();
            data.fromEntity(entity);
            result.add(data);
        }
        return result;
    }
}
