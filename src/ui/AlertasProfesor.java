package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

import service.AlertService;
import session.Session;

public class AlertasProfesor extends JPanel {

    private JPanel notificacionesPanel;

    public AlertasProfesor(List<String> mensajes) {
        inicializarInterfaz();
        for (String msg : mensajes) {
            agregarNotificacion(msg);
        }
    }

    private void inicializarInterfaz() {
        setLayout(new BorderLayout());
        setBackground(new Color(230, 245, 255));

        // Panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBackground(new Color(230, 245, 255));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
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
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        // Botón Eliminar alertas
        JButton btnEliminar = new JButton("Eliminar alertas");
        btnEliminar.setFocusPainted(false);
        btnEliminar.setBackground(new Color(200, 60, 60));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnEliminar.addActionListener(this::eliminarTodas);

        JPanel panelBoton = new JPanel();
        panelBoton.setBackground(new Color(230, 245, 255));
        panelBoton.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        panelBoton.add(btnEliminar);

        panelPrincipal.add(panelBoton, BorderLayout.SOUTH);

        add(panelPrincipal, BorderLayout.CENTER);
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

    private void eliminarTodas(ActionEvent e) {
        // Eliminar visualmente
        notificacionesPanel.removeAll();
        notificacionesPanel.revalidate();
        notificacionesPanel.repaint();

        // Eliminar del archivo JSON
        String rol = Session.getCurrentUser().getRole();
        AlertService.clearAlertsForRole(rol);

        JOptionPane.showMessageDialog(this, "Todas las alertas han sido eliminadas.");
    }
}
