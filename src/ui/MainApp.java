package ui;

import session.Session;

import javax.swing.*;
import java.awt.*;
import java.util.List;
//import ui.ProfesorSchedulePanel; // D importar horario 
import ui.EstudianteSchdulePanel;


public class MainApp extends JFrame {
<<<<<<<<< Temporary merge branch 1
    private JPanel contentPanel;
=========
     
    private JPanel contentPanel; // D Panel central donde cambia el contenido


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

        List<String> menuList = new java.util.ArrayList<>();
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

        // Layout
=========
        
        // Área de contenido
        // JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel = new JPanel(new BorderLayout());  // D

        contentPanel.add(new JLabel("<html><h1>Bienvenido, " + displayName + "!</h1><p>Seleccione una opción del menú</p></html>"),
            BorderLayout.CENTER);
        
        // Layout principal
>>>>>>>>> Temporary merge branch 2
        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void handleMenuClick(String menuItem) {
<<<<<<<<< Temporary merge branch 1
    switch (menuItem) {
        case "Perfil":
    // Abrir ventana perfil solo si es estudiante
            if ("ESTUDIANTE".equals(Session.getCurrentUser().getRole())) {
               new PerfilEstudiante();
            } else {
        JOptionPane.showMessageDialog(this, "Pantalla de Perfil para " + Session.getCurrentUser().getRole());
            }
            break;

        case "Alertas":
            showMessage("Pantalla de Alertas (en construcción)");
            break;
        case "Horario":
            showMessage("Pantalla de Horario (en construcción)");
            break;
        case "Cerrar Sesión":
            Session.logout();
            new LoginWindow();
            dispose();
            break;
=========
        switch (menuItem) {
            case "Perfil":
            contentPanel.removeAll();
            contentPanel.add(new ProfesorProfilePanel(), BorderLayout.CENTER);
            contentPanel.revalidate();
            contentPanel.repaint();
                break;
            case "Alertas":
                JOptionPane.showMessageDialog(this, "Pantalla de Alertas");
                break;
            case "Horario":  //D 
                contentPanel.removeAll();
                contentPanel.add(new ProfesorSchedulePanel(), BorderLayout.CENTER);
                contentPanel.revalidate();
                contentPanel.repaint();
            break;
            
            case "Cerrar Sesión":
                Session.logout();
                new LoginWindow();
                dispose();
                break;
        }
>>>>>>>>> Temporary merge branch 2
    }
}
 
    
    
    private void showMessage(String msg) {
        contentPanel.removeAll();
        JLabel label = new JLabel("<html><div style='text-align:center;'><h2>" + msg + "</h2></div></html>", SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        contentPanel.add(label, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}
