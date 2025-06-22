package service;

import java.util.*;
import model.Alert;
import util.JsonDB;

public class AlertService {
    private static final String ALERTS_JSON = "alerts.json";

    // Obtener todas las alertas guardadas
    public static List<Alert> getAllAlerts() {
        return JsonDB.load(ALERTS_JSON, Alert.class);
    }

    // Agregar una nueva alerta
    public static void addAlert(String message, List<String> recipients) {
        List<Alert> alerts = getAllAlerts();

        Alert newAlert = new Alert(
            UUID.randomUUID().toString(),
            message,
            recipients,
            new java.sql.Date(System.currentTimeMillis())
        );

        alerts.add(newAlert);
        JsonDB.save(ALERTS_JSON, alerts);
    }

    // Eliminar alertas de un rol específico (como "PROFESOR" o "ESTUDIANTE")
    public static void clearAlertsForRole(String rol) {
        List<Alert> alerts = getAllAlerts();
        List<Alert> filtradas = new ArrayList<>();

        for (Alert a : alerts) {
            if (!a.getDestinatarios().contains(rol)) {
                filtradas.add(a); // Conservar solo las alertas que NO son para ese rol
            }
        }

        JsonDB.save(ALERTS_JSON, filtradas); // Guardar la nueva lista filtrada
    }
}
