package com.example.prueba2;

public class Cliente {
    private String nombre;
    private String apellidos;
    private String nif;
    private String provincia;
    public boolean esVip;

    public Cliente(String nombre, String apellidos, String nif, String provincia, boolean esVip) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.nif = nif;
        this.provincia = provincia;
        this.esVip = esVip;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getNif() {
        return nif;
    }

    public String getProvincia() {
        return provincia;
    }

    public boolean esVip() {
        return esVip;
    }

    @Override
    public String toString() {
        return "Cliente:\n" +
                "Nombre: " + nombre + '\n' +
                "Apellidos: " + apellidos + '\n';
    }
}
