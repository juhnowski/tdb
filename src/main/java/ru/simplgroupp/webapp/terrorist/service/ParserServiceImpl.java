package ru.simplgroupp.webapp.terrorist.service;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.simplgroupp.webapp.terrorist.data.Person;

import javax.ejb.Stateless;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 23.07.2015
 * 12:35
 */

@Stateless
public class ParserServiceImpl implements ParserService {
    private final String RUSSIAN_FL_ID = "textRussianFL";

    private Logger logger = Logger.getLogger(ParserServiceImpl.class);

    @Override
    public List<Person> parse(String url) throws IOException, ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

        List<Person> result = new ArrayList<>();
        Document doc = Jsoup.connect(url).get();
        Elements divEl = doc.select("div#" + RUSSIAN_FL_ID);
        Elements pEl = divEl.select("p");
        for (Element el : pEl) {
            Person person = new Person();

            String data = el.html().replaceAll("^\\d+\\.", "").replace(";", "").replace("*", "").trim();
            StringTokenizer tokenizer = new StringTokenizer(data, ",");

            person.setFullName(tokenizer.nextToken().trim());
            if (tokenizer.hasMoreTokens()) {
                String birthday = tokenizer.nextToken();
                if (birthday.contains("г.р.")) {
                    birthday = birthday.replaceAll("г.р.", "").trim();
                    Date dateStr = formatter.parse(birthday);
                    person.setBirthday(dateStr);
                }
            }
            if (tokenizer.hasMoreElements()) {
                person.setBirthPlace(tokenizer.nextToken().trim());
            }

            result.add(person);
        }
        return result;
    }
}
