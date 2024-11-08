package com.example.mantenimientoclientesv2;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ActivityResultLauncher<Intent> resultLauncher;
    ListView listaClientes;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //region Interface
        setContentView(R.layout.activity_main);
        listaClientes = (ListView)findViewById(R.id.listaClientes);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //endregion
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        ClienteActivityReturn(result);
                    }
                });

        poblarLista();
        listaClientes.setOnItemClickListener(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                nuevoCliente();
            }
        });
    }
    
    public void ClienteActivityReturn(ActivityResult result) {
        if(result.getResultCode()==RESULT_OK) {
            poblarLista();
            Toast.makeText(this,getString(R.string.OperacionRealizada),Toast.LENGTH_LONG).show();
        }
    }

    public void poblarLista() {
        listaClientes.setAdapter(new ClienteAdapter(this,ConexionBD.getInstance(this).getClientes()));
    }

    @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cliente cliente = (Cliente)parent.getItemAtPosition(position);
        abrirCliente(cliente.getCodCliente());
    }

    private void nuevoCliente() {
        Intent intent = new Intent(this,ClienteActivity.class);
        resultLauncher.launch(intent);
    }

    private void abrirCliente(long codCliente) {
        Intent intent = new Intent(this,ClienteActivity.class);
        intent.putExtra("codCliente", codCliente);
        resultLauncher.launch(intent);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}