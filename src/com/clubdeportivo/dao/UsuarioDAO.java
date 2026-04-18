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
                u.setPasswordHash(rs.getString("password_hash")); // 🔥 corregido
                u.setRol(rs.getString("rol"));

                return u;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean insertarUsuario(Usuario usuario) {

        // VALIDACIÓN PREVIA
        if (existeUsername(usuario.getUsername())) {
            return false;
        }

        String sql = "INSERT INTO usuariosistema (username, password_hash, rol) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getPasswordHash());
            stmt.setString(3, usuario.getRol());

            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean existeUsername(String username) {

        String sql = "SELECT 1 FROM usuariosistema WHERE username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();

            return rs.next(); // true si existe

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}