package ru.simplgroupp.webapp.terrorist.rest;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import ru.simplgroupp.webapp.terrorist.Constants;
import ru.simplgroupp.webapp.terrorist.Utils;
import ru.simplgroupp.webapp.terrorist.data.ParsePageTypes;
import ru.simplgroupp.webapp.terrorist.data.SettingsData;
import ru.simplgroupp.webapp.terrorist.data.TerroristData;
import ru.simplgroupp.webapp.terrorist.data.UpdateData;
import ru.simplgroupp.webapp.terrorist.service.ScheduleService;
import ru.simplgroupp.webapp.terrorist.service.TerroristService;
import ru.simplgroupp.webapp.terrorist.service.TerroristSettingsService;
import ru.simplgroupp.webapp.terrorist.service.UpdateService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 23.07.2015
 * 13:43
 */

@Stateless
@Path("/")
public class TerroristRestController {
    private final Logger logger = Logger.getLogger(TerroristRestController.class.getName());

    @EJB
    private TerroristService terroristService;
    @EJB
    private TerroristSettingsService terroristSettingsService;
    @EJB
    private ScheduleService scheduleService;
    @EJB
    private UpdateService updateService;

    @POST
    @Path("schedule")
    @Produces({MediaType.APPLICATION_JSON + "; charset=UTF-8"})
    public String schedule(@FormParam("days") String days, @FormParam("hour") Integer hour) {
        terroristSettingsService.saveSettings(days, hour);
        scheduleService.startTimer();

        return "{\"success\" : true }";
    }

    @GET
    @Path("update")
    @Produces({MediaType.APPLICATION_JSON + "; charset=UTF-8"})
    public JSONObject update() {
        final Boolean result = terroristService.update();
        return new JSONObject(new HashMap() {{
            put("success", result);
        }});
    }

    @POST
    @Path("man_update")
    @Produces({MediaType.APPLICATION_JSON + "; charset=UTF-8"})
    public JSONObject schedule(@FormParam("url") String url, @FormParam("type") String type) {
        ParsePageTypes parseType = ParsePageTypes.valueOf(type);
        final Boolean result = terroristService.update(url, parseType);
        return new JSONObject(new HashMap() {{
            put("success", result);
        }});
    }

    @POST
    @Path("check")
    @Produces({MediaType.APPLICATION_JSON + "; charset=UTF-8"})
    public JSONObject isExist(@FormParam("surname") String surname, @FormParam("name") String name,
                              @FormParam("midname") String midname, @FormParam("birthday") String birthday) {
        logger.info("surname: " + surname + " name: " + name + " midname: " + midname + " birthday: " + birthday);
        JSONObject result = new JSONObject();

        if ("".equals(surname) || "".equals(name) || "".equals(birthday)) {
            result.put("error", "Вы не указали имя, фамилию либо дату рождения!");
            return result;
        }

        if (!Utils.isValidDate(birthday)) {
            result.put("error", "Дата должна быть в формате dd.MM.yyyy!");
            return result;
        }

        SimpleDateFormat format = new SimpleDateFormat(Constants.DD_MM_YYYY);
        format.setLenient(false);

        Date date;
        TerroristData person = null;
        try {
            date = format.parse(birthday);
            person = terroristService.isTerrorist(surname, name, midname, date);
            if (person != null) {
                result.put("exist", true);
                result.put("result", person);
            } else {
                result.put("exist", false);
            }
        } catch (ParseException e) {
            logger.error(e);
            result.put("error", "Дата должна быть в формате dd.MM.yyyy!");
        }
        return result;
    }

    @GET
    @Path("settings")
    @Produces({MediaType.APPLICATION_JSON + "; charset=UTF-8"})
    public SettingsData getSettings() {
        return terroristSettingsService.getSettingsData();
    }

    @GET
    @Path("updates")
    @Produces({MediaType.APPLICATION_JSON + "; charset=UTF-8"})
    public List<UpdateData> getUpdatesData() {
        return updateService.getUpdateDataWithLimit();
    }
}
