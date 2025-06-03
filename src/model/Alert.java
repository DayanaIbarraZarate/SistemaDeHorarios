package model;

import java.sql.Date;
import java.util.List;

public class Alert {
    public String id;
    public String message;
    public List<String> recipients;
    public Date date;

    public Alert(String id, String message, List<String> recipients, Date date) {
        this.id = id;
        this.message = message;
        this.recipients = recipients;
        this.date = date;
    }

    public String getMensaje() {
        return message;
    }

    public List<String> getDestinatarios() {
        return recipients;
    }

    public Date getFecha() {
        return date;
    }
    
}
