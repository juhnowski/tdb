package ru.simplgroupp.webapp.terrorist.service;

import org.apache.log4j.Logger;
import ru.simplgroupp.webapp.terrorist.model.TerroristSettingsEntity;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * 26.07.2015
 * 14:21
 */

@Singleton
@Startup
public class ScheduleServiceImpl implements ScheduleService {
    private Logger logger = Logger.getLogger(ScheduleServiceImpl.class.getName());

    @Resource
    TimerService timerService;

    @PersistenceContext(unitName = "TerrPU")
    private EntityManager emMicro;

    @EJB
    private TerroristService terroristService;

    private Timer timer;

    @PostConstruct
    public void initialize() {
        startTimer();
    }

    @Timeout
    public void execute(Timer timer) {
        logger.info("Updating tables with terrorists...");
        terroristService.update();
        logger.info("Terrorist update complete");
    }

    /**
     * Стартуем таймер и удаляем старый если запущен
     */
    @Override
    public void startTimer() {
        List<TerroristSettingsEntity> result = emMicro.createQuery("select ts from TerroristSettingsEntity ts", TerroristSettingsEntity.class).getResultList();
        if (timer != null) {
            timer.cancel();
        }
        if (result.size() > 0) {
            TerroristSettingsEntity settings = result.get(0);

            ScheduleExpression expression = new ScheduleExpression(); // "mon,tue,wed,thu,fri,sat,sun"
            expression.dayOfWeek(settings.getDays()).hour(settings.getHour()).minute(0).second(0);
            timer = timerService.createCalendarTimer(expression);
            logger.info("Update terrorist timer started.");
        }
    }
}
