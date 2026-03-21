package com.clubdeportivo.dao;

import com.clubdeportivo.config.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class InvitacionDAO {

    // 🔹 Obtener invitaciones usadas
    public int obtenerUsadas(int idSocio, int mes, int anio) {

        String sql = "SELECT cantidad_usadas FROM invitacion WHERE id_socio = ? AND mes = ? AND anio = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idSocio);
            stmt.setInt(2, mes);
            stmt.setInt(3, anio);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("cantidad_usadas");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0; // si no hay registro → 0 usadas
    }

    // 🔹 Insertar registro inicial
    public void insertar(int idSocio, int mes, int anio, int cantidad) {

        String sql = "INSERT INTO invitacion (id_socio, mes, anio, cantidad_usadas) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idSocio);
            stmt.setInt(2, mes);
            stmt.setInt(3, anio);
            stmt.setInt(4, cantidad);

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 Actualizar invitaciones
    public void actualizar(int idSocio, int mes, int anio, int cantidad) {

        String sql = "UPDATE invitacion SET cantidad_usadas = ? WHERE id_socio = ? AND mes = ? AND anio = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cantidad);
            stmt.setInt(2, idSocio);
            stmt.setInt(3, mes);
            stmt.setInt(4, anio);

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}