package com.clubdeportivo.dao;

import com.clubdeportivo.config.DatabaseConnection;
import com.clubdeportivo.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDAO {

    public Usuario obtenerPorUsername(String username) {

        String sql = "SELECT * FROM usuariosistema WHERE username=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Usuario u = new Usuario();
                u.setId(rs.getInt("id_usuario"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password_hash"));
                u.setRol(rs.getString("rol"));

                return u;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void insertarUsuario(Usuario usuario) {

        String sql = "INSERT INTO usuariosistema (username, password_hash, rol) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getPassword());
            stmt.setString(3, usuario.getRol());

            stmt.executeUpdate();

            System.out.println("Usuario creado correctamente");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}