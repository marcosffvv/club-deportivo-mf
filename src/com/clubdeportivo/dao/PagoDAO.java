package com.clubdeportivo.dao;

import com.clubdeportivo.config.DatabaseConnection;
import com.clubdeportivo.model.Pago;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PagoDAO {

    // INSERTAR PAGO
    public void insertarPago(Pago pago) {
        String sql = "INSERT INTO pago (id_socio, mes, anio, fecha_pago, importe) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pago.getIdSocio());
            stmt.setInt(2, pago.getMes());
            stmt.setInt(3, pago.getAnio());
            stmt.setDate(4, java.sql.Date.valueOf(pago.getFechaPago()));
            stmt.setDouble(5, pago.getImporte());

            stmt.executeUpdate();
            System.out.println("Pago insertado correctamente");

        } catch (SQLException e) {
            System.out.println("Error al insertar pago");
            e.printStackTrace();
        }
    }

    // OBTENER PAGOS POR SOCIO
    public List<Pago> obtenerPagosPorSocio(int idSocio) {
        List<Pago> lista = new ArrayList<>();
        String sql = "SELECT * FROM pago WHERE id_socio = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idSocio);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Pago pago = new Pago();
                pago.setIdPago(rs.getInt("id_pago"));
                pago.setIdSocio(rs.getInt("id_socio"));
                pago.setMes(rs.getInt("mes"));
                pago.setAnio(rs.getInt("anio"));
                pago.setFechaPago(rs.getDate("fecha_pago").toLocalDate());
                pago.setImporte(rs.getDouble("importe"));

                lista.add(pago);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // COMPROBAR SI EXISTE PAGO (CLAVE PARA ESTADO)
    public boolean existePago(int idSocio, int mes, int anio) {
        String sql = "SELECT COUNT(*) FROM pago WHERE id_socio = ? AND mes = ? AND anio = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idSocio);
            stmt.setInt(2, mes);
            stmt.setInt(3, anio);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}