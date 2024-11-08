package com.example.elecciones;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class AdaptadorCandidatos extends ArrayAdapter<Candidato> {

    public AdaptadorCandidatos(Context context, int resource, List<Candidato> candidatos) {
        super(context, resource, candidatos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        setColor(view, position);
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = super.getDropDownView(position, convertView, parent);
        setColor(view, position);
        return view;
    }

    private void setColor(View view, int position) {
        TextView textView = (TextView) view;
        Candidato candidato = getItem(position);
        if (candidato != null) {
            textView.setTextColor(candidato.getColorPartido());
        }
    }


}