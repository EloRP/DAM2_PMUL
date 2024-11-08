package com.example.mantenimientoclientesv2;

public class Cliente {
    long codCliente,codProvincia;
    String nombre, apellidos,NIF;
    boolean VIP;
    float latitud,longitud;
    public Cliente() {}
    public Cliente(long codCliente,String nombre,String apellidos,String NIF, long codProvincia,boolean VIP,float latitud,float longitud){
        this.codCliente=codCliente;
        this.nombre=nombre;
        this.apellidos=apellidos;
        this.NIF=NIF;
        this.codProvincia=codProvincia;
        this.VIP=VIP;
        this.latitud=latitud;
        this.longitud=longitud;
    }
    public long getCodCliente () { return codCliente; }
    public boolean isVIP() { return VIP; }
    @Override public String toString() { return apellidos + ", " + nombre; }
}
