
package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class EstudianteSchdulePanel extends JPanel {

    private JTable scheduleTable;
    private final Color backgroundColor = new Color(255, 245, 235); // Naranja pastel suave

    public EstudianteSchdulePanel() {
        setOpaque(true);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Horario Estudiantil", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 24));
        titulo.setForeground(new Color(30, 30, 30));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(titulo, BorderLayout.NORTH);

        String[] columnNames = {"Hora", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"};
        String[][] data = generarFilasHorarioEstudiante();

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

        JTableHeader header = scheduleTable.getTableHeader();
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setBackground(new Color(255, 210, 180));
                label.setForeground(new Color(40, 40, 40));
                label.setFont(new Font("Serif", Font.BOLD, 14));
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setOpaque(true);
                return label;
            }
        };
        for (int i = 0; i < scheduleTable.getColumnCount(); i++) {
            scheduleTable.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
            scheduleTable.getColumnModel().getColumn(i).setCellRenderer(getRoundedCellRenderer());
        }

        JScrollPane scrollPane = new JScrollPane(scheduleTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        add(scrollPane, BorderLayout.CENTER);
    }

    private String[][] generarFilasHorarioEstudiante() {
        List<String[]> rows = new ArrayList<>();
        LocalTime horaInicio = LocalTime.of(6, 45);
        LocalTime horaFin = LocalTime.of(21, 45);

        while (horaInicio.isBefore(horaFin)) {
            LocalTime siguienteHora = horaInicio.plusMinutes(90);
            String rango = horaInicio + " - " + siguienteHora;
            rows.add(new String[]{rango, "", "", "", "", "", ""});
            horaInicio = siguienteHora;
        }

        return rows.toArray(new String[0][0]);
    }

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

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(backgroundColor);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.dispose();
    }
}
