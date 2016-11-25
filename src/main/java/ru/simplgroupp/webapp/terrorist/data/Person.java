package ru.simplgroupp.webapp.terrorist.data;

import ru.simplgroupp.webapp.terrorist.Constants;
import ru.simplgroupp.webapp.terrorist.Utils;
import ru.simplgroupp.webapp.terrorist.model.TerroristEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * 24.07.2015
 * 14:50
 */

public class Person {
    private String surname;
    private String name;
    private String midname;
    private Date birthday;
    private String birthPlace;

    public Person() {

    }

    public Person(String surname, String name, String midname, String birthPlace, String birthday) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DD_MM_YYYY);
        this.surname = surname;
        this.name = name;
        this.midname = midname;
        this.birthPlace = birthPlace;
        if (!Utils.isValidDate(birthday)) {
            throw new ParseException("Дата должна быть в формате dd.MM.yyyy", 0);
        }
        this.birthday = sdf.parse(birthday);
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMidname() {
        return midname;
    }

    public void setMidname(String midname) {
        this.midname = midname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getFullName() {
        return surname + " " + name + " " + midname;
    }

    public void setFullName(String fullName) {
        StringTokenizer token = new StringTokenizer(fullName, " ");
        setSurname(token.nextToken());
        setName(token.nextToken());
        if (token.hasMoreTokens()) {
            String da = token.nextToken();
            setMidname(da);
            if (token.hasMoreTokens()) {
                setMidname(getMidname() + " " + token.nextToken());
            }
        }
    }

    public void fromEntity(TerroristEntity entity) {
        this.surname = entity.getSurname();
        this.name = entity.getName();
        this.midname = entity.getMidname();
        this.birthday = entity.getBirthday();
        this.birthPlace = entity.getBirthPlace();
    }

    @Override
    public String toString() {
        return "Person{" +
                "surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", midname='" + midname + '\'' +
                ", birthday=" + birthday +
                ", birthPlace='" + birthPlace + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (surname != null ? !surname.equals(person.surname) : person.surname != null) return false;
        if (name != null ? !name.equals(person.name) : person.name != null) return false;
        if (midname != null ? !midname.equals(person.midname) : person.midname != null) return false;
        if (birthday != null ? !birthday.equals(person.birthday) : person.birthday != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = surname != null ? surname.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (midname != null ? midname.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        return result;
    }
}
