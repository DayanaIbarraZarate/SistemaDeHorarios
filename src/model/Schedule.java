package model;

public class Schedule {
    public String id;
    public String dia;
    public String horaInicio;
    public String horaFin;
    public String materia;
    public String profesor;

    public Schedule(String id, String dia, String horaInicio, String horaFin, String materia, String profesor) {
        this.id = id;
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.materia = materia;
        this.profesor = profesor;
    }


    public Schedule(String id, String dia, String horaInicio, String horaFin, String materia) {
        this.id = id;
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.materia = materia;
    }

    public String getId() { return id; }
    public String getDia() { return dia; }
    public String getHoraInicio() { return horaInicio; }
    public String getHoraFin() { return horaFin; }
    public String getMateria() { return materia; }

}
