package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import session.Session;
import model.User;
import service.AuthService;

public class PerfilEstudiante extends JFrame {

    private JTextField nombreField;
    private JTextField apellidoField;
    private JTextField ciudadField;
    private JTextField correoField;
    private JComboBox<String> timezoneCombo;

    private User currentUser;

    public PerfilEstudiante() {
        currentUser = Session.getCurrentUser();

        setTitle("Perfil del Estudiante");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(173, 216, 230)); // fondo celeste suave
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Labels y campos
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

        // Cargar datos actuales del usuario si existen
        nombreField.setText(currentUser.getNombre() != null ? currentUser.getNombre() : "");
        apellidoField.setText(currentUser.getApellido() != null ? currentUser.getApellido() : "");
        ciudadField.setText(currentUser.getCiudad() != null ? currentUser.getCiudad() : "");
        correoField.setText(currentUser.getCorreo() != null ? currentUser.getCorreo() : "");
        timezoneCombo.setSelectedItem(currentUser.getTimezone() != null ? currentUser.getTimezone() : java.util.TimeZone.getDefault().getID());

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // fila 1
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(nombreLabel, gbc);
        gbc.gridx = 1;
        panel.add(nombreField, gbc);

        // fila 2
        gbc.gridx = 0; gbc.gridy++;
        panel.add(apellidoLabel, gbc);
        gbc.gridx = 1;
        panel.add(apellidoField, gbc);

        // fila 3
        gbc.gridx = 0; gbc.gridy++;
        panel.add(ciudadLabel, gbc);
        gbc.gridx = 1;
        panel.add(ciudadField, gbc);

        // fila 4
        gbc.gridx = 0; gbc.gridy++;
        panel.add(correoLabel, gbc);
        gbc.gridx = 1;
        panel.add(correoField, gbc);

        // fila 5
        gbc.gridx = 0; gbc.gridy++;
        panel.add(timezoneLabel, gbc);
        gbc.gridx = 1;
        panel.add(timezoneCombo, gbc);

        // botones
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(new Color(173, 216, 230));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        btnGuardar.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                boolean validarCampos = validarCampos();
                if (validarCampos){
                    guardarCambios();
                }
                //ardarCambios();
            }
        });

        btnCancelar.addActionListener(e -> dispose());

        buttonsPanel.add(btnGuardar);
        buttonsPanel.add(btnCancelar);

        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(buttonsPanel, gbc);

        add(panel);
        setVisible(true);
    }

    private void guardarCambios() {
        currentUser.setNombre(nombreField.getText().trim());
        currentUser.setApellido(apellidoField.getText().trim());
        currentUser.setCiudad(ciudadField.getText().trim());
        currentUser.setCorreo(correoField.getText().trim());
        currentUser.setTimezone((String) timezoneCombo.getSelectedItem());

        AuthService.updateUser(currentUser);

        JOptionPane.showMessageDialog(this, "Datos guardados correctamente.");
        dispose();
    }
    private boolean validarCampos() {
        if (nombreField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo 'Nombre' no puede estar vacío.");
            return false;
        }
        if (apellidoField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo 'Apellido' no puede estar vacío.");
            return false;
        }
        if (ciudadField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo 'Ciudad' no puede estar vacío.");
            return false;
        }
        if (correoField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo 'Correo' no puede estar vacío.");
            return false;
        }
    
        String correo = correoField.getText().trim();
        // Expresión regular básica para validar email
        if (!correo.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
            JOptionPane.showMessageDialog(this, "El correo ingresado no tiene un formato válido.");
            return false;
        }
    
        return true;
    }
    
}