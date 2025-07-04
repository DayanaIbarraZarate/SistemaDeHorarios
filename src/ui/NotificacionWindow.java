package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import service.AlertService;
import service.UserService;
import session.Session;
import model.Alert;
import model.User;

public class NotificacionWindow extends JFrame {

    private JTextField avisoField;
    private JToggleButton toggleProfesores;
    private JToggleButton toggleEstudiantes;

    public NotificacionWindow() {
        setTitle("Nueva Notificaci贸n");
        setSize(550, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Color fondoAzul = new Color(119, 167, 177);
        Color fondoClaro = new Color(240, 240, 240);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(fondoAzul);
        mainPanel.setLayout(new GridBagLayout());

        JPanel notificationPanel = new RoundedPanel(20);
        notificationPanel.setBackground(fondoClaro);
        notificationPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel tituloLabel = new JLabel("NUEVA NOTIFICACI脫N");
        tituloLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        notificationPanel.add(tituloLabel, gbc);

        JLabel avisoLabel = new JLabel("AVISO");
        gbc.gridy++;
        gbc.gridwidth = 1;
        notificationPanel.add(avisoLabel, gbc);

        avisoField = new JTextField();
        gbc.gridx = 1;
        notificationPanel.add(avisoField, gbc);

        JLabel destinatarioLabel = new JLabel("DESTINATARIO");
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        notificationPanel.add(destinatarioLabel, gbc);

        toggleProfesores = new JToggleButton("PROFESORES");
        toggleProfesores.setFocusPainted(false);
        gbc.gridy++;
        notificationPanel.add(toggleProfesores, gbc);

        toggleEstudiantes = new JToggleButton("ESTUDIANTES");
        toggleEstudiantes.setFocusPainted(false);
        gbc.gridy++;
        notificationPanel.add(toggleEstudiantes, gbc);

        JButton confirmarBtn = new JButton("CONFIRMAR");
        confirmarBtn.setBackground(fondoAzul);
        confirmarBtn.setForeground(Color.WHITE);
        confirmarBtn.setFocusPainted(false);
        gbc.gridy++;
        notificationPanel.add(confirmarBtn, gbc);

        confirmarBtn.addActionListener(this::enviarNotificacion);

        mainPanel.add(notificationPanel);
        add(mainPanel);
        setVisible(true);
    }

    private void enviarNotificacion(ActionEvent e) {
        String mensaje = avisoField.getText().trim();
        boolean aProfesores = toggleProfesores.isSelected();
        boolean aEstudiantes = toggleEstudiantes.isSelected();

        if (mensaje.isEmpty() || (!aProfesores && !aEstudiantes)) {
            JOptionPane.showMessageDialog(this, "Completa el aviso y selecciona al menos un destinatario.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<String> destinatarios = new ArrayList<>();

        if (aProfesores) {
            List<User> profesores = UserService.getUsersByRole("PROFESOR");
            for (User u : profesores) {
                destinatarios.add(u.getUsername());
            }
        }

        if (aEstudiantes) {
            List<User> estudiantes = UserService.getUsersByRole("ESTUDIANTE");
            for (User u : estudiantes) {
                destinatarios.add(u.getUsername());
            }
        }

        AlertService.addAlert(mensaje, destinatarios);

        JOptionPane.showMessageDialog(this, "Notificacion guardada y enviada correctamente.");
        dispose();
    }

    // Mostrar notificaciones para el usuario actual
    public static void mostrarAlertasParaUsuarioActual() {
        String username = Session.getCurrentUser().getUsername();
        List<String> mensajes = AlertService.getAlertsForUser(username);

        if (mensajes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay notificaciones para mostrar.", "Alertas", JOptionPane.INFORMATION_MESSAGE);
        } else {
            new AlertasProfesor(mensajes);
        }
    }

    // Panel redondeado
    static class RoundedPanel extends JPanel {
        private int radius;

        public RoundedPanel(int radius) {
            this.radius = radius;
            setOpaque(false);
        }

        
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.dispose();
            super.paintComponent(g);
        }
    }
}