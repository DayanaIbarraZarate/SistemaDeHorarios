package service;

import model.Schedule;
import util.JsonDB;
import java.util.List;
import java.util.UUID;

public class ScheduleService {
    private static final String SCHEDULES_JSON = "schedules.json";

    public static List<Schedule> getAllSchedules() {
        return JsonDB.load(SCHEDULES_JSON, Schedule.class);
    }

    public static void addSchedule(String dia, String horaInicio, String horaFin, String materia, String tipo) {
    
       
        List<Schedule> schedules = getAllSchedules();
        schedules.add(new Schedule(
            UUID.randomUUID().toString(),
            dia,
            horaInicio,
            horaFin,
            materia,
            
            tipo // 👈 ahora se pasa el tipo
        ));
        JsonDB.save(SCHEDULES_JSON, schedules);
    }
}