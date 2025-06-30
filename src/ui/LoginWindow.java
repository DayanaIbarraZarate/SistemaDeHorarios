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
        setSize(400, 250); // tamaño mediano
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Colores
        Color darkBg = new Color(34, 34, 34);
        Color lightField = new Color(240, 240, 240);
        Color lightButton = new Color(200, 200, 255);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        panel.setBackground(darkBg);

        // Componentes
        JLabel userLabel = new JLabel("Usuario:");
        userLabel.setForeground(Color.WHITE);
        panel.add(userLabel);

        usernameField = new JTextField();
        usernameField.setToolTipText("Ingrese su nombre de usuario");
        usernameField.setBackground(lightField);
        panel.add(usernameField);

        JLabel passLabel = new JLabel("Contraseña:");
        passLabel.setForeground(Color.WHITE);
        panel.add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setToolTipText("Ingrese su contraseña");
        passwordField.setBackground(lightField);
        panel.add(passwordField);

        // Botones
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(lightButton);
        loginButton.setForeground(Color.BLACK);
        loginButton.addActionListener(this::handleLogin);

        JButton signupButton = new JButton("Registrarse");
        signupButton.setBackground(lightButton);
        signupButton.setForeground(Color.BLACK);
        signupButton.addActionListener(this::handleSignup);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonPanel.setBackground(darkBg);
        buttonPanel.add(loginButton);
        buttonPanel.add(signupButton);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        getContentPane().setBackground(darkBg);
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