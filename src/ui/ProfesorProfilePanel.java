package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import session.Session;
import model.User;
import service.AuthService;

public class ProfesorProfilePanel extends JPanel {

    private JTextField nombreField;
    private JTextField apellidoField;
    private JTextField ciudadField;
    private JTextField correoField;
    private JComboBox<String> timezoneCombo;

    private User currentUser;

    public ProfesorProfilePanel() {
        currentUser = Session.getCurrentUser();

        setLayout(new GridBagLayout());
        setBackground(new Color(220, 240, 255)); // Celeste claro

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        // Componentes
        JLabel nombreLabel = new JLabel("Nombre:");
        nombreField = new JTextField(20);
        JLabel apellidoLabel = new JLabel("Apellido:");
        apellidoField = new JTextField(20);
        JLabel ciudadLabel = new JLabel("Ciudad:");
        ciudadField = new JTextField(20);
        JLabel correoLabel = new JLabel("Correo:");
        correoField = new JTextField(20);
        JLabel timezoneLabel = new JLabel("Zona Horaria:");
        timezoneCombo = new JComboBox<>(java.util.TimeZone.getAvailableIDs());

        // Datos actuales
        nombreField.setText(currentUser.getNombre() != null ? currentUser.getNombre() : "");
        apellidoField.setText(currentUser.getApellido() != null ? currentUser.getApellido() : "");
        ciudadField.setText(currentUser.getCiudad() != null ? currentUser.getCiudad() : "");
        correoField.setText(currentUser.getCorreo() != null ? currentUser.getCorreo() : "");
        timezoneCombo.setSelectedItem(currentUser.getTimezone() != null ? currentUser.getTimezone() : java.util.TimeZone.getDefault().getID());

        // Añadir a layout
        int fila = 0;
        gbc.gridx = 0; gbc.gridy = fila;
        add(nombreLabel, gbc); gbc.gridx = 1;
        add(nombreField, gbc);

        gbc.gridx = 0; gbc.gridy = ++fila;
        add(apellidoLabel, gbc); gbc.gridx = 1;
        add(apellidoField, gbc);

        gbc.gridx = 0; gbc.gridy = ++fila;
        add(ciudadLabel, gbc); gbc.gridx = 1;
        add(ciudadField, gbc);

        gbc.gridx = 0; gbc.gridy = ++fila;
        add(correoLabel, gbc); gbc.gridx = 1;
        add(correoField, gbc);

        gbc.gridx = 0; gbc.gridy = ++fila;
        add(timezoneLabel, gbc); gbc.gridx = 1;
        add(timezoneCombo, gbc);

        // Botones
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(getBackground());
        JButton btnGuardar = new JButton("Guardar");
        JButton btnLimpiar = new JButton("Limpiar");

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCambios();
            }
        });

        btnLimpiar.addActionListener(e -> limpiarCampos());

        buttonsPanel.add(btnGuardar);
        buttonsPanel.add(btnLimpiar);

        gbc.gridx = 0; gbc.gridy = ++fila;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(buttonsPanel, gbc);
    }

    private void guardarCambios() {
        currentUser.setNombre(nombreField.getText().trim());
        currentUser.setApellido(apellidoField.getText().trim());
        currentUser.setCiudad(ciudadField.getText().trim());
        currentUser.setCorreo(correoField.getText().trim());
        currentUser.setTimezone((String) timezoneCombo.getSelectedItem());

        AuthService.updateUser(currentUser);
        JOptionPane.showMessageDialog(this, "Datos guardados correctamente.");
    }

    private void limpiarCampos() {
        nombreField.setText("");
        apellidoField.setText("");
        ciudadField.setText("");
        correoField.setText("");
        timezoneCombo.setSelectedItem(java.util.TimeZone.getDefault().getID());
    }
}
