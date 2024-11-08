package com.example.mantenimientoclientesv2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class ConexionBD {
    private static final String NOMBRE_BD = "clientes.db";
    private static final int VERSION_BD = 1;
    private static SQLiteDatabase bd = null;
    private static ConexionBD conexionBD = null;

    private ConexionBD(SQLiteDatabase bd) {
        this.bd = bd;
    }

    public static ConexionBD getInstance(Context contexto) {
        if (conexionBD == null)
            conexionBD = new ConexionBD(new AsistenteBD(contexto).getReadableDatabase());
        return conexionBD;
    }

    public ArrayList<Provincia> getProvincias() {
        ArrayList<Provincia> provincias = new ArrayList<>();
        Cursor filas = bd.rawQuery("SELECT * FROM provincias ORDER BY nombre", null);
        int colIndexCodProvincia = filas.getColumnIndex("codProvincia");
        int colIndexNombre = filas.getColumnIndex("nombre");
        while (filas.moveToNext()) {
            int codProvinciaBD = filas.getInt(colIndexCodProvincia);
            String nombre = filas.getString(colIndexNombre);
            provincias.add(new Provincia(codProvinciaBD, nombre));
        }
        return provincias;
    }

    private ArrayList<Cliente> getClientes(long codCliente) {
        String where = (codCliente > 0) ? "WHERE codCliente=" + codCliente : "";
        ArrayList<Cliente> datosClientes = new ArrayList<>();
        Cursor filas = bd.rawQuery("SELECT * FROM clientes " + where + " ORDER BY apellidos,nombre", null);
        int colIndexCodCliente = filas.getColumnIndex("codCliente");
        int colIndexNombre = filas.getColumnIndex("nombre");
        int colIndexApellidos = filas.getColumnIndex("apellidos");
        int colIndexNIF = filas.getColumnIndex("NIF");
        int colIndexCodProvincia = filas.getColumnIndex("codProvincia");
        int colIndexVIP = filas.getColumnIndex("VIP");
        int colIndexLatitud = filas.getColumnIndex("latitud");
        int colIndexLongitud = filas.getColumnIndex("longitud");
        while (filas.moveToNext()) {
            long cod = filas.getLong(colIndexCodCliente);
            String nombre = filas.getString(colIndexNombre);
            String apellidos = filas.getString(colIndexApellidos);
            String NIF = filas.getString(colIndexNIF);
            long codProvincia = filas.getLong((colIndexCodProvincia));
            boolean VIP = filas.getInt(colIndexVIP) == 1;
            int latitud = filas.getInt(colIndexLatitud);
            int longitud = filas.getInt(colIndexLongitud);
            datosClientes.add(new Cliente(cod, nombre, apellidos, NIF, codProvincia, VIP, latitud, longitud));
        }
        return datosClientes;
    }

    public ArrayList<Cliente> getClientes() {
        return getClientes(-1);
    }

    public Cliente getCliente(long codCliente) {
        return getClientes(codCliente).get(0);
    }

    private boolean validarDatosCliente(ContentValues cv) {
        String NIF = cv.get("NIF").toString().toUpperCase();
        if (!Utiles.esNIF(NIF)) return false;
        cv.put("NIF", NIF);
        return true;
    }

    public long insertarCliente(ContentValues cv) {
        if (!validarDatosCliente(cv)) return -1;
        try {
            return bd.insert("clientes", null, cv);
        } catch (SQLiteConstraintException ex) {
            return -1;
        }
    }

    public boolean actualizarCliente(ContentValues cv, long codCliente) {
        if (!validarDatosCliente(cv)) return false;
        int numRowsAffected = bd.update("clientes", cv, "codCliente=?", new String[]{String.valueOf(codCliente)});
        return numRowsAffected == 1;
    }

    private static class AsistenteBD extends SQLiteOpenHelper {
        public AsistenteBD(Context context) {
            super(context, NOMBRE_BD, null, VERSION_BD);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE provincias (codProvincia INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT)");
            db.execSQL("CREATE TABLE clientes (codCliente INTEGER PRIMARY KEY AUTOINCREMENT, nombreTEXT, apellidos TEXT, NIF TEXT, codProvincia INTEGER, VIP INTEGER, latitud REAL, longitud REAL)");
            db.execSQL("CREATE UNIQUE INDEX NIF ON clientes(NIF COLLATE NOCASE)");
            ContentValues cv = new ContentValues();
            cv.put("nombre", "A Coruña");
            db.insert("provincias", null, cv);
            cv.put("nombre", "Lugo");
            db.insert("provincias", null, cv);
            cv.put("nombre", "Ourense");
            db.insert("provincias", null, cv);
            cv.put("nombre", "Pontevedra");
            db.insert("provincias", null, cv);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}