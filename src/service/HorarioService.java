package service;

import model.Horario;

import java.io.*;

public class HorarioService {

    private static final String CARPETA = "SistemaHorarios/horario/";

    public static void guardarHorario(Horario horario, String tipo, String id) {
        String nombreArchivo = CARPETA + "horario_" + tipo + "_" + id + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            // Escribir encabezado
            String[] encabezados = horario.getEncabezados();
            writer.write(String.join(";", encabezados));
            writer.newLine();

            // Escribir filas
            String[][] matriz = horario.getMatriz();
            for (String[] fila : matriz) {
                writer.write(String.join(";", fila));
                writer.newLine();
            }

            System.out.println("Horario guardado en: " + nombreArchivo);
        } catch (IOException e) {
            System.err.println("Error al guardar el horario: " + e.getMessage());
        }
    }

    public static Horario cargarHorario(String tipo, String id) {
        String nombreArchivo = CARPETA + "horario_" + tipo + "_" + id + ".txt";
        Horario horario = new Horario();

        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            System.out.println("Archivo no encontrado: " + nombreArchivo);
            return horario; // se devuelve un horario vacío
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            // Leer encabezado (descartamos)
            String encabezado = reader.readLine();

            int fila = 0;
            String linea;
            while ((linea = reader.readLine()) != null && fila < horario.getFilas()) {
                String[] columnas = linea.split(";", -1); // -1 para conservar celdas vacías
                for (int col = 0; col < columnas.length && col < horario.getColumnas(); col++) {
                    horario.setCelda(fila, col, columnas[col]);
                }
                fila++;
            }

            System.out.println("Horario cargado desde: " + nombreArchivo);
        } catch (IOException e) {
            System.err.println("Error al leer el horario: " + e.getMessage());
        }

        return horario;
    }
}