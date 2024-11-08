package com.example.prueba2;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;
import com.example.prueba2.databinding.ActivityMainBinding;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private Button btnAnhadirClientes;
    private SQLiteDatabase db;
    private ListView lvClientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = BDControlador.getInstance(this).getReadableDatabase();
        lvClientes = findViewById(R.id.lvClientes);
        btnAnhadirClientes = findViewById(R.id.btnAnhadirClientes);
        cargarClientes(db, lvClientes);

        btnAnhadirClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                metodoBoton();
            }
        });
    }

    private void metodoBoton() {
        Intent intent = new Intent(this, ClienteActivity.class);
        startActivity(intent);
    }

    private void cargarClientes(SQLiteDatabase bd, ListView lvClientes) {
        Cursor cursor = bd.rawQuery("SELECT * FROM CLIENTES", null);
        String nombre, apellidos, nif, direccion, provincia;
        boolean esVip;
        int i = 0;
        List<Cliente> listaClientes = new ArrayList<>();
        AdaptadorClientes adaptador = new AdaptadorClientes(this, android.R.layout.simple_list_item_1, listaClientes);

        // Verifica si el cursor no es nulo y tiene datos
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                nombre = cursor.getString(cursor.getColumnIndexOrThrow("NOMBRE"));
                apellidos = cursor.getString(cursor.getColumnIndexOrThrow("APELLIDOS"));
                nif = cursor.getString(cursor.getColumnIndexOrThrow("NIF"));
                provincia = cursor.getString(cursor.getColumnIndexOrThrow("PROVINCIA"));
                esVip = cursor.getInt(cursor.getColumnIndexOrThrow("VIP")) == 1;

                Cliente cliente = new Cliente(nombre, apellidos, nif, provincia, esVip);
                listaClientes.add(cliente);
                i++;
            }
        }

        // Crea el adaptador después de llenar la lista
        lvClientes.setAdapter(adaptador); // Establece el adaptador después de llenar la lista
        cursor.close();
    }
}