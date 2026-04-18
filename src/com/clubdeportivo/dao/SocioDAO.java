package com.clubdeportivo.dao;

import com.clubdeportivo.config.DatabaseConnection;
import com.clubdeportivo.model.Socio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SocioDAO {

    public List<Socio> obtenerTodos() {

        List<Socio> socios = new ArrayList<>();

        String sql = "SELECT * FROM Socio";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Socio socio = new Socio();

                socio.setIdSocio(rs.getInt("id_socio"));
                socio.setNombre(rs.getString("nombre"));
                socio.setApellidos(rs.getString("apellidos"));
                socio.setEmail(rs.getString("email"));
                socio.setTelefono(rs.getString("telefono"));
                socio.setActivo(rs.getBoolean("activo"));
                socio.setIdTipoCuota(rs.getInt("id_tipo_cuota"));

                socios.add(socio);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return socios;
    }

    public Socio obtenerPorId(int idSocio) {

        String sql = "SELECT * FROM Socio WHERE id_socio = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idSocio);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Socio socio = new Socio();

                socio.setIdSocio(rs.getInt("id_socio"));
                socio.setNombre(rs.getString("nombre"));
                socio.setApellidos(rs.getString("apellidos"));
                socio.setEmail(rs.getString("email"));
                socio.setTelefono(rs.getString("telefono"));
                socio.setActivo(rs.getBoolean("activo"));
                socio.setIdTipoCuota(rs.getInt("id_tipo_cuota"));

                return socio;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void insertarSocio(Socio socio) {

        String sql = "INSERT INTO Socio (nombre, apellidos, email, telefono, fecha_alta, activo, id_tipo_cuota) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, socio.getNombre());
            stmt.setString(2, socio.getApellidos());
            stmt.setString(3, socio.getEmail());
            stmt.setString(4, socio.getTelefono());
            stmt.setDate(5, java.sql.Date.valueOf(socio.getFechaAlta()));
            stmt.setBoolean(6, socio.isActivo());
            stmt.setInt(7, socio.getIdTipoCuota());

            stmt.executeUpdate();

            System.out.println("Socio insertado correctamente");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actualizarSocio(Socio socio) {

        String sql = "UPDATE Socio SET nombre=?, apellidos=?, email=?, telefono=?, activo=?, id_tipo_cuota=? WHERE id_socio=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, socio.getNombre());
            stmt.setString(2, socio.getApellidos());
            stmt.setString(3, socio.getEmail());
            stmt.setString(4, socio.getTelefono());
            stmt.setBoolean(5, socio.isActivo());
            stmt.setInt(6, socio.getIdTipoCuota());
            stmt.setInt(7, socio.getIdSocio());

            stmt.executeUpdate();

            System.out.println("Socio actualizado");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminarSocio(int idSocio) {

        String deleteInvitaciones = "DELETE FROM invitacion WHERE id_socio=?";
        String deletePagos = "DELETE FROM pago WHERE id_socio=?";
        String deleteSocio = "DELETE FROM socio WHERE id_socio=?";

        try (Connection conn = DatabaseConnection.getConnection()) {

            try (PreparedStatement stmt1 = conn.prepareStatement(deleteInvitaciones)) {
                stmt1.setInt(1, idSocio);
                stmt1.executeUpdate();
            }

            try (PreparedStatement stmt2 = conn.prepareStatement(deletePagos)) {
                stmt2.setInt(1, idSocio);
                stmt2.executeUpdate();
            }

            try (PreparedStatement stmt3 = conn.prepareStatement(deleteSocio)) {
                stmt3.setInt(1, idSocio);
                stmt3.executeUpdate();
            }

            System.out.println("Socio eliminado correctamente");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String[]> obtenerEvolucionSocios() {

        List<String[]> datos = new ArrayList<>();

        String sql = "{CALL getEvolucionSocios()}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                String fecha = rs.getDate("fecha_alta").toString();
                String total = String.valueOf(rs.getInt("total_acumulado"));

                datos.add(new String[]{fecha, total});
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return datos;
    }
}