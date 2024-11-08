package com.example.prueba2;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.List;

public class AdaptadorClientes extends ArrayAdapter<Cliente>{

    public AdaptadorClientes(Context context, int resource, List<Cliente> objects) {
        super(context, resource, objects);
    }

    public AdaptadorClientes(Context context, List<Cliente> objects) {
        super(context, android.R.layout.simple_spinner_item, objects);
    }

    @Override
    public View getView(int position, @Nullable View convertView, ViewGroup parent) {
        View layoutFila = super.getView(position, convertView, parent);
        Cliente cliente = (Cliente) getItem(position);
        TextView tvNombre = layoutFila.findViewById(android.R.id.text1);
        tvNombre.setTextColor(ColorStateList.valueOf(cliente.esVip()? Color.BLUE:Color.BLACK));
        layoutFila.setBackgroundColor(cliente.esVip()?Color.YELLOW:Color.WHITE);
        return layoutFila;

    }
}
