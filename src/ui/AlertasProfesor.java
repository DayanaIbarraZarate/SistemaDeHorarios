package ui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AlertasProfesor extends JFrame {

    private JTextArea textArea;

    public AlertasProfesor(List<String> mensajes) {
        setTitle("Alertas del Profesor");

        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        textArea = new JTextArea(10, 30);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        // Carga mensajes
        for (String msg : mensajes) {
            textArea.append("- " + msg + "\n");

        }

        add(new JScrollPane(textArea), BorderLayout.CENTER);
        setVisible(true);
    }
}
