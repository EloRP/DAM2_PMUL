package com.example.elecciones;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Color;

import androidx.annotation.Nullable;

import com.example.elecciones.Util.Utiles;


public class BDControlador extends SQLiteOpenHelper {
    private static final String NOMBRE_BD = "BDCandidatos";
    private static final int VERSION_BD = 4;
    private SQLiteStatement sqlInsertPartido;
    private SQLiteStatement sqlInsertCandidato;
    private BDControlador(@Nullable Context context) {
        super(context, NOMBRE_BD, null, VERSION_BD);
    }

    private static BDControlador instancia=null;
    public static BDControlador getInstance(Activity activity) {
        if(instancia==null)
            instancia=new BDControlador(activity);
        return instancia;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE CANDIDATO ("
                + "IDCANDIDATO INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "NOMBRECANDIDATO TEXT,"
                + "IDPARTIDO INTEGER,"
                + "NUMVOTOS INTEGER"
                + ")");

        db.execSQL("CREATE TABLE USUARIO ("
                + "IDUSUARIO INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "NIF TEXT,"
                + "PASSWORD TEXT,"
                + "NUMVOTOS INTEGER,"
                + "HAVOTADO TINYINT"
                + ")");

        db.execSQL("CREATE TABLE PARTIDO ("
                + "IDPARTIDO INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "NOMBREPARTIDO TEXT,"
                + "COLOR INTEGER"
                + ")");


        db.execSQL("INSERT INTO USUARIO (NIF, PASSWORD, NUMVOTOS)" +
                "VALUES ('53864409L', '" + Utiles.generateHash("abc123.")+ "', +  0 )");

        db.execSQL("INSERT INTO USUARIO (NIF, PASSWORD, NUMVOTOS)" +
                "VALUES ('30066602J', '" + Utiles.generateHash("sesamo")+ "', + 0 )");



    sqlInsertPartido = db.compileStatement("INSERT INTO PARTIDO (NOMBREPARTIDO, COLOR) VALUES(?, ?)");
    sqlInsertCandidato = db.compileStatement("INSERT INTO CANDIDATO (IDPARTIDO, NOMBRECANDIDATO) VALUES(?,?)");

        insertaDatosPartido("PP", Color.BLUE, new String[]{"Alberto Núñez Feijóo", "Cuca Gamarra", "Borja Sémper"});
        insertaDatosPartido("VOX", Color.GREEN, new String[]{"Santiago Abascal", "Iván Espinosa de los Monteros", "Macarena Olona"});
        insertaDatosPartido("PSOE", Color.RED, new String[]{"Pedro Sánchez", "Adriana Lastra", "Margarita Robles"});
        insertaDatosPartido("Podemos", Color.MAGENTA, new String[]{"Pablo Iglesias", "Irene Montero", "Pablo Echenique"});



    }

    private void insertaDatosPartido(String nombrePartido, int color, String[] listaCandidatos) {
        sqlInsertPartido.bindString(1, nombrePartido);
        sqlInsertPartido.bindLong(2, color);
        long codPartido = sqlInsertPartido.executeInsert();
        for (String nombreCandidato : listaCandidatos) {
            sqlInsertCandidato.bindLong(1, codPartido);
            sqlInsertCandidato.bindString(2, nombreCandidato);
            sqlInsertCandidato.execute();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
