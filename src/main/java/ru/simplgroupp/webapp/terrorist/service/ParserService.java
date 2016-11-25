package ru.simplgroupp.webapp.terrorist.service;

/**
 * 23.07.2015
 * 12:31
 */

import ru.simplgroupp.webapp.terrorist.data.ParsePageTypes;
import ru.simplgroupp.webapp.terrorist.data.Person;

import javax.ejb.Local;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * Класс для парсинга террористов с сайта
 */
@Local
public interface ParserService {
    /**
     * Метод парсит террористов с указанного урл
     *
     * @param url URL страницы для парсинга
     * @return Список лиц с сайта
     */
    List<Person> parse(String url) throws IOException, ParseException;
}
