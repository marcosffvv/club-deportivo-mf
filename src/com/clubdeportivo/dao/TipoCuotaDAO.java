package com.clubdeportivo.dao;

import com.clubdeportivo.config.DatabaseConnection;
import com.clubdeportivo.model.TipoCuota;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TipoCuotaDAO {

    public TipoCuota obtenerPorId(int id) {

        String sql = "SELECT * FROM tipocuota WHERE id_tipo_cuota = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new TipoCuota(
                        rs.getInt("id_tipo_cuota"),
                        rs.getString("nombre"),
                        rs.getDouble("precio"),
                        rs.getInt("max_invitaciones_mes")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<TipoCuota> obtenerTodos() {

        List<TipoCuota> lista = new ArrayList<>();

        String sql = "SELECT * FROM tipocuota";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                TipoCuota cuota = new TipoCuota(
                        rs.getInt("id_tipo_cuota"),
                        rs.getString("nombre"),
                        rs.getDouble("precio"),
                        rs.getInt("max_invitaciones_mes")
                );

                lista.add(cuota);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}