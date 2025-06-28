package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
//import java.awt.event.*;

public class HorarioAdmin extends JFrame {
 
 private JTable table;
private DefaultTableModel model;
private FormularioAgregarMateria formMateria; // <-- Añade esta línea añadido por liz para poder cerrar las clases hijas
private NotificacionWindow ventanaNotificacion;//puesto por liz para que se cierre las notificaciones 


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

        cerrarSesion.addActionListener(e -> {
    // Cierra FormularioAgregarMateria si está abierto
    if (formMateria != null) {
        formMateria.dispose();
    }

    // Cierra NotificacionWindow si está abierto
    if (ventanaNotificacion != null) {
        ventanaNotificacion.dispose();
    }

    dispose(); // cierra ventana principal
    new LoginWindow();
    JOptionPane.showMessageDialog(null, "Volviendo al login...");
});



        //liz puso estas 2 lineas
        
        // notificar.addActionListener(e -> new NotificacionWindow());
notificar.addActionListener(e -> {
    ventanaNotificacion = new NotificacionWindow();
    ventanaNotificacion.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosed(java.awt.event.WindowEvent e) {
            ventanaNotificacion = null; // limpiar referencia
        }
    });
});

            addMateria.addActionListener(e -> {
    formMateria = new FormularioAgregarMateria();
    formMateria.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosed(java.awt.event.WindowEvent e) {
            actualizarTabla();
            formMateria = null; // limpiar referencia
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
                {"06:45-08:15", "", "", "", "", "", ""},
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
       //liz añadio esto 
        table.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Columna 0 es la de horarios, no cambiar color ahí
        if (column == 0) {
            c.setBackground(Color.WHITE);
            return c;
        }

        String texto = (value == null) ? "" : value.toString().toLowerCase();

        if (texto.contains("prof:")) {
            c.setBackground(new Color(200, 180, 255));  // color lila suave
        } else if (texto.contains("est:")) {
            c.setBackground(new Color(255, 200, 220));  // color rosado suave
        } else {
            c.setBackground(Color.WHITE);  // sin texto o distinto, blanco
        }

        return c;
    }
});
//hasta aqui liz añadio



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

        String tipoPrefijo = s.tipo.equalsIgnoreCase("profesor") ? "prof:" : "est:";

        for (int row = 0; row < model.getRowCount(); row++) {
            if (model.getValueAt(row, 0).equals(rango)) {
                String actual = (String) model.getValueAt(row, col);

                String texto = tipoPrefijo + s.materia;

                if (!actual.isEmpty()) {
                    texto = actual + ", " + texto;
                }

                model.setValueAt(texto, row, col);
                break;
            }
        }
    


    

        
        
    }
}

    
}
