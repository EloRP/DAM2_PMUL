package com.example.prueba2;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BDControlador extends SQLiteOpenHelper {
    private static final String NOMBRE_BD = "BDClientes";
    private static final int VERSION_BD = 1;

    private BDControlador(@Nullable Context context) {
        super(context, NOMBRE_BD, null, VERSION_BD);
    }

    // Nombre de la tabla y columnas
    private static final String CLIENTES = "Clientes";
    private static final String ID = "ID";
    private static final String NOMBRE = "NOMBRE";
    private static final String APELLIDOS = "APELLIDOS";
    private static final String NIF = "NIF";
    private static final String PROVINCIA = "PROVINCIA";
    private static final String VIP = "VIP";

    // SQL para crear la tabla
    private static final String creacionTabla =
            "CREATE TABLE " + CLIENTES + " (" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NOMBRE + " TEXT, " +
                    APELLIDOS + " TEXT, " +
                    NIF + " TEXT, " +
                    PROVINCIA + " TEXT, " +
                    VIP + " INTEGER)";

    private static final String creacionTablaProvincia =
            "CREATE TABLE " + PROVINCIA + " (" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NOMBRE + " TEXT)";

    private static final String anhadirProvincias =
            "INSERT INTO PROVINCIA (NOMBRE)" +
                    "VALUES ('PONTEVEDRA'), ('A CORUÑA'), ('LUGO'), ('OURENSE')";


    private static BDControlador instancia=null;

    public static BDControlador getInstance(Activity activity) {
        if(instancia==null)
            instancia=new BDControlador(activity);
        return instancia;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(creacionTabla);
        db.execSQL(creacionTablaProvincia);
        db.execSQL(anhadirProvincias);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CLIENTES);
        onCreate(db);
    }

    public void insertarCliente(String nombre, String apellidos, String nif, String provincia, boolean esVip) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOMBRE, nombre);
        values.put(APELLIDOS, apellidos);
        values.put(NIF, nif);
        values.put(PROVINCIA, provincia);
        values.put(VIP, esVip ? 1 : 0);
        db.insert(CLIENTES, null, values);
    }
}
