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

        if (!hashedInput.equals(u.getPassword())) return null;

        if (!"ADMIN".equalsIgnoreCase(u.getRol())) return null;

        return u;
    }

    public void crearAdmin(String username, String password) {

        Usuario u = new Usuario();

        u.setUsername(username);
        u.setPassword(HashUtil.hash(password));
        u.setRol("ADMIN");

        dao.insertarUsuario(u);
    }
}