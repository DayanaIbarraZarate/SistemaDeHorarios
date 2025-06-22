package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
//import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ProfesorSchedulePanel extends JPanel {

    private JTable scheduleTable;
    private final Color backgroundColor = new Color(230, 245, 255); // Celeste pastel

    public ProfesorSchedulePanel() {
        // Parte visual del fondo
        setOpaque(true);
        setLayout(new BorderLayout());

        //Título visual
        JLabel titulo = new JLabel("Horario", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 24));
        titulo.setForeground(new Color(30, 30, 30));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(titulo, BorderLayout.NORTH);

        //  tabla
        String[] columnNames = {"Hora", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"};
        String[][] data = generarFilasHorario();

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        scheduleTable = new JTable(model);
        scheduleTable.setRowHeight(50);
        scheduleTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        scheduleTable.setForeground(new Color(40, 40, 40));
        scheduleTable.setGridColor(new Color(220, 220, 220));
        scheduleTable.setOpaque(false);
        scheduleTable.setBackground(new Color(0, 0, 0, 0));
        scheduleTable.setFillsViewportHeight(true);

        // Estilo de encabezados
        //JTableHeader header = scheduleTable.getTableHeader();
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setBackground(new Color(190, 220, 250));
                label.setForeground(new Color(40, 40, 40));
                label.setFont(new Font("Serif", Font.BOLD, 14));
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setOpaque(true);
                return label;
            }
        };
        for (int i = 0; i < scheduleTable.getColumnCount(); i++) {
            scheduleTable.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
            scheduleTable.getColumnModel().getColumn(i).setCellRenderer(getRoundedCellRenderer()); // Redondeado
        }

        //  Scroll con fondo transparente
        JScrollPane scrollPane = new JScrollPane(scheduleTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        add(scrollPane, BorderLayout.CENTER);
    }

    //  Genera las filas del horario
    private String[][] generarFilasHorario() {
        List<String[]> rows = new ArrayList<>();
        LocalTime horaInicio = LocalTime.of(7, 0);
        LocalTime horaFin = LocalTime.of(20, 0);

        while (horaInicio.isBefore(horaFin)) {
            LocalTime siguienteHora = horaInicio.plusMinutes(90);
            String rango = horaInicio + " - " + siguienteHora;
            rows.add(new String[]{rango, "", "", "", "", "", ""});
            horaInicio = siguienteHora;
        }
        return rows.toArray(new String[0][0]);
    }

    // Renderizador de celdas redondeadas (copiado desde visualPanel)
    private TableCellRenderer getRoundedCellRenderer() {
        return new DefaultTableCellRenderer() {
                public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel cell = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                cell.setOpaque(true);
                cell.setHorizontalAlignment(CENTER);
                cell.setForeground(new Color(40, 40, 40));
                cell.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.WHITE, 3, true),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)
                ));
                return cell;
            }
        };
    }

    // Utilidades opcionales si necesitas crear botones o campos en el futuro
    public static JTextField styledTextField(String placeholder) {
        JTextField field = new JTextField();
        field.setText(placeholder);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBackground(new Color(255, 230, 230));
        field.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        return field;
    }

    public static JButton styledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(bgColor);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return button;
    }

    // Pinta el fondo (desde visualPanel)
   
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(backgroundColor);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.dispose();
    }
}
