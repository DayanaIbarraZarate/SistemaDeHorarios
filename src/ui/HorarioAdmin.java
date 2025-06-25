package ui;

import model.User;
import service.UserService;
import service.HorarioService;
import model.Horario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.List;
//clase totalmente modificada por liz
public class HorarioAdmin extends JFrame {
    private JPanel menuPanel;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    // Tablas para poder acceder a ellas desde los botones
    private JTable tablaEstudiantes;
    private JTable tablaProfesores;

    public HorarioAdmin() {
        setTitle("Sistema de Gestión - Administrador");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Botón hamburguesa top
        JButton btnMenu = new JButton("\u2630");
        btnMenu.setFocusPainted(false);
        btnMenu.setPreferredSize(new Dimension(50, 40));
        btnMenu.setFont(new Font("Segoe UI", Font.BOLD, 20));
        btnMenu.setBackground(new Color(30, 144, 255));
        btnMenu.setForeground(Color.WHITE);
        btnMenu.setBorderPainted(false);
        btnMenu.setOpaque(true);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(btnMenu);
        topPanel.setBackground(new Color(30, 144, 255));
        add(topPanel, BorderLayout.NORTH);

        // Menú lateral
        menuPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        menuPanel.setPreferredSize(new Dimension(180, 0));
        menuPanel.setBackground(new Color(240, 248, 255));
        menuPanel.setVisible(false);

        JButton btnEstudiantes = crearBotonMenu("Estudiantes");
        JButton btnProfesores = crearBotonMenu("Profesores");
        JButton btnNotificaciones = crearBotonMenu("Notificaciones");
        JButton btnCerrar = crearBotonMenu("Cerrar Sesión");

        menuPanel.add(btnEstudiantes);
        menuPanel.add(btnProfesores);
        menuPanel.add(btnNotificaciones);
        menuPanel.add(new JLabel());
        menuPanel.add(btnCerrar);

        add(menuPanel, BorderLayout.WEST);

        // Panel principal con CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Panel bienvenida
        JPanel panelBienvenida = new JPanel(new BorderLayout());
        JLabel labelBienvenida = new JLabel("Bienvenido Administrador", SwingConstants.CENTER);
        labelBienvenida.setFont(new Font("Segoe UI", Font.BOLD, 28));
        panelBienvenida.setBackground(Color.WHITE);
        panelBienvenida.add(labelBienvenida, BorderLayout.CENTER);

        // Panel Estudiantes con tabla y botones
        JPanel panelEstudiantes = crearPanelUsuarios("estudiante");

        // Panel Profesores con tabla y botones
        JPanel panelProfesores = crearPanelUsuarios("profesor");

        // Panel Notificaciones simple
        JPanel panelNotificaciones = new JPanel(new BorderLayout());
        panelNotificaciones.setBackground(Color.WHITE);
        JLabel labelNotificaciones = new JLabel("Panel Notificaciones", SwingConstants.CENTER);
        labelNotificaciones.setFont(new Font("Segoe UI", Font.BOLD, 22));
        panelNotificaciones.add(labelNotificaciones, BorderLayout.CENTER);

        mainPanel.add(panelBienvenida, "bienvenida");
        mainPanel.add(panelEstudiantes, "estudiantes");
        mainPanel.add(panelProfesores, "profesores");
        mainPanel.add(panelNotificaciones, "notificaciones");

        add(mainPanel, BorderLayout.CENTER);

        cardLayout.show(mainPanel, "bienvenida");

        // Eventos menú lateral
        btnMenu.addActionListener(e -> menuPanel.setVisible(!menuPanel.isVisible()));

        btnEstudiantes.addActionListener(e -> {
            cardLayout.show(mainPanel, "estudiantes");
            menuPanel.setVisible(false);
        });

        btnProfesores.addActionListener(e -> {
            cardLayout.show(mainPanel, "profesores");
            menuPanel.setVisible(false);
        });

        btnNotificaciones.addActionListener(e -> {
            new NotificacionWindow();
            dispose();  
            menuPanel.setVisible(false);
        });

        btnCerrar.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro que quieres salir?", "Cerrar sesión", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        setVisible(true);
    }

    private JPanel crearPanelUsuarios(String tipoUsuario) {
        List<User> usuarios = UserService.getUsersByRole(tipoUsuario);

        Object[][] datos = new Object[usuarios.size()][4];
        for (int i = 0; i < usuarios.size(); i++) {
            User u = usuarios.get(i);
            datos[i][0] = u.getId();
            datos[i][1] = u.getNombre();
            datos[i][2] = tipoUsuario.equals("estudiante") ? "Carrera X" : u.getCiudad(); // o alguna info distinta
            datos[i][3] = u.getCorreo();
        }

        String[] columnas = new String[]{"ID", "Nombre", tipoUsuario.equals("estudiante") ? "Carrera" : "Ciudad", "Correo"};

        DefaultTableModel modelo = new DefaultTableModel(datos, columnas) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tabla = new JTable(modelo);
        tabla.setFillsViewportHeight(true);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        tabla.setRowHeight(28);

        JScrollPane scrollPane = new JScrollPane(tabla);

        // Guardar referencias para uso posterior
        if (tipoUsuario.equals("estudiante")) {
            tablaEstudiantes = tabla;
        } else if (tipoUsuario.equals("profesor")) {
            tablaProfesores = tabla;
        }

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panelBotones.setBackground(Color.WHITE);

        // Botones: Visualizar Perfil, Crear Horario, Editar Horario, Eliminar Horario
        JButton btnVisualizarPerfil = crearBotonPanel("Visualizar Perfil");
        JButton btnCrearHorario = crearBotonPanel("Crear Horario");
        JButton btnEditarHorario = crearBotonPanel("Editar Horario");
        JButton btnEliminarHorario = crearBotonPanel("Eliminar Horario");

        panelBotones.add(btnVisualizarPerfil);
        panelBotones.add(btnCrearHorario);
        panelBotones.add(btnEditarHorario);
        panelBotones.add(btnEliminarHorario);

        panel.add(panelBotones, BorderLayout.SOUTH);

        // ActionListener botones

        btnVisualizarPerfil.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona un usuario primero.", "Atención", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String id = tabla.getValueAt(fila, 0).toString();
            String nombre = tabla.getValueAt(fila, 1).toString();
            JOptionPane.showMessageDialog(this,
                    "Perfil de usuario:\nID: " + id + "\nNombre: " + nombre + "\nTipo: " + tipoUsuario,
                    "Perfil", JOptionPane.INFORMATION_MESSAGE);
        });

        btnCrearHorario.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona un usuario primero.", "Atención", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String id = tabla.getValueAt(fila, 0).toString();
            new HorarioEditor(tipoUsuario, id);
        });

        btnEditarHorario.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona un usuario primero.", "Atención", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String id = tabla.getValueAt(fila, 0).toString();
            // Abrir el editor (igual que crear porque carga horario si existe)
            new HorarioEditor(tipoUsuario, id);
        });

        btnEliminarHorario.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona un usuario primero.", "Atención", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String id = tabla.getValueAt(fila, 0).toString();
            String rutaArchivo = "SistemaHorarios/data/horario_" + tipoUsuario + "_" + id + ".txt";

            File archivo = new File(rutaArchivo);
            if (archivo.exists()) {
                int confirmar = JOptionPane.showConfirmDialog(this,
                        "¿Seguro que quieres eliminar el horario de este usuario?",
                        "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                if (confirmar == JOptionPane.YES_OPTION) {
                    if (archivo.delete()) {
                        JOptionPane.showMessageDialog(this, "Horario eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "No se pudo eliminar el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "No existe un horario para este usuario.", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        return panel;
    }

    private JButton crearBotonMenu(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btn.setFocusPainted(false);
        btn.setBackground(new Color(0, 102, 204));
        btn.setForeground(Color.WHITE);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(0, 122, 255));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(0, 102, 204));
            }
        });

        return btn;
    }

    private JButton crearBotonPanel(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setBackground(new Color(0, 123, 255));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(140, 40));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(10, 149, 255));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(0, 123, 255));
            }
        });

        return btn;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new HorarioAdmin().setVisible(true));
    }
}