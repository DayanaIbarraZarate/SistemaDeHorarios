
package ui;

import model.Horario;
import service.HorarioService;
import session.Session;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class HorarioViewerPanel extends JPanel {
    public HorarioViewerPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(230, 245, 255));

        String tipo = Session.getCurrentUser().getRole().toLowerCase();
        String id = Session.getCurrentUser().getId();

        Horario horario = HorarioService.cargarHorario(tipo, id);

        JTable tabla = new JTable(new DefaultTableModel(horario.getMatriz(), horario.getEncabezados()) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        tabla.setRowHeight(35);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabla.setGridColor(Color.LIGHT_GRAY);

        // Color turquesa claro para celdas
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setBackground(new Color(204, 255, 255));
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JLabel label = new JLabel("Horario de: " + Session.getCurrentUser().getNombre(), SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(label, BorderLayout.NORTH);
    }
}