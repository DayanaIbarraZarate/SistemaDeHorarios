package model;

/**
 *
 * @author usser
 */
public class Horario {
    private final String[] dias = {
        "Hora", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"
    };

    private final String[] horas = {
        "06:45-08:15", "08:15-09:45", "09:45-11:15",
        "11:15-12:45", "12:45-14:15", "14:15-15:45",
        "15:45-17:15", "17:15-18:45", "18:45-20:15",
        "20:15-21:45"
    };

    private String[][] matriz;

    public Horario() {
        matriz = new String[horas.length][dias.length];
        inicializarHorario();
    }

    private void inicializarHorario() {
        for (int i = 0; i < horas.length; i++) {
            matriz[i][0] = horas[i]; // primera columna = hora
            for (int j = 1; j < dias.length; j++) {
                matriz[i][j] = ""; // resto = vacío
            }
        }
    }

    public String[][] getMatriz() {
        return matriz;
    }

    public String[] getEncabezados() {
        return dias;
    }

    public int getFilas() {
        return matriz.length;
    }

    public int getColumnas() {
        return dias.length;
    }

    public String getCelda(int fila, int columna) {
        return matriz[fila][columna];
    }

    public void setCelda(int fila, int columna, String valor) {
        matriz[fila][columna] = valor;
    }

    public String getHoraEnFila(int fila) {
        return horas[fila];
    }
}
