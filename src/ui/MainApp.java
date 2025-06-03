package ui;

import session.Session;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainApp extends JFrame {
    public MainApp() {
        setTitle("Sistema de Horarios");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Obtener usuario actual y nombre de pantalla
        String role = Session.getCurrentUser().getRole();
        String displayName = Session.getCurrentUser().getNombre();
        if (displayName == null || displayName.isEmpty()) {
            displayName = Session.getCurrentUser().getUsername();
        }
        // Crear sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(240, 240, 240));
        sidebar.setPreferredSize(new Dimension(150, getHeight()));
        
        // Botones del sidebar según rol
        List<String> menuList = new java.util.ArrayList<>();
        if ("ADMIN".equals(role)) {
            menuList.add("Horario");
            menuList.add("Cerrar Sesión");
        } else if ("PROFESOR".equals(role)) {
            menuList.add("Perfil");
            menuList.add("Alertas");
            menuList.add("Horario");
            menuList.add("Cerrar Sesión");
        } else {
            // ESTUDIANTE u otro
            menuList.add("Perfil");
            menuList.add("Alertas");
            menuList.add("Horario");
            menuList.add("Cerrar Sesión");
        }
        for (String item : menuList) {
            JButton button = new JButton(item);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setMaximumSize(new Dimension(140, 40));
            button.addActionListener(_ -> handleMenuClick(item));
            sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
            sidebar.add(button);
        }
        
        // Área de contenido
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(new JLabel("<html><h1>Bienvenido, " + displayName + "!</h1><p>Seleccione una opción del menú</p></html>"),
            BorderLayout.CENTER);
        
        // Layout principal
        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void handleMenuClick(String menuItem) {
        switch (menuItem) {
            case "Perfil":
                JOptionPane.showMessageDialog(this, "Pantalla de Perfil");
                break;
            case "Alertas":
                JOptionPane.showMessageDialog(this, "Pantalla de Alertas");
                break;
            case "Horario":
                JOptionPane.showMessageDialog(this, "Pantalla de Horario");
                break;
            case "Cerrar Sesión":
                Session.logout();
                new LoginWindow();
                dispose();
                break;
        }
    }
}