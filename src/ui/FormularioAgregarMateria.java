
package ui;

import service.ScheduleService;

import javax.swing.*;
import java.awt.*;

public class FormularioAgregarMateria extends JFrame {

    public FormularioAgregarMateria() {
        setTitle("Agregar Materia al Horario");
        setSize(450, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(10, 2, 10, 10));

        String[] dias = {"LUNES", "MARTES", "MIÉRCOLES", "JUEVES", "VIERNES", "SÁBADO"};
        String[] tipos = {"profesor", "estudiante"};

        String[] horasInicio = {
                "06:45", "08:15", "09:45", "11:15",
                "12:45", "14:15", "15:45", "17:15",
                "18:45", "20:15"
        };

        String[] horasFin = {
                "08:15", "09:45", "11:15", "12:45",
                "14:15", "15:45", "17:15", "18:45",
                "20:15", "21:45"
        };

        JComboBox<String> diaBox = new JComboBox<>(dias);
        JComboBox<String> horaInicioBox = new JComboBox<>(horasInicio);
        JComboBox<String> horaFinBox = new JComboBox<>(horasFin);
        JTextField materiaField = new JTextField();
        
        JComboBox<String> tipoBox = new JComboBox<>(tipos);

        add(new JLabel("Día:"));
        add(diaBox);

        add(new JLabel("Hora Inicio:"));
        add(horaInicioBox);

        add(new JLabel("Hora Fin:"));
        add(horaFinBox);

        add(new JLabel("Materia:"));
        add(materiaField);

       
        add(new JLabel("Tipo:"));
        add(tipoBox);



        JButton guardarBtn = new JButton("Guardar");
        guardarBtn.addActionListener(e -> {
            String dia = diaBox.getSelectedItem().toString();
            String horaInicio = horaInicioBox.getSelectedItem().toString();
            String horaFin = horaFinBox.getSelectedItem().toString();
            String materia = materiaField.getText().trim();
           
            String tipo = tipoBox.getSelectedItem().toString();

            if (materia.isEmpty()) {
                JOptionPane.showMessageDialog(this, "La materia es obligatoria.");
                return;
            }

           ScheduleService.addSchedule(dia, horaInicio, horaFin, materia, tipo);

            JOptionPane.showMessageDialog(this, "Materia agregada correctamente.");
            dispose();
        });

        add(new JLabel()); // espacio vacío
        add(guardarBtn);

        setVisible(true);
    }
}
