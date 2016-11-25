package ru.simplgroupp.webapp.terrorist.rest;

import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.json.simple.JSONObject;
import ru.simplgroupp.webapp.terrorist.data.ParsePageTypes;
import ru.simplgroupp.webapp.terrorist.service.TerroristService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

/**
 * 12.08.2015
 * 11:41
 */

@Stateless
@Path("/")
public class CsvRestController {
    private final Logger logger = Logger.getLogger(CsvRestController.class.getName());

    @EJB
    private TerroristService terroristService;

    @POST
    @Path("csv_upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public JSONObject parseFile(MultipartFormDataInput input) {
        JSONObject jsonObject = new JSONObject();

        List<InputPart> inputParts = input.getFormDataMap().get("file");
        InputPart ipType = input.getFormDataMap().get("type").get(0);

        boolean result = true;
        for (InputPart inputPart : inputParts) {
            try {
                String type = ipType.getBody(String.class, null);
                String content = inputPart.getBody(String.class, null);
                if (content != null) {
                    result = terroristService.updateFromFile(content, ParsePageTypes.valueOf(type));
                    jsonObject.put("success", result);
                    if (!result) {
                        jsonObject.put("error", "Ошибка при парсинге файла!");
                    }
                } else {
                    jsonObject.put("error", "Файл пуст!");
                }
            } catch (IOException e) {
                e.printStackTrace();
                jsonObject.put("error", "Ошибка при обновлении!");
                result = false;
            }
        }
        return jsonObject;
    }
}