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

    // Agregar una nueva alerta (a usuarios individuales)
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

    // Obtener las alertas visibles para un usuario específico
    public static List<String> getAlertsForUser(String username) {
        List<Alert> all = getAllAlerts();
        List<String> mensajes = new ArrayList<>();

        for (Alert alert : all) {
            if (alert.getDestinatarios().contains(username)) {
                mensajes.add(alert.getMensaje());
            }
        }

        return mensajes;
    }

    // Eliminar alertas solo para un usuario específico
    public static void clearAlertsForUser(String username) {
        List<Alert> alerts = getAllAlerts();

        for (Alert alert : alerts) {
            alert.getDestinatarios().remove(username); // Quitar al usuario de la lista de destinatarios
        }

        JsonDB.save(ALERTS_JSON, alerts); // Guardar cambios
    }
}
