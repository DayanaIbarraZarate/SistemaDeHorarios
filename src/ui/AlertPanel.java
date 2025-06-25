
package ui;

import model.Alert;
import service.AlertService;
import session.Session;

import javax.swing.*;
import java.awt.*;
import java.util.List;
//este codigo abre el boton alertas y funciona para estudiantes y profesores 
public class AlertPanel extends JPanel {
    public AlertPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(230, 245, 255)); // Fondo suave

        String userId = Session.getCurrentUser().getId();

        List<Alert> todasLasAlertas = AlertService.getAllAlerts();

        // Filtrar solo las alertas dirigidas al usuario actual
       String role = Session.getCurrentUser().getRole(); // "PROFESOR", "ESTUDIANTE", etc.

List<Alert> alertasUsuario = todasLasAlertas.stream()
    .filter(alert -> {
        List<String> destinatarios = alert.getDestinatarios();
        return destinatarios != null && destinatarios.contains(role);
    })
    .toList();


        // Área de texto para mostrar alertas
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textArea.setBackground(new Color(240, 255, 255)); // Color de fondo turquesa claro
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        if (alertasUsuario.isEmpty()) {
            textArea.setText("🔔 No tienes alertas pendientes.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (Alert alert : alertasUsuario) {
                sb.append("📢 [").append(alert.getFecha())
                  .append("] ").append(alert.getMensaje()).append("\n\n");
            }
            textArea.setText(sb.toString());
        }

        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Título
        JLabel title = new JLabel("🛎️ Alertas Recibidas", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        title.setForeground(new Color(0, 102, 153));

        add(title, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }
}