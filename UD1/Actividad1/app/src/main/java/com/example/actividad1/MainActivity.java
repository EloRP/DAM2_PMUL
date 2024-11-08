package com.example.actividad1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    TextView lblNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nombreUsuario();

        Button btnAjustes = (Button) findViewById(R.id.btnAjustes);
        nombreUsuario();
        btnAjustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirAjustes();
            }
        });
    }

    private void abrirAjustes() {
        Intent intent = new Intent(this, AjustesActivity.class);
        startActivity(intent);
    }

    private void nombreUsuario() {
        SharedPreferences sp = getSharedPreferences("spGuardarDatos", MODE_PRIVATE);
        String nombreUser = sp.getString("Nombre", "Nombre no encontrado");
        lblNombre = (TextView) findViewById(R.id.lblNombre);
        lblNombre.setText("Hola, " + nombreUser);
    }
}