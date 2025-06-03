package service;

import java.util.*;

import model.Alert;
import util.JsonDB;

public class AlertService {
    private static final String ALERTS_JSON = "alerts.json";

    public static List<Alert> getAllAlerts() {
        return JsonDB.load(ALERTS_JSON, Alert.class);
    }

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
}
