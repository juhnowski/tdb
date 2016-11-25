package ru.simplgroupp.webapp.terrorist.service;

import ru.simplgroupp.webapp.terrorist.data.SettingsData;
import ru.simplgroupp.webapp.terrorist.model.TerroristSettingsEntity;

import javax.ejb.Local;

/**
 * 26.07.2015
 * 22:07
 */

@Local
public interface TerroristSettingsService {
    /**
     * Метод достает настройки обновлений для таблицы с террористами
     *
     * @return настройки
     */
    TerroristSettingsEntity getSettings();

    /**
     * Метод достает настройки для передачи на фронт
     *
     * @return настройки
     */
    SettingsData getSettingsData();

    /**
     * Метод сохраняет настройки
     *
     * @param days дни
     * @param hour часы
     */
    void saveSettings(String days, Integer hour);
}
