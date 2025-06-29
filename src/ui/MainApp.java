package ui;

import session.Session;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
//import ui.ProfesorSchedulePanel; // D importar horario 

public class MainApp extends JFrame {   
    private JPanel contentPanel; // Panel central donde cambia el contenido

    // Variable para controlar la ventana PerfilEstudiante abierta
    private PerfilEstudiante perfilEstudiante;

    public MainApp() {
        setTitle("Sistema de Horarios - Estudiante");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String role = Session.getCurrentUser().getRole();
        String displayName = Session.getCurrentUser().getNombre();
        if (displayName == null || displayName.isEmpty()) {
            displayName = Session.getCurrentUser().getUsername();
        }

        // Colores
        Color celesteFondo = new Color(200, 230, 255);
        Color celesteBoton = new Color(100, 180, 255);
        Color blanco = Color.WHITE;

        // Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(celesteFondo);
        sidebar.setPreferredSize(new Dimension(180, getHeight()));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        List<String> menuList = new ArrayList<>();
        if ("ADMIN".equals(role)) {
            menuList.add("Horario");
            menuList.add("Cerrar Sesión");
        } else if ("PROFESOR".equals(role) || "ESTUDIANTE".equals(role)) {
            menuList.add("Perfil");
            menuList.add("Alertas");
            menuList.add("Horario");
            menuList.add("Cerrar Sesión");
        }

        for (String item : menuList) {
            JButton button = new JButton(item);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setMaximumSize(new Dimension(160, 40));
            button.setBackground(celesteBoton);
            button.setForeground(blanco);
            button.setFocusPainted(false);
            button.setFont(new Font("Segoe UI", Font.BOLD, 14));
            button.addActionListener(e -> handleMenuClick(item));
            sidebar.add(Box.createRigidArea(new Dimension(0, 15)));
            sidebar.add(button);
        }

        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        JLabel welcomeLabel = new JLabel("<html><div style='text-align:center;'><h1>¡Bienvenid@, " 
            + displayName + "!</h1><p>Selecciona una opción del menú</p></div></html>", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        contentPanel.add(welcomeLabel, BorderLayout.CENTER);

        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void handleMenuClick(String menuItem) {
        switch (menuItem) {
            case "Perfil":
                if ("ESTUDIANTE".equals(Session.getCurrentUser().getRole())) {
                    if (perfilEstudiante == null || !perfilEstudiante.isDisplayable()) {
                        perfilEstudiante = new PerfilEstudiante();
                    } else {
                        perfilEstudiante.toFront();
                        perfilEstudiante.requestFocus();
                    }
                } else if ("PROFESOR".equals(Session.getCurrentUser().getRole())) {
                    contentPanel.removeAll();
                    contentPanel.add(new ProfesorProfilePanel(), BorderLayout.CENTER);
                    contentPanel.revalidate();
                    contentPanel.repaint();
                } else {
                    JOptionPane.showMessageDialog(this, "Pantalla de Perfil para " + Session.getCurrentUser().getRole());
                }
                break;

            case "Alertas":
                String rol = Session.getCurrentUser().getRole();
                List<model.Alert> todas = service.AlertService.getAllAlerts();
                List<String> mensajes = new ArrayList<>();

                for (model.Alert alerta : todas) {
                    if (alerta.getDestinatarios().contains(rol)) {
                        mensajes.add(alerta.getMensaje());
                    }
                }

                contentPanel.removeAll();
                contentPanel.add(new AlertasProfesor(mensajes), BorderLayout.CENTER);
                contentPanel.revalidate();
                contentPanel.repaint();
                break;

            case "Horario":
                if ("PROFESOR".equals(Session.getCurrentUser().getRole())) {
                    contentPanel.removeAll();
                    contentPanel.add(new ProfesorSchedulePanel(), BorderLayout.CENTER);
                    contentPanel.revalidate();
                    contentPanel.repaint();
                } else {
                    JOptionPane.showMessageDialog(this, "Pantalla de Horario (en construcción)");
                }
                break;

            case "Cerrar Sesión":
                int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas cerrar sesión?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    if (perfilEstudiante != null) {
                        perfilEstudiante.dispose();
                        perfilEstudiante = null;
                    }
                    Session.logout();
                    this.dispose();
                    new LoginWindow().setVisible(true);
                }
                break;
        }
    }
}
