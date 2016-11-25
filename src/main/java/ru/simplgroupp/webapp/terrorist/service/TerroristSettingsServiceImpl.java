package ru.simplgroupp.webapp.terrorist.service;

import ru.simplgroupp.webapp.terrorist.data.SettingsData;
import ru.simplgroupp.webapp.terrorist.model.TerroristSettingsEntity;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * 26.07.2015
 * 22:07
 */

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class TerroristSettingsServiceImpl implements TerroristSettingsService {
    @PersistenceContext(unitName = "TerrPU")
    private EntityManager emMicro;

    @Override
    public TerroristSettingsEntity getSettings() {
        List<TerroristSettingsEntity> result = emMicro.createQuery("select ts from TerroristSettingsEntity ts", TerroristSettingsEntity.class).getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public SettingsData getSettingsData() {
        TerroristSettingsEntity entity = getSettings();
        if (entity == null) return null;
        SettingsData settingsData = new SettingsData();
        settingsData.setDays(entity.getDays());
        settingsData.setHour(entity.getHour());
        return settingsData;
    }

    @Override
    public void saveSettings(String days, Integer hour) {
        TerroristSettingsEntity settings = getSettings();
        if (settings == null) {
            settings = new TerroristSettingsEntity();
        }
        settings.setDays(days);
        settings.setHour(hour);
        emMicro.merge(settings);
    }
}
