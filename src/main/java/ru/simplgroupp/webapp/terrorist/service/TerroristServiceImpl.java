package ru.simplgroupp.webapp.terrorist.service;

import org.apache.log4j.Logger;
import ru.simplgroupp.webapp.terrorist.data.ParsePageTypes;
import ru.simplgroupp.webapp.terrorist.data.Person;
import ru.simplgroupp.webapp.terrorist.data.TerroristData;
import ru.simplgroupp.webapp.terrorist.model.TerroristEntity;
import ru.simplgroupp.webapp.terrorist.model.UpdateEntity;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 23.07.2015
 * 12:36
 */

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class TerroristServiceImpl implements TerroristService {
    private Logger logger = Logger.getLogger(TerroristServiceImpl.class.getName());

    @EJB
    private ParserService parserService;

    @PersistenceContext(unitName = "TerrPU")
    private EntityManager emMicro;

    @Override
    public Boolean update() {
        return update(ParsePageTypes.ACT.getUrl(), ParsePageTypes.ACT) &
                update(ParsePageTypes.ADD.getUrl(), ParsePageTypes.ADD) &
                update(ParsePageTypes.DEL.getUrl(), ParsePageTypes.DEL);
    }

    @Override
    public Boolean update(String url, ParsePageTypes type) {
        UpdateEntity updateEntity = new UpdateEntity();
        updateEntity.setType(type.name());
        try {
            List<Person> personList = parserService.parse(url);
            this.update(personList, type);
        } catch (IOException e) {
            updateEntity.setMessage(e.toString());
            updateEntity.setStatus(false);
        } catch (ParseException e) {
            updateEntity.setMessage(e.toString());
            updateEntity.setStatus(false);
        }
        logger.info("Terrorist table updated.");
        emMicro.persist(updateEntity);
        return updateEntity.getStatus();
    }

    @Override
    public Boolean updateFromFile(String content, ParsePageTypes parsePageTypes) {
        UpdateEntity updateEntity = new UpdateEntity();
        updateEntity.setMessage("FROM FILE OK");
        updateEntity.setType(parsePageTypes.name());

        List<Person> persons = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(content);

        while (tokenizer.hasMoreTokens()) {
            String[] strings = tokenizer.nextToken().split(";");
            try {
                persons.add(new Person(strings[0], strings[1], strings[2], strings[3], strings[4]));
            } catch (Exception e) {
                logger.error(e);
                updateEntity.setMessage(e.toString());
                updateEntity.setStatus(false);
            }
        }
        if (updateEntity.getStatus()) {
            update(persons, parsePageTypes);
        }
        emMicro.persist(updateEntity);
        return updateEntity.getStatus();
    }

    private void update(List<Person> persons, ParsePageTypes type) {
        if (ParsePageTypes.ACT.equals(type)) {
            updateAct(persons);
        } else if (ParsePageTypes.ADD.equals(type)) {
            updateAdd(persons);
        } else if (ParsePageTypes.DEL.equals(type)) {
            unActivateDeletedPersons(persons);
        }
    }

    private void updateAdd(List<Person> persons) {
        if (persons.size() > 0) {
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE terrorists SET databeg = '").append(new Date()).append("' WHERE ");
            String or = "";
            for (Person p : persons) {
                sql.append(or).append("(");
                sql.append("lower(name) = '").append(p.getName().toLowerCase()).append("'");
                if (p.getSurname() != null) {
                    sql.append(" and lower(surname) = '").append(p.getSurname().toLowerCase()).append("'");
                }
                if (p.getMidname() != null) {
                    sql.append(" and lower(midname) = '").append(p.getMidname().toLowerCase()).append("'");
                }
                if (p.getBirthday() != null) {
                    sql.append(" and birthday = '").append(p.getBirthday()).append("'");
                }
                sql.append(")");
                or = "or";
            }
            int o = emMicro.createNativeQuery(sql.toString()).executeUpdate();
        }
    }

    private void updateAct(List<Person> persons) {
        List<TerroristEntity> entities = getList();
        List<Person> dbPersons = new ArrayList<>();
        for (TerroristEntity en : entities) {
            Person person = new Person();
            person.fromEntity(en);
            dbPersons.add(person);
        }

        persons.removeAll(dbPersons);
        if (persons.size() > 0) {
            for (Person p : persons) {
                TerroristEntity en = new TerroristEntity();
                en.setSurname(p.getSurname());
                en.setName(p.getName());
                en.setMidname(p.getMidname());
                en.setBirthPlace(p.getBirthPlace());
                en.setBirthday(p.getBirthday());
                en.setIsActive(true);
                en.setDatabeg(new Date(1262293200000L));
                emMicro.persist(en);
            }
        }
        emMicro.flush();
    }

    private List<Person> parseFromUrl(String url) throws IOException, ParseException {
        return parserService.parse(url);
    }

    private void unActivateDeletedPersons(List<Person> persons) {
        if (persons.size() > 0) {
            StringBuilder sql = new StringBuilder();
            sql.append("update terrorists set isactive = 'f', dataend = '" + new Date() + "' where ");
            String or = "";
            for (Person p : persons) {
                sql.append(or);
                sql.append("(");
                sql.append("lower(name) = '").append(p.getName().toLowerCase()).append("'");
                if (p.getSurname() != null) {
                    sql.append(" and lower(surname) = '").append(p.getSurname().toLowerCase()).append("'");
                }
                if (p.getMidname() != null) {
                    sql.append(" and lower(midname) = '").append(p.getMidname().toLowerCase()).append("'");
                }
                if (p.getBirthday() != null) {
                    sql.append(" and birthday = '").append(p.getBirthday()).append("'");
                }
                sql.append(")");
                or = " or ";
            }
            emMicro.createNativeQuery(sql.toString()).executeUpdate();
        }
    }

    @Override
    public List<TerroristEntity> getList() {
        return emMicro.createQuery("select t from TerroristEntity t", TerroristEntity.class).getResultList();
    }

    @Override
    public TerroristData isTerrorist(String surname, String name, String midname, Date birthday) {
        TypedQuery<TerroristEntity> query = emMicro.createQuery(
                "select t from TerroristEntity t where lower(t.surname) = :surname and lower(t.name) = :name and lower(t.midname) = :midname and birthday = :birthday",
                TerroristEntity.class
        );
        query.setParameter("surname", surname.toLowerCase());
        query.setParameter("name", name.toLowerCase());
        query.setParameter("midname", midname.toLowerCase());
        query.setParameter("birthday", birthday);
        List<TerroristEntity> result = query.getResultList();

        TerroristData data = null;
        if (result.size() > 0) {
            data = new TerroristData();
            data.fromDao(result.get(0));
        }

        return data;
    }
}
