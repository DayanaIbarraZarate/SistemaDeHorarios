package ui;

import service.ScheduleService;

import javax.swing.*;
import java.awt.*;
//import java.util.Arrays;

public class FormularioAgregarMateria extends JFrame {

    public FormularioAgregarMateria() {
        setTitle("Agregar Materia al Horario");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6, 2, 10, 10));

        // Opciones
        String[] dias = {"LUNES", "MARTES", "MIÉRCOLES", "JUEVES", "VIERNES", "SÁBADO"};
        String[] horas = {
                "06:45", "08:15", "09:45", "11:15", "12:45",
                "14:15", "15:45", "17:15", "18:45", "20:15"
        };

        // Componentes
        JComboBox<String> diaCombo = new JComboBox<>(dias);
        JComboBox<String> horaInicioCombo = new JComboBox<>(horas);
        JComboBox<String> horaFinCombo = new JComboBox<>(horas);
        JTextField materiaField = new JTextField();
        JTextField profesorField = new JTextField();

        JButton guardar = new JButton("Guardar");

        // Agregar componentes al formulario
        add(new JLabel("Día:"));
        add(diaCombo);
        add(new JLabel("Hora inicio:"));
        add(horaInicioCombo);
        add(new JLabel("Hora fin:"));
        add(horaFinCombo);
        add(new JLabel("Materia:"));
        add(materiaField);
        add(new JLabel("Profesor:"));
        add(profesorField);
        add(new JLabel(""));
        add(guardar);

        // Evento del botón Guardar
        guardar.addActionListener(e -> {
            String dia = (String) diaCombo.getSelectedItem();
            String horaInicio = (String) horaInicioCombo.getSelectedItem();
            String horaFin = (String) horaFinCombo.getSelectedItem();
            String materia = materiaField.getText().trim();
           // String profesor = profesorField.getText().trim();

            if (materia.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar una materia.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Guardar en JSON
            ScheduleService.addSchedule(dia, horaInicio, horaFin, materia);
            JOptionPane.showMessageDialog(this, "Materia agregada exitosamente.");

            dispose(); // cerrar el formulario
        });

        setVisible(true);
    }
}