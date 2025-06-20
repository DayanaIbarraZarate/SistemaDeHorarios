package ui;

import javax.swing.*;
import java.awt.*;

public class ProfesorProfilePanel extends JPanel {

    //Color de fondo
    private final Color backgroundColor = new Color(230, 245, 255); // Celeste pastel

    public ProfesorProfilePanel() {
        setOpaque(true);
        setLayout(new BorderLayout()); // Usamos BorderLayout como base

        // Panel vertical para los campos (centrado)
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false); // Para mantener el fondo personalizado
        content.setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80)); // Márgenes

        //  Info superior
        JLabel infoLabel = new JLabel("<html><b>Joel Pizarro</b><br>Joel@gmail.com</html>");
        infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.add(infoLabel);

        content.add(Box.createVerticalStrut(10)); // Espacio

        //  Botón Editar
        JButton editButton = styledButton("Editar", new Color(128, 255, 255));
        editButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.add(editButton);

        content.add(Box.createVerticalStrut(20)); // Espacio

        //Campos de texto
        content.add(styledTextField("Nombre"));
        content.add(Box.createVerticalStrut(10));

        content.add(styledTextField("Apellido"));
        content.add(Box.createVerticalStrut(10));

        content.add(styledTextField("País"));
        content.add(Box.createVerticalStrut(10));

        content.add(styledTextField("Correo electrónico"));
        content.add(Box.createVerticalStrut(10));

        content.add(styledTextField("Zona Horaria"));
        content.add(Box.createVerticalStrut(20));

        //  Botón "Agregar correo"
        JButton addEmailButton = styledButton("+ Agregar correo alternativo", new Color(255, 255, 204));
        content.add(addEmailButton);

        content.add(Box.createVerticalStrut(20));

        //  Botón Guardar
        JButton botonGuardar = styledButton("Guardar Cambios", new Color(100, 200, 200));
        botonGuardar.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.add(botonGuardar);

        // Agregar todo al panel principal (centrado)
        add(content, BorderLayout.CENTER);
    }

    //  Personaliza el fondo del panel
    
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(backgroundColor);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.dispose();
    }

    //texto estilizado
    private static JTextField styledTextField(String placeholder) {
        JTextField field = new JTextField();
        field.setText(placeholder);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBackground(new Color(255, 230, 230)); // Rosa claro
        field.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        return field;
    }

    // Botón estilizado
    private static JButton styledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(bgColor);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return button;
    }
}
