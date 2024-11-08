package com.example.mantenimientoclientesv2;

public class Provincia {
    private long codProvincia;
    private String nombre;
    public Provincia(long codProvincia, String nombre) {
        this.codProvincia=codProvincia;
        this.nombre=nombre;
    }
    public long getCodProvincia() { return codProvincia; }
    @Override public String toString() { return nombre; }
}