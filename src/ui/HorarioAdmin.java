package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
//import java.awt.event.*;

public class HorarioAdmin extends JFrame {
 //liz 
 private JTable table;
private DefaultTableModel model;

    public HorarioAdmin() {
        setTitle("Horario del Administrador");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Color de fondo 
        Color fondo = new Color(119, 167, 177); 
        getContentPane().setBackground(fondo);
        setLayout(new BorderLayout(10, 10));

        //  Encabezado 
        JLabel label = new JLabel("Horario: Administrador");
        label.setFont(new Font("Segoe UI", Font.BOLD, 22));
        label.setForeground(Color.DARK_GRAY);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(new EmptyBorder(20, 10, 10, 10));
        add(label, BorderLayout.NORTH);

        // Panel de botones 
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelButtons.setBackground(fondo);

        JButton addMateria = new JButton("Agregar Materia");
        JButton notificar = new JButton("Notificar");
        JButton cerrarSesion = new JButton("Cerrar Sesión");

        // Eventos
        cerrarSesion.addActionListener(e -> {
            dispose(); // cerrar ventana actual
            new LoginWindow();
            JOptionPane.showMessageDialog(null, "Volviendo al login...");
        });
        //liz puso estas 2 lineas
        
         notificar.addActionListener(e -> new NotificacionWindow());
        addMateria.addActionListener(e -> {
    FormularioAgregarMateria form = new FormularioAgregarMateria();
    form.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosed(java.awt.event.WindowEvent e) {
            actualizarTabla(); // Recargar la tabla desde JSON al cerrar el formulario
        }
    });
});



        // Agregar botones
        panelButtons.add(addMateria);
        panelButtons.add(notificar);
        panelButtons.add(cerrarSesion);
        add(panelButtons, BorderLayout.BEFORE_FIRST_LINE);

        //  Tabla de horario 
        String[] columnNames = {"Horario", "LUNES", "MARTES", "MIÉRCOLES", "JUEVES", "VIERNES", "SÁBADO"};
        Object[][] data = {
                {"06:45-8:15", "", "", "", "", "", ""},
                {"08:15-09:45", "", "", "", "", "", ""},
                {"09:45-11:15", "", "", "", "", "", ""},
                {"11:15-12:45", "", "", "", "", "", ""},
                {"12:45-14:15", "", "", "", "", "", ""},
                {"14:15-15:45", "", "", "", "", "", ""},
                {"15:45-17:15", "", "", "", "", "", ""},
                {"17:15-18:45", "", "", "", "", "", ""},
                {"18:45-20:15", "", "", "", "", "", ""},
                {"20:15-21:45", "", "", "", "", "", ""}
        };

        //liz JTable table = new JTable(new DefaultTableModel(data, columnNames));
        model = new DefaultTableModel(data, columnNames);
        table = new JTable(model);

        table.setRowHeight(40);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        // Panel central redondeado
        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.setBackground(fondo);
        panelCentro.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
        panelCentro.add(scrollPane, BorderLayout.CENTER);
        add(panelCentro, BorderLayout.CENTER);
        actualizarTabla(); // Cargar datos del JSON al iniciar *añadido por liz
        setVisible(true);
    }
private void actualizarTabla() {
    // Limpiar todas las celdas excepto la columna de hora
    for (int i = 0; i < model.getRowCount(); i++) {
        for (int j = 1; j < model.getColumnCount(); j++) {
            model.setValueAt("", i, j);
        }
    }

    java.util.List<model.Schedule> lista = service.ScheduleService.getAllSchedules();
    for (model.Schedule s : lista) {
        int col = switch (s.dia.toUpperCase()) {
            case "LUNES" -> 1;
            case "MARTES" -> 2;
            case "MIÉRCOLES" -> 3;
            case "JUEVES" -> 4;
            case "VIERNES" -> 5;
            case "SÁBADO" -> 6;
            default -> -1;
        };
        if (col == -1) continue;

        String rango = s.horaInicio + "-" + s.horaFin;
        for (int row = 0; row < model.getRowCount(); row++) {
            if (model.getValueAt(row, 0).equals(rango)) {
                String texto = s.materia;
                if (s.profesor != null && !s.profesor.isEmpty()) {
                    texto += " (" + s.profesor + ")";
                }
                model.setValueAt(texto, row, col);
                break;
            }
        }
    }
}

    
}
