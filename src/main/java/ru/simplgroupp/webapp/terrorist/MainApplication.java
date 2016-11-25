package ru.simplgroupp.webapp.terrorist;

import ru.simplgroupp.webapp.terrorist.rest.CsvRestController;
import ru.simplgroupp.webapp.terrorist.rest.TerroristRestController;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * 23.07.2015
 * 18:35
 */

@ApplicationPath("/rest")
public class MainApplication extends Application {
    Set<Class<?>> classes = new HashSet<>();

    public MainApplication() {
        classes.add(TerroristRestController.class);
        classes.add(CsvRestController.class);
    }

    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }
}
