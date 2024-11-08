package com.example.mantenimientoclientesv2;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class ClienteAdapter extends ArrayAdapter<Cliente> {
    public ClienteAdapter(Context contexto, ArrayList<Cliente> clientes) {
        super(contexto,android.R.layout.simple_list_item_1,clientes);
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View layoutFila = super.getView(position, convertView, parent);
        Cliente cliente = getItem(position);
        TextView tvNombre = (TextView) layoutFila.findViewById(android.R.id.text1);
        tvNombre.setTextColor(ColorStateList.valueOf(cliente.isVIP()? Color.BLUE:Color.BLACK));
        layoutFila.setBackgroundColor(cliente.isVIP()?Color.YELLOW:Color.WHITE);
        return layoutFila;
    }
}
