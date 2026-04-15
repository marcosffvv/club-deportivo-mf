package com.clubdeportivo.service;

import com.clubdeportivo.model.Usuario;

public class Sesion {

    private static Usuario usuarioActual;

    public static void setUsuario(Usuario usuario) {
        usuarioActual = usuario;
    }

    public static Usuario getUsuario() {
        return usuarioActual;
    }

    public static void cerrarSesion() {
        usuarioActual = null;
    }
}