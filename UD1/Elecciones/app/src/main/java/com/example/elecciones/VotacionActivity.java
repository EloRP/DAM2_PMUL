package com.example.elecciones;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class VotacionActivity extends AppCompatActivity {

    Spinner spnrCandidatos;
    Button btnVotar;
    SQLiteDatabase bd;
    TextView txtNumCandidatos;
    int numVotaciones = 0;
    final int NUM_MAXIMO_VOTOS = 3;
    int[] numVotosConfirmados = new int[NUM_MAXIMO_VOTOS];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_votacion);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        spnrCandidatos = findViewById(R.id.spnrCandidatos);
        btnVotar = findViewById(R.id.btnVotar);
        txtNumCandidatos = findViewById(R.id.txtNumCandidatos);
        bd = BDControlador.getInstance(this).getWritableDatabase();
        poblarCandidatos();
        btnVotar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                metodoVotacion();
            }
        });
    }

    private void poblarCandidatos() {
        Cursor cursor = bd.rawQuery("SELECT IDCANDIDATO, NOMBRECANDIDATO, NOMBREPARTIDO, COLOR FROM CANDIDATO C INNER JOIN PARTIDO P ON C.IDPARTIDO = P.IDPARTIDO", null);
        int idCandidato;
        String nombreCandidato;
        String nombrePartido;
        int colorPartido;
        List<Candidato> listaCandidatos = new ArrayList<>();
        AdaptadorCandidatos adaptador = new AdaptadorCandidatos(this, android.R.layout.simple_list_item_1, listaCandidatos);

        while (cursor.moveToNext()) {
            idCandidato = cursor.getInt(cursor.getColumnIndexOrThrow("IDCANDIDATO"));
            nombreCandidato = cursor.getString(cursor.getColumnIndexOrThrow("NOMBRECANDIDATO"));
            nombrePartido = cursor.getString(cursor.getColumnIndexOrThrow("NOMBREPARTIDO"));
            colorPartido = cursor.getInt(cursor.getColumnIndexOrThrow("COLOR"));
            listaCandidatos.add(new Candidato(idCandidato, nombreCandidato, nombrePartido, colorPartido));
        }

        spnrCandidatos.setAdapter(adaptador);
        cursor.close();

    }
    private void metodoVotacion() {
    if (numVotaciones < NUM_MAXIMO_VOTOS) {
        int idCandidato = ((Candidato) spnrCandidatos.getSelectedItem()).getIdCandidato();
        numVotosConfirmados[numVotaciones] = idCandidato;
        numVotaciones++;
        txtNumCandidatos.setText(numVotaciones + "/" + NUM_MAXIMO_VOTOS + " candidatos votados");
    }
    if (numVotaciones == NUM_MAXIMO_VOTOS) {
        btnVotar.setEnabled(false);
    }
    }

    private void mostrarCandidatosVotados() {

    }
}
