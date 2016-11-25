package ru.simplgroupp.webapp.terrorist.service;

/**
 * 23.07.2015
 * 12:13
 */

import ru.simplgroupp.webapp.terrorist.data.ParsePageTypes;
import ru.simplgroupp.webapp.terrorist.data.Person;
import ru.simplgroupp.webapp.terrorist.data.TerroristData;
import ru.simplgroupp.webapp.terrorist.model.TerroristEntity;

import javax.ejb.Local;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Класс для работы с террористами
 */
@Local
public interface TerroristService {
    /**
     * Метод обновляет таблицу с террористами
     *
     * @return true если обновление прошло успешно
     */
    Boolean update();

    /**
     * Метод обновляет таблицу с террористами по определеному урлу
     *
     * @param url  URL парсируемой страницы
     * @param type тип парсируемой страницы (актуальные, добавленые, удаленые)
     * @return true если обновление прошло успешно
     */
    Boolean update(String url, ParsePageTypes type);

    /**
     * Метод обновляет таблицу с террористами по файлу
     *
     * @param content контент файла
     * @param parsePageTypes тип парсируемой страницы (актуальные, добавленые, удаленые)
     * @return true если обновление прошло успешно
     */
    Boolean updateFromFile(String content, ParsePageTypes parsePageTypes);

    /**
     * Возвращает список всех террористов в базе
     *
     * @return список террористов
     */
    List<TerroristEntity> getList();

    /**
     * Проверка на наличие ФИО в базе террористов
     *
     * @param surname  Фамилия
     * @param name     Имя
     * @param midname  Отчество
     * @param birthday Год рождения
     * @return TerroristData информация о найденой записи
     */
    TerroristData isTerrorist(String surname, String name, String midname, Date birthday);
}
