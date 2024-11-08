package com.example.elecciones;

public class Candidato {
    private int idCandidato;
    private String nombre;
    private String apellidos;
    private int idPartido;
    private String nombrePartido;
    private int colorPartido;

    public int getIdCandidato() {
        return idCandidato;
    }

    public void setIdCandidato(int idCandidato) {
        this.idCandidato = idCandidato;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getIdPartido() {
        return idPartido;
    }

    public void setIdPartido(int idPartido) {
        this.idPartido = idPartido;
    }

    public String getNombrePartido() {
        return nombrePartido;
    }

    public void setNombrePartido(String nombrePartido) {
        this.nombrePartido = nombrePartido;
    }

    public int getColorPartido() {
        return colorPartido;
    }

    public void setColorPartido(int colorPartido) {
        this.colorPartido = colorPartido;
    }

    public Candidato(int idCandidato, String nombre) {
        this.idCandidato = idCandidato;
        this.nombre = nombre;
    }

    public Candidato(int idCandidato, String nombre, String nombrePartido, int colorPartido) {
        this.idCandidato = idCandidato;
        this.nombre = nombre;
        this.nombrePartido = nombrePartido;
        this.colorPartido = colorPartido;
    }

    @Override
    public String toString() {
        return idCandidato + " - " + nombre + "\t\t" + nombrePartido;
    }
}
