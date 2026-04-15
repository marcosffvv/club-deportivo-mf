package com.clubdeportivo.service;

import com.clubdeportivo.dao.UsuarioDAO;
import com.clubdeportivo.model.Usuario;
import com.clubdeportivo.util.HashUtil;

public class AuthService {

    private UsuarioDAO dao = new UsuarioDAO();

    public Usuario login(String username, String password) {

        Usuario u = dao.obtenerPorUsername(username);

        if (u == null) return null;

        String hashedInput = HashUtil.hash(password);

        // 🔥 CAMBIO CLAVE
        if (!hashedInput.equals(u.getPasswordHash())) return null;

        // 🔥 SOLO ADMINS
        if (!"ADMIN".equalsIgnoreCase(u.getRol())) return null;

        return u;
    }

    public boolean crearAdmin(String username, String password) {

        Usuario u = new Usuario();

        u.setUsername(username);

        // 🔥 IMPORTANTE: guardar hash
        u.setPasswordHash(HashUtil.hash(password));

        u.setRol("ADMIN");

        return dao.insertarUsuario(u); // 🔥 devolvemos resultado
    }
}