package com.example.mantenimientoclientesv2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utiles {
    public static boolean esNIF(String nif) {
        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        Pattern pattern = Pattern.compile("(\\d{8})([" + letras + "])",Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(nif);
        if (matcher.matches()) {
            int numero = Integer.parseInt(matcher.group(1));
            String letra = matcher.group(2);
            char letraCorrecta = letras.charAt(numero % 23);
            if (letra.toUpperCase().charAt(0) == letraCorrecta) return true;
        }
        return false;
    }
}