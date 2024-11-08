package com.example.mantenimientoclientesv2;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mantenimientoclientesv2.databinding.ActivityClienteBinding;

import java.util.ArrayList;

public class ClienteActivity extends AppCompatActivity implements View.OnClickListener {
    static ConexionBD bd;
    boolean esNuevoCliente;
    long codCliente;
    EditText etNombre, etApellidos, etNIF, etLatitud, etLongitud;
    Spinner spListaProvincias;
    CheckBox chkVIP;
    ImageButton bMapa;
    Button bGuardar;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bd = ConexionBD.getInstance(this);
        codCliente = getIntent().getLongExtra("codCliente", -1);
        esNuevoCliente = (codCliente == -1);
        setResult(RESULT_CANCELED);
        setContentView(R.layout.activity_cliente);
        initComponents();
        etNIF.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s,int start,int count,int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable s) {
                etNIF.setTextColor(!Utiles.esNIF(s.toString()) ? Color.RED : Color.BLACK);
            }
        });
        bMapa.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) { VerMapa(); }
        });
        bGuardar.setOnClickListener(this);
        bGuardar.setText(getString(esNuevoCliente ? R.string.insertar : R.string.guardar));
        setTitle(getString(esNuevoCliente ? R.string.nuevoCliente : R.string.editarCliente));
        ArrayList<Provincia> listaProvincias=ConexionBD.getInstance(this).getProvincias();
        long codSinProvincia=0; //codProvincia virtual correspondiente al 1º elemento vacío de la lista de provincias
        listaProvincias.add(0,new Provincia(codSinProvincia,"")); // primer elemento en blanco. Comprobar su código.
        this.spListaProvincias.setAdapter(new ArrayAdapter<Provincia>(
                this,
                android.R.layout.simple_list_item_1,
                listaProvincias));
        if (!esNuevoCliente) {
            Cliente cliente=ConexionBD.getInstance(this).getCliente(codCliente);
            etNombre.setText(cliente.nombre);
            etApellidos.setText(cliente.apellidos);
            etNIF.setText(cliente.NIF);
            chkVIP.setChecked(cliente.VIP);
            etLatitud.setText(String.valueOf(cliente.latitud));
            etLongitud.setText(String.valueOf(cliente.longitud));
            if(cliente.codProvincia!=codSinProvincia) {
                int posProvinciaSeleccionada = 0;
                for (Provincia provincia : listaProvincias) {
                    if (provincia.getCodProvincia()==cliente.codProvincia)
                        break;
                    posProvinciaSeleccionada++;
                }
                this.spListaProvincias.setSelection(posProvinciaSeleccionada);
            }
        }
    }
    private void initComponents() {
        etNombre = (EditText) findViewById(R.id.etNombre);
        etApellidos = (EditText) findViewById(R.id.etApellidos);
        etNIF = (EditText) findViewById(R.id.etNIF);
        spListaProvincias =(Spinner)findViewById(R.id.listaProvincias);
        chkVIP = (CheckBox) findViewById(R.id.chkVIP);
        etLatitud=findViewById(R.id.latitud);
        etLongitud=findViewById(R.id.longitud);
        bMapa=findViewById(R.id.bMapa);
        bGuardar = (Button) findViewById(R.id.bGuardar);
    }
    public void VerMapa() {
        String latitud=etLatitud.getText().toString();
        String longitud=etLongitud.getText().toString();
        String URI="geo:"+latitud+","+longitud+"?z=17";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(URI));
        startActivity(intent);
    }
    @Override public void onClick(View v) { // Guardar
        ContentValues cv = new ContentValues();
        cv.put("nombre", etNombre.getText().toString().trim());
        cv.put("apellidos", etApellidos.getText().toString().trim());
        cv.put("NIF", etNIF.getText().toString().trim());
        cv.put("VIP", chkVIP.isChecked() ? 1 : 0);
        cv.put("codProvincia", ((Provincia) spListaProvincias.getSelectedItem()).getCodProvincia());
        try { cv.put("latitud", Float.parseFloat(etLatitud.getText().toString().replace(",", "."))); }
        catch (NumberFormatException ex) { cv.put("latitud", ""); }
        try { cv.put("longitud", Float.parseFloat(etLongitud.getText().toString().replace(",", "."))); }
        catch (NumberFormatException ex) {cv.put("longitud", ""); }
        if (esNuevoCliente) {
            if(bd.insertarCliente(cv)<0) {
                etNIF.setError(getString(R.string.problemas_con_los_datos_del_cliente));
                return;
            }
        }
        else
        if(!bd.actualizarCliente(cv,codCliente)) {
            bGuardar.setError(getString(R.string.problemas_con_los_datos_del_cliente));
            return;
        }
        setResult(RESULT_OK);
        finish();
    }
}