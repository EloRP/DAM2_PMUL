package com.example.prueba2;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
public class ClienteActivity extends AppCompatActivity {

    TextInputEditText txtApellidos;
    TextInputEditText txtNombre;
    TextInputEditText txtNif;
    TextView lblProvincia;
    Spinner spnrProvincia;
    CheckBox chkVip;
    Button btnGuardar;
    SQLiteDatabase bd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_clase_cliente);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtApellidos = findViewById(R.id.txtApellidos);
        txtNombre = findViewById(R.id.txtNombre);
        txtNif = findViewById(R.id.txtNif);
        spnrProvincia = findViewById(R.id.spnrProvincia);
        chkVip = findViewById(R.id.chkVip);
        btnGuardar = findViewById(R.id.btnGuardar);
        bd = BDControlador.getInstance(this).getWritableDatabase();
        poblarProvincias();

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ejecutarMain();
                guardarCliente();

            }
        });

    }

    private void ejecutarMain() {
        Intent intent = new Intent(ClienteActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void poblarProvincias() {
        Cursor cursor = bd.rawQuery("SELECT NOMBRE FROM PROVINCIA", null);
        String nombreProvincia;
        ArrayList<String> provincias = new ArrayList<>();
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, provincias);

        while (cursor.moveToNext()) {
            nombreProvincia = cursor.getString(cursor.getColumnIndexOrThrow("NOMBRE"));
            provincias.add(nombreProvincia);
        }

        spnrProvincia.setAdapter(adaptador);
        cursor.close();

    }

    private void guardarCliente() {
        String nombre = txtNombre.getText().toString();
        String apellidos = txtApellidos.getText().toString();
        String nif = txtNif.getText().toString();
        String provincia = spnrProvincia.getSelectedItem().toString();
        boolean esVip = chkVip.isChecked();

        BDControlador bdControlador = BDControlador.getInstance(this);

        bdControlador.insertarCliente(nombre, apellidos, nif, provincia, esVip);

        Toast.makeText(this, "Cliente guardado", Toast.LENGTH_SHORT).show();
    }
    
}