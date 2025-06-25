package ui;

import model.Horario;
import service.HorarioService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class HorarioEditor extends JFrame {
    private Horario horario;
    private JTable tablaHorario;
    private String tipoUsuario; // "profesor" o "estudiante"
    private String idUsuario;

    public HorarioEditor(String tipoUsuario, String idUsuario) {
        this.tipoUsuario = tipoUsuario;
        this.idUsuario = idUsuario;

        setTitle("Editor de Horario - " + tipoUsuario.toUpperCase() + " [" + idUsuario + "]");
        setSize(900, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Cargar horario si ya existe
        horario = HorarioService.cargarHorario(tipoUsuario, idUsuario);

        // Crear modelo para JTable
        DefaultTableModel model = new DefaultTableModel(horario.getMatriz(), horario.getEncabezados()) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0; // La columna de horas no es editable
            }
        };

        tablaHorario = new JTable(model);
        tablaHorario.setRowHeight(35);

        JScrollPane scrollPane = new JScrollPane(tablaHorario);
        add(scrollPane, BorderLayout.CENTER);

        JButton btnGuardar = new JButton("Guardar Horario");
        btnGuardar.addActionListener(this::guardarHorario);

        JPanel panelBoton = new JPanel();
        panelBoton.add(btnGuardar);
        add(panelBoton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void guardarHorario(ActionEvent e) {
        DefaultTableModel model = (DefaultTableModel) tablaHorario.getModel();

        for (int fila = 0; fila < model.getRowCount(); fila++) {
            for (int col = 1; col < model.getColumnCount(); col++) { // col = 0 es la hora
                String valor = (String) model.getValueAt(fila, col);
                horario.setCelda(fila, col, valor == null ? "" : valor.trim());
            }
        }

        HorarioService.guardarHorario(horario, tipoUsuario, idUsuario);
        JOptionPane.showMessageDialog(this, "✅ Horario guardado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
}
