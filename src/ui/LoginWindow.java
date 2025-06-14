package ui;

import model.User;
import service.AuthService;
import session.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginWindow extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginWindow() {
        setTitle("Login - Sistema de Horarios");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Componentes
        panel.add(new JLabel("Usuario:"));
        usernameField = new JTextField();
        panel.add(usernameField);
        
        panel.add(new JLabel("Contraseña:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);
        
        // Botones
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(this::handleLogin);
        
        JButton signupButton = new JButton("Registrarse");
        signupButton.addActionListener(this::handleSignup);
        
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonPanel.add(loginButton);
        buttonPanel.add(signupButton);
        
        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void handleLogin(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        
        User user = AuthService.login(username, password);
        if (user != null) {
            Session.setCurrentUser(user);
            String displayName = user.getNombre();
            if (displayName == null || displayName.isEmpty()) {
                displayName = user.getUsername();
            }
            JOptionPane.showMessageDialog(this, "¡Bienvenid@, " + displayName + "!");
             // Según el rol, abre la pantalla adecuada
            if ("ADMIN".equals(user.getRole())) {
                new HorarioAdmin();
            } else if ("PROFESOR".equals(user.getRole())) {
                new MainApp();
            } else if ("ESTUDIANTE".equals(user.getRole())) {
                new MainApp();
            }

            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void handleSignup(ActionEvent e) {
        // Campos de registro
        JTextField signupUser = new JTextField();
        JPasswordField signupPass = new JPasswordField();
        JComboBox<String> roleCombo = new JComboBox<>(new String[]{"ESTUDIANTE", "PROFESOR"});
        JPanel signupPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        signupPanel.add(new JLabel("Username:"));
        signupPanel.add(signupUser);
        signupPanel.add(new JLabel("Password:"));
        signupPanel.add(signupPass);
        signupPanel.add(new JLabel("Rol:"));
        signupPanel.add(roleCombo);
        
        int result = JOptionPane.showConfirmDialog(this, signupPanel, "Registro de Usuario", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String username = signupUser.getText();
            String password = new String(signupPass.getPassword());
            boolean success = AuthService.signUp(username, password, (String) roleCombo.getSelectedItem());
            
            if (success) {
                JOptionPane.showMessageDialog(this, "¡Registro exitoso!");
            } else {
                JOptionPane.showMessageDialog(this, "Usuario ya existe", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}