package ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AlertasProfesor extends JPanel {

    private JPanel notificacionesPanel;

    public AlertasProfesor() {
        setLayout(new BorderLayout());
        setBackground(new Color(230, 245, 255)); // Fondo general


        // Panel principal 
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBackground(new Color(230, 245, 255)); // Fondo del panel de notificaciones
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panelPrincipal.setLayout(new BorderLayout());

        // Título
        JLabel titulo = new JLabel("ALERTAS");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titulo.setForeground(Color.DARK_GRAY);
        panelPrincipal.add(titulo, BorderLayout.NORTH);

        // Panel para notificaciones
        notificacionesPanel = new JPanel();
        notificacionesPanel.setLayout(new BoxLayout(notificacionesPanel, BoxLayout.Y_AXIS));
        notificacionesPanel.setBackground(new Color(230, 245, 255));

        JScrollPane scrollPane = new JScrollPane(notificacionesPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Más fluido

        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        
        add(panelPrincipal, BorderLayout.CENTER);

        // Cargar notificaciones de ejemplo
        cargarNotificacionesDemo();
    }
    //Ejemplo de como se veran las notificaciones
    private void cargarNotificacionesDemo() {
        List<String> mensajes = new ArrayList<>();
        mensajes.add("Reunión con coordinación a las 10:00 am.");
        mensajes.add("Recordatorio: subir calificaciones finales.");
        mensajes.add("Nuevo mensaje de la dirección académica.");

        for (String msg : mensajes) {
            agregarNotificacion(msg);
        }
    }

    public void agregarNotificacion(String mensaje) {
        JPanel notificacion = new JPanel();
        notificacion.setBackground(new Color(255, 230, 230));
        notificacion.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        notificacion.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        notificacion.setAlignmentX(Component.LEFT_ALIGNMENT);
        notificacion.setLayout(new BorderLayout());

        JLabel label = new JLabel(mensaje);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        notificacion.add(label, BorderLayout.CENTER);

        notificacionesPanel.add(Box.createVerticalStrut(10));
        notificacionesPanel.add(notificacion);
        notificacionesPanel.revalidate();
        notificacionesPanel.repaint();
    }
}
