package com.example.actividad1;

import static java.lang.String.valueOf;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class AjustesActivity extends AppCompatActivity {

    TextInputEditText txtNome;
    TextInputEditText txtIdade;
    CheckBox chkTrabaja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ajustes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnGuardar = findViewById(R.id.btnGuardar);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarDatos();
            }
        });
        txtNome = findViewById(R.id.txtNome);
        txtIdade = findViewById(R.id.txtIdade);
        chkTrabaja  = findViewById(R.id.chkTrabaja);
    }

    private void guardarDatos() {
        SharedPreferences sp = getSharedPreferences("spGuardarDatos", MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();

        e.putString("Nombre",valueOf(txtNome.getText()));
        e.putInt("Idade",Integer.parseInt(valueOf(txtIdade.getText())));
        e.putBoolean("Traballa",chkTrabaja.isChecked());
        e.apply();
        }
    }
