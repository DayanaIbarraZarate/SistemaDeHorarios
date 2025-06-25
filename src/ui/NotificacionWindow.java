package ui;
import javax.swing.*;

import service.AlertService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class NotificacionWindow extends JFrame {

    private JTextField mensajeField;
    private JCheckBox chkProfesores;
    private JCheckBox chkEstudiantes;

    public NotificacionWindow() {
        setTitle("Enviar Notificación");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(173, 216, 230)); // Celeste claro
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel titulo = new JLabel("NUEVA NOTIFICACIÓN", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        panel.add(titulo, gbc);

        // Campo mensaje
        gbc.gridy++;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Mensaje:"), gbc);

        mensajeField = new JTextField();
        gbc.gridx = 1;
        panel.add(mensajeField, gbc);

        // Check Profesores
        gbc.gridx = 0;
        gbc.gridy++;
        chkProfesores = new JCheckBox("Profesores");
        chkProfesores.setBackground(panel.getBackground());
        panel.add(chkProfesores, gbc);

        // Check Estudiantes
        gbc.gridx = 1;
        chkEstudiantes = new JCheckBox("Estudiantes");
        chkEstudiantes.setBackground(panel.getBackground());
        panel.add(chkEstudiantes, gbc);

        // Botón CONFIRMAR con azul oscuro y texto negro
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton btnConfirmar = new JButton("CONFIRMAR");
        btnConfirmar.setBackground(new Color(0, 102, 204)); // Azul oscuro
        btnConfirmar.setForeground(Color.BLACK); // Letras negras
        btnConfirmar.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnConfirmar.setFocusPainted(false);
        btnConfirmar.addActionListener(this::enviarNotificacion);
        panel.add(btnConfirmar, gbc);

        add(panel);
        setVisible(true);
    }

    private void enviarNotificacion(ActionEvent e) {
        String mensaje = mensajeField.getText().trim();
        boolean aProfesores = chkProfesores.isSelected();
        boolean aEstudiantes = chkEstudiantes.isSelected();

        if (mensaje.isEmpty() || (!aProfesores && !aEstudiantes)) {
            JOptionPane.showMessageDialog(this, "Debes escribir un mensaje y seleccionar al menos un destinatario.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<String> destinatarios = new ArrayList<>();
        if (aProfesores) destinatarios.add("PROFESOR");
        if (aEstudiantes) destinatarios.add("ESTUDIANTE");
//liz puso esta linea
        AlertService.addAlert(mensaje, destinatarios);
        JOptionPane.showMessageDialog(this, "Mensaje enviado a: " + String.join(", ", destinatarios), "Éxito", JOptionPane.INFORMATION_MESSAGE);
        mensajeField.setText("");
        chkProfesores.setSelected(false);
        chkEstudiantes.setSelected(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(NotificacionWindow::new);
    }
}
