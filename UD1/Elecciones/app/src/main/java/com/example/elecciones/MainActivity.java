package com.example.elecciones;

import static com.example.elecciones.Util.Utiles.NifOk;
import static com.example.elecciones.Util.Utiles.PasswordOk;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import com.google.android.material.textfield.TextInputEditText;

import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    TextInputEditText txtNif;
    TextInputEditText txtPassword;
    private Button btnIniciarSesion;
    String textoNif;
    String textoPassword;
    BDControlador bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bd = BDControlador.getInstance(this);
        txtNif = findViewById(R.id.txtNif);
        txtPassword = findViewById(R.id.txtPassword);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                metodoBoton();
            }
        });
        }

    private void metodoBoton() {
        textoNif = txtNif.getText().toString();
        textoPassword = txtPassword.getText().toString();

        boolean nifCorrecto = NifOk(textoNif);
        boolean passwordCorrecta = PasswordOk(textoPassword);

        if (nifCorrecto && passwordCorrecta) {
            Intent intent = new Intent(this, VotacionActivity.class);
            startActivity(intent);
        } else if (!nifCorrecto && !passwordCorrecta) {
            Toast.makeText(this, "Error al verificar el NIF y la contraseña.", Toast.LENGTH_SHORT).show();
        } else if (!nifCorrecto) {
            Toast.makeText(this, "Error al verificar el NIF.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al verificar la contraseña.", Toast.LENGTH_SHORT).show();
        }
    }


}


